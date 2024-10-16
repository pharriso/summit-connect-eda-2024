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
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.SessionWindows;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.processor.api.Record;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.apache.kafka.streams.processor.api.Processor;
import org.philpat.Sensu;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.kafka.client.serialization.ObjectMapperSerde;

@ApplicationScoped
public class ThrottlerResource {

    private String sensueventsKeyedTopic = "sensu-keyed";
    private String sensueventsLimitedTopic = "limited-sensu";

    @ConfigProperty(name = "window.size.seconds.throttle",  defaultValue="30")
    private Long windowsize;

    @ConfigProperty(name = "throttle.count",  defaultValue="1")
    private Long throttlecount;

    @jakarta.enterprise.inject.Produces
    public Topology buildTopology(){

        System.out.println ("The throttler window size is " + windowsize + " seconds");
        System.out.println ("The throttle count is " + throttlecount + " seconds");
        StreamsBuilder builder = new StreamsBuilder();
       
        ObjectMapperSerde<Sensu> sensuEventSerDes = new ObjectMapperSerde<>(Sensu.class);

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
            .count(Materialized.as("windowed-events"))
        ;

        produceOneEvent
            
            .toStream()

            // filtering on null as well as count because sessionwindow produces a tombstone
            .filter((key, count) -> count != null && count.intValue() == throttlecount.intValue())

            // Convert the key back to the sensu-keyed key, and the message back to the orginal 
            .process(() -> new Processor<Windowed<String>, Long, String, Sensu>() {

                private ProcessorContext<String, Sensu> context;
                @Override
                public void init(ProcessorContext<String, Sensu> context) {
                    
                    this.context = context;
                }

                @Override
                public void process(Record<Windowed<String>, Long> record) {
                    
                    Record<String,Long> sensuNewRecordwithKey = record.withKey(record.key().key());
                    
                    try {
                        Record<String,Sensu> sensuNewRecordwithKeyAndValue = sensuNewRecordwithKey.withValue
                            (new ObjectMapper().readValue(record.headers().lastHeader("origMessage").value(), Sensu.class));
                        sensuNewRecordwithKeyAndValue.headers().remove("origMessage");
                        context.forward(sensuNewRecordwithKeyAndValue);
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
            .to(sensueventsLimitedTopic, Produced.with(Serdes.String(), sensuEventSerDes)) 
        ;
   
       return builder.build();
       
    }
}