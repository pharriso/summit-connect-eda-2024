package org.eda.kafka;

import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.processor.api.FixedKeyProcessor;
import org.apache.kafka.streams.processor.api.FixedKeyProcessorContext;
import org.apache.kafka.streams.processor.api.FixedKeyRecord;

import org.philpat.Sensu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.kafka.client.serialization.ObjectMapperSerde;

@ApplicationScoped
public class ReKeyResource {

    private String sensueventsTopic = "sensu-is-good";
    private String sensueventsKeyedTopic = "sensu-keyed";

    @jakarta.enterprise.inject.Produces
    public Topology buildTopology(){
        
        StreamsBuilder builder = new StreamsBuilder();
       
        ObjectMapperSerde<Sensu> sensuEventSerDes = new ObjectMapperSerde<>(Sensu.class);

        // Stream the Sensu event mesages into the toplogy to transform the key. Needed for creating a key to determine where the event came from. 
        KStream<String, Sensu> eventStream = builder.stream(sensueventsTopic, Consumed.with(Serdes.String(), sensuEventSerDes));
        eventStream
    
            .process(() -> new FilterProcessor<String, Sensu>())

            // Convert the key to location, rack, host, check name
            .map((k, v) -> KeyValue.pair(v.getEntity().getMetadata().getLabels().getLocation() + "/" + v.getEntity().getMetadata().getLabels().getRack() + 
                            "/" + v.getEntity().getMetadata().getName() + "/" + v.getCheck().getMetadata().getName(), v ))
             
            //Extraction orginal message and store in header. This will be restored after the count. 
            .processValues(() -> new FixedKeyProcessor<String,Sensu,Sensu>() {

                private FixedKeyProcessorContext<String, Sensu> context;

                @Override
                public void init(FixedKeyProcessorContext<String,Sensu> context){
                    this.context = context;
                }
                    @Override
                public void process(FixedKeyRecord<String, Sensu> record) {
                    
                    try {
                        record.headers().add("origMessage", new ObjectMapper().writeValueAsBytes(record.value()));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                    context.forward(record);
                } 
            }
            ) 
            .peek((k, v) -> {
                ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
                System.out.println ("Rekeyed: " + k + " Status: " + v.getCheck().getState() + " " + DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(zonedDateTime));
            })
            .to(sensueventsKeyedTopic, Produced.with(Serdes.String(), sensuEventSerDes))
        ;
   
       return builder.build();
       
    }
}