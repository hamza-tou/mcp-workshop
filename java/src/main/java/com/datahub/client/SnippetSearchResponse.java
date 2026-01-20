package com.datahub.client;

import java.util.List;

public class SnippetSearchResponse {
    private int total;
    private List<DataHubSnippet> results;
    
    // Constructors
    public SnippetSearchResponse() {}
    
    public SnippetSearchResponse(int total, List<DataHubSnippet> results) {
        this.total = total;
        this.results = results;
    }
    
    // Getters and Setters
    public int getTotal() {
        return total;
    }
    
    public void setTotal(int total) {
        this.total = total;
    }
    
    public List<DataHubSnippet> getResults() {
        return results;
    }
    
    public void setResults(List<DataHubSnippet> results) {
        this.results = results;
    }
}
