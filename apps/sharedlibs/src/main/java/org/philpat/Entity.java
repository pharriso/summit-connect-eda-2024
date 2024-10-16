
package org.philpat;

import java.util.List;

public class Entity {

    private String entityClass;
    private System system;
    private List<Object> subscriptions;
    private Integer lastSeen;
    private String user;
    private Metadata__1 metadata;
    private String sensuAgentVersion;

    public String getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(String entityClass) {
        this.entityClass = entityClass;
    }

    public System getSystem() {
        return system;
    }

    public void setSystem(System system) {
        this.system = system;
    }

    public List<Object> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Object> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Integer getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Integer lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Metadata__1 getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata__1 metadata) {
        this.metadata = metadata;
    }

    public String getSensuAgentVersion() {
        return sensuAgentVersion;
    }

    public void setSensuAgentVersion(String sensuAgentVersion) {
        this.sensuAgentVersion = sensuAgentVersion;
    }

}
