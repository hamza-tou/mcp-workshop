package com.datahub.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Component
public class DataHubClient {
    
    private final RestClient restClient;
    
    public DataHubClient(@Value("${datahub.api.base-url:http://localhost:8000}") String baseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
    
    /**
     * Get all available tags from DataHub API
     * @return List of tags
     * @throws RestClientException if the request fails
     */
    public List<String> getAvailableTags() {
        try {
            return restClient.get()
                    .uri("/tags")
                    .retrieve()
                    .body(new org.springframework.core.ParameterizedTypeReference<List<String>>() {});
        } catch (RestClientException e) {
            throw new RestClientException("Failed to fetch tags from DataHub API", e);
        }
    }
    
    /**
     * Search for documents or code snippets in DataHub
     * @param query The search query
     * @param scope The search scope ("docs" or "snippets")
     * @param limit Maximum number of results to return
     * @return SearchResponse containing the search results
     * @throws RestClientException if the request fails
     */
    public GlobalSearchResponse search(String query, String scope, int limit) {
        try {
            if ("docs".equals(scope)) {
                DocSearchResponse docResponse = restClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/search")
                                .queryParam("q", query)
                                .queryParam("scope", scope)
                                .queryParam("limit", limit)
                                .build())
                        .retrieve()
                        .body(DocSearchResponse.class);
                
                // Convert to generic SearchResponse
                return new GlobalSearchResponse(docResponse.getTotal(), 
                        List.copyOf(docResponse.getResults()));
            } else {
                SnippetSearchResponse snippetResponse = restClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/search")
                                .queryParam("q", query)
                                .queryParam("scope", scope)
                                .queryParam("limit", limit)
                                .build())
                        .retrieve()
                        .body(SnippetSearchResponse.class);
                
                // Convert to generic SearchResponse
                return new GlobalSearchResponse(snippetResponse.getTotal(), 
                        List.copyOf(snippetResponse.getResults()));
            }
        } catch (RestClientException e) {
            throw new RestClientException("Failed to search DataHub API", e);
        }
    }
    
    /**
     * Get documents by tag from DataHub API
     * @param tag The tag to filter documents by
     * @return DocSearchResponse containing the list of documents with the specified tag
     * @throws RestClientException if the request fails
     */
    public DocSearchResponse getDocsByTag(String tag) {
        try {
            List<DataHubDocument> results = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/docs")
                            .queryParam("tag", tag)
                            .build())
                    .retrieve()
                    .body(new org.springframework.core.ParameterizedTypeReference<List<DataHubDocument>>() {});
            
            // Wrap results in a DocSearchResponse
            return new DocSearchResponse(results != null ? results.size() : 0, results);
        } catch (RestClientException e) {
            throw new RestClientException("Failed to fetch documents by tag from DataHub API", e);
        }
    }
    
    /**
     * Get code snippets from DataHub API with optional filters
     * @param type Optional type filter for snippets
     * @param service Optional service filter for snippets
     * @return SnippetSearchResponse containing the list of snippets
     * @throws RestClientException if the request fails
     */
    public SnippetSearchResponse getSnippets(String type, String service) {
        try {
            List<DataHubSnippet> results = restClient.get()
                    .uri(uriBuilder -> {
                        var builder = uriBuilder.path("/snippets");
                        if (type != null && !type.isEmpty()) {
                            builder.queryParam("type", type);
                        }
                        if (service != null && !service.isEmpty()) {
                            builder.queryParam("service", service);
                        }
                        return builder.build();
                    })
                    .retrieve()
                    .body(new org.springframework.core.ParameterizedTypeReference<List<DataHubSnippet>>() {});
            
            // Wrap results in a SnippetSearchResponse
            return new SnippetSearchResponse(results != null ? results.size() : 0, results);
        } catch (RestClientException e) {
            throw new RestClientException("Failed to fetch snippets from DataHub API", e);
        }
    }
    
    /**
     * Get a document by its ID from DataHub API
     * @param docId The document ID to fetch
     * @return DataHubDocument containing the full document details
     * @throws RestClientException if the request fails or document is not found
     */
    public DataHubDocument getDocumentById(String docId) {
        try {
            return restClient.get()
                    .uri("/docs/" + docId)
                    .retrieve()
                    .body(DataHubDocument.class);
        } catch (RestClientException e) {
            throw new RestClientException("Failed to fetch document from DataHub API", e);
        }
    }
    
    /**
     * Get a code snippet by its ID from DataHub API
     * @param snippetId The snippet ID to fetch
     * @return DataHubSnippet containing the full snippet details
     * @throws RestClientException if the request fails or snippet is not found
     */
    public DataHubSnippet getSnippetById(String snippetId) {
        try {
            return restClient.get()
                    .uri("/snippets/" + snippetId)
                    .retrieve()
                    .body(DataHubSnippet.class);
        } catch (RestClientException e) {
            throw new RestClientException("Failed to fetch snippet from DataHub API", e);
        }
    }
    
}
