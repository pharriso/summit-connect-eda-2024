package org.eda.kafka;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlySessionStore;
import org.apache.kafka.streams.state.ReadOnlyWindowStore;
import org.apache.kafka.streams.state.WindowStoreIterator;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;
import org.philpat.Sensu;

import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;


// Using this to test sending messages 
@Path("/sensu")
public class SensuResource {

    private static final Logger LOGGER = Logger.getLogger(SensuResource.class);

    @Inject 
    KafkaStreams eventStreams;

    //   @Channel("sensu")
 //   Emitter<Record> emitter;
    
   /*  @POST
    public Response enqueueSensu(Sensu sensu) {
        LOGGER.infof("Sending sense event %s to Kafka", sensu.getId());
        emitter.send(Record.of("philp", sensu));
        return Response.accepted().build();
    }
*/

  /*   @GET
    public List<String> getWindowedStore(){
        ReadOnlySessionStore<String, Long> eventStore = eventStreams.store(StoreQueryParameters.fromNameAndType("windowed-events", QueryableStoreTypes.sessionStore()));
        
        return listAllSessions("windowed-events");
    }

    public List<String> listAllSessions(String storeName) {
        ReadOnlySessionStore<String, String> sessionStore = eventStreams.store(
                StoreQueryParameters.fromNameAndType(storeName, QueryableStoreTypes.sessionStore())
        );

        List<String> sessions = new ArrayList<>();
        try (WindowStoreIterator<String> iterator = sessionStore.fetch(key, Instant.ofEpochMilli(0), Instant.now())) {
            while (iterator.hasNext()) {
                sessions.add(iterator.next().value);
            }
        }
        return sessionsList;
    } */
}