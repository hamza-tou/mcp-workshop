package com.datahub.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataHubTag {
    
    @JsonProperty("tag")
    private String tag;
    
    @JsonProperty("description")
    private String description;
    
    public DataHubTag() {
    }
    
    public DataHubTag(String tag, String description) {
        this.tag = tag;
        this.description = description;
    }
    
    public String getTag() {
        return tag;
    }
    
    public void setTag(String tag) {
        this.tag = tag;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}
