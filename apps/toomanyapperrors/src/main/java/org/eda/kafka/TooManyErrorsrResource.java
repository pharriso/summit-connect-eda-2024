package org.eda.kafka;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.EmitStrategy;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.SessionWindows;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.processor.api.Record;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.apache.kafka.streams.processor.api.Processor;
import org.philpat.Sensu;
import org.philpat.snow.Snow;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.quarkus.kafka.client.serialization.ObjectMapperSerde;

@ApplicationScoped
public class TooManyErrorsrResource {

    private String sensueventsKeyedTopic = "sensu-keyed";
    private String sensueventsTooManyErrors = "report-to-snow";
    
    @ConfigProperty(name = "window.size.seconds.apperrors",  defaultValue="30")
    private Long windowsize;
    @ConfigProperty(name = "too.many.errors.count",  defaultValue="30")
    private Long errorcount;

    @jakarta.enterprise.inject.Produces
    public Topology buildTopology(){

        System.out.println ("The Too many app errors window size is " + windowsize + " seconds");
        System.out.println ("The Too many app errors count is " + errorcount + " seconds");
        StreamsBuilder builder = new StreamsBuilder();
       
        ObjectMapperSerde<Sensu> sensuEventSerDes = new ObjectMapperSerde<>(Sensu.class);

        ObjectMapper snowObjectMapper =  new ObjectMapper();
        // Wrap the JSON data with root element "snow"
        snowObjectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);

        ObjectMapperSerde<Snow> snowEventSerDes = new ObjectMapperSerde<>(Snow.class, snowObjectMapper);
        
        // Stream the keyed Sensu event mesages into the toplogy so they can be grouped

        KStream<String, Sensu> eventStreamKeyed = builder.stream(sensueventsKeyedTopic, Consumed.with(Serdes.String(), sensuEventSerDes))
        
            // Sensu passes events across once a system is fixed. Filter these and there is nothing to do
            .filterNot((k, v) -> v.getCheck().getState().equals("passing"));

       // Create a count of the events (by location, rack, host and check and send to store

        KTable<Windowed<String>, Long> produceOneEvent = eventStreamKeyed
            .groupByKey()
            
            .windowedBy(
                SessionWindows.ofInactivityGapWithNoGrace(
                /* inactivityGap = */ Duration.ofSeconds(windowsize)
                /* afterWindowEnd = */ //Duration.ofHours(8)
                )
            )
        
            .emitStrategy(EmitStrategy.onWindowUpdate())
            .count()
        ;

        produceOneEvent
            
            .toStream()

            // filtering on null as well as count because sessionwindow produces a tombstone
            // If more than "n"" error occurs in time window... only want to send one message per window so filtering for the second message only.
            .filter((key, count) -> count != null && count == errorcount)

            // Convert the key back to the sensu-keyed key, and the message back to the orginal 
            .process(() -> new Processor<Windowed<String>, Long, String, Snow>() {

                private ProcessorContext<String, Snow> context;
                @Override
                public void init(ProcessorContext<String, Snow> context) {
                    
                    this.context = context;
                }

                @Override
                public void process(Record<Windowed<String>, Long> record) {
                    
                    Record<String,Long> sensuNewRecordwithKey = record.withKey(record.key().key());
                    
                    try {

                        Sensu sensu = new ObjectMapper().readValue(record.headers().lastHeader("origMessage").value(), Sensu.class);

                        Snow snow = new Snow("Sensu check has failed " + record.value() +  " times - " + sensu.getCheck().getMetadata().getName(), 
                        sensu.getCheck().getOutput(), sensu.getEntity().getMetadata().getName());
                        
                        Record<String,Snow> snowNewRecordwithKeyAndValue = sensuNewRecordwithKey.withValue(snow);

                        snowNewRecordwithKeyAndValue.headers().remove("origMessage");
                        context.forward(snowNewRecordwithKeyAndValue);
                    } catch (StreamReadException e) {

                        e.printStackTrace();
                    } catch (DatabindException e) {

                        e.printStackTrace();
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }
                
            } )

            .peek((k, v) -> {
                ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
                System.out.println ("The key is " + k + " " + DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(zonedDateTime));
                if (!k.equals(null))
                System.out.println ("They value is " + v);
            }           
            )
            .to(sensueventsTooManyErrors, Produced.with(Serdes.String(), snowEventSerDes)) 
        ;
   
       return builder.build();
       
    }
}