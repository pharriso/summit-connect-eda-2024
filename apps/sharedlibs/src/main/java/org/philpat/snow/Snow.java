package org.philpat.snow;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "snow")
public class Snow {

    private String short_description;
    private String description;
    private String cmdb_ci;

    public Snow(String short_description, String description, String cmdb_ci) {
        this.short_description = short_description;
        this.description = description;
        this.cmdb_ci = cmdb_ci;
    }
    public String getShort_description() {
        return short_description;
    }
    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getCmdb_ci() {
        return cmdb_ci;
    }
    public void setCmdb_ci(String cmdb_ci) {
        this.cmdb_ci = cmdb_ci;
    }
    @Override
    public String toString() {
        return "Snow [short_description=" + short_description + ", description=" + description + ", cmdb_ci=" + cmdb_ci
                + "]";
    }  
}
