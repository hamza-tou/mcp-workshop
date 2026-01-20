package com.datahub;

import com.datahub.client.DataHubClient;
import com.datahub.client.DataHubDocument;
import com.datahub.client.DataHubSnippet;
import com.datahub.client.DataHubTag;
import com.datahub.client.DocSearchResponse;
import com.datahub.client.GlobalSearchResponse;
import com.datahub.client.SnippetSearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springaicommunity.mcp.annotation.McpResource;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Component
public class DataHubCapabilities {

    private static final Logger logger = LoggerFactory.getLogger(DataHubCapabilities.class);

    @Autowired
    private DataHubClient dataHubClient;

    @McpTool(
        name = "search-datahub",
        description = "Search in DataHub for technical documents or code snippets. Full-text search across title, content, tags, etc."
    )
    public String searchDataHub(
            @McpToolParam(description = "Text to search (e.g., 'graphql', 'kubernetes')", required = true) String query,
            @McpToolParam(description = "Search scope - 'docs' for documents or 'snippets' for code", required = false) String scope,
            @McpToolParam(description = "Maximum number of results to return (1-100)", required = false) Integer limit) {
        
        // Default values
        String searchScope = (scope != null && !scope.isEmpty()) ? scope : "docs";
        int searchLimit = (limit != null) ? limit : 10;
        
        try {
            GlobalSearchResponse response = dataHubClient.search(query, searchScope, searchLimit);
            
            if (response.getTotal() == 0) {
                return String.format("No results found for '%s' in %s", query, searchScope);
            }
            
            StringBuilder results = new StringBuilder();
            results.append(String.format("Found %d result(s) for '%s' in %s:\n", 
                response.getTotal(), query, searchScope));
            
            for (Object item : response.getResults()) {
                if ("docs".equals(searchScope) && item instanceof DataHubDocument) {
                    DataHubDocument doc = (DataHubDocument) item;
                    results.append(String.format("\n📄 %s\n", doc.getTitle()));
                    results.append(String.format("   ID: %s\n", doc.getDocId()));
                    results.append(String.format("   Tags: %s\n", String.join(", ", doc.getTags())));
                    results.append(String.format("   Owner: %s\n", doc.getOwner()));
                    // Content preview (first 200 characters)
                    if (doc.getContent() != null) {
                        String preview = doc.getContent().substring(0, Math.min(200, doc.getContent().length()))
                            .replace("\n", " ");
                        results.append(String.format("   Preview: %s...\n", preview));
                    }
                } else if ("snippets".equals(searchScope) && item instanceof DataHubSnippet) {
                    DataHubSnippet snippet = (DataHubSnippet) item;
                    results.append(String.format("\n💻 %s\n", snippet.getTitle()));
                    results.append(String.format("   ID: %s\n", snippet.getSnippetId()));
                    results.append(String.format("   Language: %s\n", snippet.getLanguage()));
                    results.append(String.format("   Type: %s\n", snippet.getType()));
                    results.append(String.format("   Description: %s\n", snippet.getDescription()));
                }
            }
            
            return results.toString();
            
        } catch (RestClientException e) {
            logger.error("Error during search for query '{}' in scope '{}': {}", query, searchScope, e.getMessage(), e);
            return String.format("Error during search: %s", e.getMessage());
        }
    }

    @McpTool(
        name = "list-documents-by-tag",
        description = "List documents with a specific tag"
    )
    public String listDocumentsByTag(
            @McpToolParam(description = "Tag to filter (e.g., 'api', 'kubernetes', 'graphql')", required = true) String tag) {
        
        try {
            DocSearchResponse response = dataHubClient.getDocsByTag(tag);
            
            if (response.getTotal() == 0) {
                return String.format("No documents found with tag '%s'", tag);
            }
            
            StringBuilder results = new StringBuilder();
            results.append(String.format("Documents with tag '%s' (%d found):\n", tag, response.getTotal()));
            
            for (DataHubDocument doc : response.getResults()) {
                results.append(String.format("\n📄 %s\n", doc.getTitle()));
                results.append(String.format("   ID: %s\n", doc.getDocId()));
                results.append(String.format("   Tags: %s\n", String.join(", ", doc.getTags())));
                results.append(String.format("   Owner: %s\n", doc.getOwner()));
            }
            
            return results.toString();
            
        } catch (RestClientException e) {
            logger.error("Error listing documents by tag '{}': {}", tag, e.getMessage(), e);
            return String.format("Error: %s", e.getMessage());
        }
    }

    @McpTool(
        name = "list-snippets",
        description = "List available code snippets with optional filters"
    )
    public String listSnippets(
            @McpToolParam(description = "Type of snippet (query, function, config, middleware, migration)", required = false) String type,
            @McpToolParam(description = "Service concerned (postgresql, redis, kubernetes, etc.)", required = false) String service) {
        
        try {
            SnippetSearchResponse response = dataHubClient.getSnippets(type, service);
            
            if (response.getTotal() == 0) {
                StringBuilder filterInfo = new StringBuilder();
                if (type != null && !type.isEmpty()) {
                    filterInfo.append("type='").append(type).append("'");
                }
                if (service != null && !service.isEmpty()) {
                    if (filterInfo.length() > 0) {
                        filterInfo.append(" and ");
                    }
                    filterInfo.append("service='").append(service).append("'");
                }
                String filterStr = filterInfo.length() > 0 ? " with " + filterInfo : "";
                return String.format("No snippets found%s", filterStr);
            }
            
            StringBuilder results = new StringBuilder();
            results.append(String.format("Snippets found (%d):\n", response.getTotal()));
            
            for (DataHubSnippet snippet : response.getResults()) {
                results.append(String.format("\n💻 %s\n", snippet.getTitle()));
                results.append(String.format("   ID: %s\n", snippet.getSnippetId()));
                results.append(String.format("   Language: %s\n", snippet.getLanguage()));
                results.append(String.format("   Type: %s\n", snippet.getType()));
                if (snippet.getService() != null && !snippet.getService().isEmpty()) {
                    results.append(String.format("   Service: %s\n", snippet.getService()));
                }
            }
            
            return results.toString();
            
        } catch (RestClientException e) {
            logger.error("Error listing snippets with type '{}' and service '{}': {}", type, service, e.getMessage(), e);
            return String.format("Error: %s", e.getMessage());
        }
    }

    @McpTool(
        name = "get-available-tags",
        description = "List all available tags in DataHub"
    )
    public String getAvailableTags() {
        try {
            List<DataHubTag> tags = dataHubClient.getAvailableTags();
            
            StringBuilder results = new StringBuilder();
            results.append(String.format("Available tags (%d):\n", tags.size()));
            
            for (DataHubTag tag : tags) {
                results.append(String.format("  • %s: %s\n", tag.getTag(), tag.getDescription()));
            }
            
            return results.toString();
            
        } catch (RestClientException e) {
            logger.error("Error getting available tags: {}", e.getMessage(), e);
            return String.format("Error: %s", e.getMessage());
        }
    }

    @McpResource(
        uri = "datahub://docs/{docId}",
        name = "DataHub Document",
        description = "Retrieve the complete content of a DataHub document"
    )
    public String getDocument(String docId) {
        try {
            DataHubDocument doc = dataHubClient.getDocumentById(docId);
            
            StringBuilder result = new StringBuilder();
            result.append(String.format("# %s\n\n", doc.getTitle()));
            result.append(String.format("**ID**: %s\n", doc.getDocId()));
            result.append(String.format("**Owner**: %s\n", doc.getOwner()));
            result.append(String.format("**Tags**: %s\n", String.join(", ", doc.getTags())));
            result.append(String.format("**Created**: %s\n", doc.getCreatedAt()));
            result.append(String.format("**Updated**: %s\n\n", doc.getUpdatedAt()));
            result.append("---\n\n");
            result.append(doc.getContent());
            
            return result.toString();
            
        } catch (RestClientException e) {
            logger.error("Error retrieving document '{}': {}", docId, e.getMessage(), e);
            if (e.getMessage() != null && e.getMessage().contains("404")) {
                return String.format("Document '%s' not found", docId);
            }
            return String.format("Connection error: %s", e.getMessage());
        }
    }

    @McpResource(
        uri = "datahub://snippets/{snippetId}",
        name = "DataHub Snippet",
        description = "Retrieve a complete code snippet"
    )
    public String getSnippet(String snippetId) {
        try {
            DataHubSnippet snippet = dataHubClient.getSnippetById(snippetId);
            
            StringBuilder result = new StringBuilder();
            result.append(String.format("# %s\n\n", snippet.getTitle()));
            result.append(String.format("**ID**: %s\n", snippet.getSnippetId()));
            result.append(String.format("**Language**: %s\n", snippet.getLanguage()));
            result.append(String.format("**Type**: %s\n", snippet.getType()));
            if (snippet.getService() != null && !snippet.getService().isEmpty()) {
                result.append(String.format("**Service**: %s\n", snippet.getService()));
            }
            result.append(String.format("**Description**: %s\n\n", snippet.getDescription()));
            result.append("## Code\n\n");
            result.append(String.format("```%s\n", snippet.getLanguage()));
            result.append(snippet.getCode());
            result.append("\n```");
            
            return result.toString();
            
        } catch (RestClientException e) {
            logger.error("Error retrieving snippet '{}': {}", snippetId, e.getMessage(), e);
            if (e.getMessage() != null && e.getMessage().contains("404")) {
                return String.format("Snippet '%s' not found", snippetId);
            }
            return String.format("Connection error: %s", e.getMessage());
        }
    }
}
