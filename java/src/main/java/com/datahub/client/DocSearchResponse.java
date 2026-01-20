package com.datahub.client;

import java.util.List;

public class DocSearchResponse {
    private int total;
    private List<DataHubDocument> results;
    
    // Constructors
    public DocSearchResponse() {}
    
    public DocSearchResponse(int total, List<DataHubDocument> results) {
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
    
    public List<DataHubDocument> getResults() {
        return results;
    }
    
    public void setResults(List<DataHubDocument> results) {
        this.results = results;
    }
}
