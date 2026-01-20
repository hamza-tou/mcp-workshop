package com.datahub.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataHubSnippet {
    
    private String title;
    
    @JsonProperty("snippet_id")
    private String snippetId;
    
    private String language;
    private String type;
    private String service;
    private String description;
    private String code;
    
    // Constructors
    public DataHubSnippet() {}
    
    // Getters and Setters
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getSnippetId() {
        return snippetId;
    }
    
    public void setSnippetId(String snippetId) {
        this.snippetId = snippetId;
    }
    
    public String getLanguage() {
        return language;
    }
    
    public void setLanguage(String language) {
        this.language = language;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getService() {
        return service;
    }
    
    public void setService(String service) {
        this.service = service;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
}
