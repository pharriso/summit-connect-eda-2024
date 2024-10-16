package org.eda.kafka;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;
import org.philpat.Sensu;

import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;


// Using this to test sending messages 
@Path("/sensu")
public class SensuResource {
    private static final Logger LOGGER = Logger.getLogger(SensuResource.class);

    @Channel("sensu-is-good")
    Emitter<Record> emitter;
    
     @POST
    public Response enqueueSensu(Sensu sensu) {
        LOGGER.infof("Sending sense event %s to Kafka", sensu.getId());
        emitter.send(Record.of("philp", sensu));
        return Response.accepted().build();
    }

}