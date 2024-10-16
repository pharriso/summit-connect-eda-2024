
package org.philpat;

public class Sensu {

    private Check check;
    private Entity entity;
    private String id;
    private Metadata__2 metadata;
    private Integer timestamp;

    public Check getCheck() {
        return check;
    }

    public void setCheck(Check check) {
        this.check = check;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Metadata__2 getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata__2 metadata) {
        this.metadata = metadata;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

}
