package com.datahub.client;

import java.util.List;

public class GlobalSearchResponse {
    private int total;
    private List<Object> results;
    
    // Constructors
    public GlobalSearchResponse() {}
    
    public GlobalSearchResponse(int total, List<Object> results) {
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
    
    public List<Object> getResults() {
        return results;
    }
    
    public void setResults(List<Object> results) {
        this.results = results;
    }
}
