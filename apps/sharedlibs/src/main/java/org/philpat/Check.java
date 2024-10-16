
package org.philpat;

import java.util.List;

public class Check {

    private String command;
    private List<Object> handlers;
    private List<Object> subscriptions;
    private String output;
    private String state;
    private Integer status;
    private Integer totalStateChange;
    private Integer lastOk;
    private Integer occurrences;
    private Integer occurrencesWatermark;
    private Metadata metadata;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public List<Object> getHandlers() {
        return handlers;
    }

    public void setHandlers(List<Object> handlers) {
        this.handlers = handlers;
    }

    public List<Object> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Object> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTotalStateChange() {
        return totalStateChange;
    }

    public void setTotalStateChange(Integer totalStateChange) {
        this.totalStateChange = totalStateChange;
    }

    public Integer getLastOk() {
        return lastOk;
    }

    public void setLastOk(Integer lastOk) {
        this.lastOk = lastOk;
    }

    public Integer getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(Integer occurrences) {
        this.occurrences = occurrences;
    }

    public Integer getOccurrencesWatermark() {
        return occurrencesWatermark;
    }

    public void setOccurrencesWatermark(Integer occurrencesWatermark) {
        this.occurrencesWatermark = occurrencesWatermark;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

}
