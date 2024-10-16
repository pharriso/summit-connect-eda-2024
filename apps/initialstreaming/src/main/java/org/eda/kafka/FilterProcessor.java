package org.eda.kafka;

import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.processor.api.Record;
import org.philpat.Sensu;

//* Only return sensu events, these events do not have headers */
public class FilterProcessor <K,V> implements Processor<String, Sensu, String, Sensu> {

    private ProcessorContext context;

    @Override
    public void init(ProcessorContext<String, Sensu> context) {
        this.context = context;  
    }

    @Override
    public void process(Record<String, Sensu> record) {
        String testfilter;
        try {
            testfilter = record.headers().lastHeader("message-type").toString();
        }
        catch(NullPointerException ex){
            testfilter = null;
        }
            


        if (testfilter == null) {
            context.forward(record); /// no type header means sensu
        }
        else if (testfilter == "snowfix") {
            System.out.println(String.format("no thanks, not interested in %s ", testfilter));
        }

        
    }
    
}
