package com.datahub.client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.MediaType;

import com.datahub.client.DataHubClient;
import com.datahub.client.DocSearchResponse;
import com.datahub.client.DataHubDocument;
import com.datahub.client.GlobalSearchResponse;
import com.datahub.client.SnippetSearchResponse;
import com.datahub.client.DataHubSnippet;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class DataHubClientTest {
    
    private ClientAndServer mockServer;
    private DataHubClient dataHubClient;
    
    @BeforeEach
    void setUp() {
        // Start MockServer on a random available port
        mockServer = ClientAndServer.startClientAndServer(0);
        int port = mockServer.getPort();
        
        // Create DataHubClient pointing to the mock server
        dataHubClient = new DataHubClient("http://localhost:" + port);
    }
    
    @AfterEach
    void tearDown() {
        // Stop the mock server after each test
        if (mockServer != null) {
            mockServer.stop();
        }
    }
    
    @Test
    public void testGetAvailableTags() {
        // Arrange
        List<String> expectedTags = List.of("tag1", "tag2", "tag3");
        
        // Configure MockServer expectation
        mockServer
            .when(
                request()
                    .withMethod("GET")
                    .withPath("/tags")
            )
            .respond(
                response()
                    .withStatusCode(200)
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withBody("[\"tag1\", \"tag2\", \"tag3\"]")
            );
        
        // Act
        List<String> actualTags = dataHubClient.getAvailableTags();
        
        // Assert
        assertNotNull(actualTags);
        assertEquals(3, actualTags.size());
        assertEquals(expectedTags, actualTags);
    }
    
    @Test
    public void testSearchDocs() {
        // Arrange
        String query = "spring boot";
        String scope = "docs";
        int limit = 10;
        
        String jsonResponse = """
            {
                "total": 2,
                "results": [
                    {
                        "title": "Spring Boot Guide",
                        "doc_id": "doc123",
                        "tags": ["java", "spring"],
                        "owner": "john.doe",
                        "content": "This is a comprehensive guide to Spring Boot..."
                    },
                    {
                        "title": "Spring Boot Best Practices",
                        "doc_id": "doc456",
                        "tags": ["spring", "best-practices"],
                        "owner": "jane.smith",
                        "content": "Learn the best practices for Spring Boot development..."
                    }
                ]
            }
            """;
        
        // Configure MockServer expectation
        mockServer
            .when(
                request()
                    .withMethod("GET")
                    .withPath("/search")
                    .withQueryStringParameter("q", query)
                    .withQueryStringParameter("scope", scope)
                    .withQueryStringParameter("limit", String.valueOf(limit))
            )
            .respond(
                response()
                    .withStatusCode(200)
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withBody(jsonResponse)
            );
        
        // Act
        GlobalSearchResponse searchResponse = dataHubClient.search(query, scope, limit);
        
        // Assert
        assertNotNull(searchResponse);
        assertEquals(2, searchResponse.getTotal());
        assertEquals(2, searchResponse.getResults().size());
        
        Object firstResult = searchResponse.getResults().get(0);
        assertTrue(firstResult instanceof DataHubDocument);
        DataHubDocument docResult = (DataHubDocument) firstResult;
        assertEquals("Spring Boot Guide", docResult.getTitle());
        assertEquals("doc123", docResult.getDocId());
        assertEquals(2, docResult.getTags().length);
        assertEquals("john.doe", docResult.getOwner());
        assertNotNull(docResult.getContent());
    }
    
    @Test
    public void testSearchSnippets() {
        // Arrange
        String query = "authentication";
        String scope = "snippets";
        int limit = 5;
        
        String jsonResponse = """
            {
                "total": 1,
                "results": [
                    {
                        "title": "JWT Authentication",
                        "snippet_id": "snip789",
                        "language": "java",
                        "type": "function",
                        "description": "JWT token validation function"
                    }
                ]
            }
            """;
        
        // Configure MockServer expectation
        mockServer
            .when(
                request()
                    .withMethod("GET")
                    .withPath("/search")
                    .withQueryStringParameter("q", query)
                    .withQueryStringParameter("scope", scope)
                    .withQueryStringParameter("limit", String.valueOf(limit))
            )
            .respond(
                response()
                    .withStatusCode(200)
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withBody(jsonResponse)
            );
        
        // Act
        GlobalSearchResponse searchResponse = dataHubClient.search(query, scope, limit);
        
        // Assert
        assertNotNull(searchResponse);
        assertEquals(1, searchResponse.getTotal());
        assertEquals(1, searchResponse.getResults().size());
        
        Object firstResult = searchResponse.getResults().get(0);
        assertTrue(firstResult instanceof DataHubSnippet);
        DataHubSnippet snippetResult = (DataHubSnippet) firstResult;
        assertEquals("JWT Authentication", snippetResult.getTitle());
        assertEquals("snip789", snippetResult.getSnippetId());
        assertEquals("java", snippetResult.getLanguage());
        assertEquals("function", snippetResult.getType());
        assertEquals("JWT token validation function", snippetResult.getDescription());
    }
    
    @Test
    public void testGetDocsByTag() {
        // Arrange
        String tag = "java";
        
        String jsonResponse = """
            [
                {
                    "title": "Spring Boot Guide",
                    "doc_id": "doc123",
                    "tags": ["java", "spring"],
                    "owner": "john.doe"
                },
                {
                    "title": "Java Concurrency Tutorial",
                    "doc_id": "doc789",
                    "tags": ["java", "concurrency"],
                    "owner": "jane.smith"
                }
            ]
            """;
        
        // Configure MockServer expectation
        mockServer
            .when(
                request()
                    .withMethod("GET")
                    .withPath("/docs")
                    .withQueryStringParameter("tag", tag)
            )
            .respond(
                response()
                    .withStatusCode(200)
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withBody(jsonResponse)
            );
        
        // Act
        DocSearchResponse docsByTag = dataHubClient.getDocsByTag(tag);
        
        // Assert
        assertNotNull(docsByTag);
        assertEquals(2, docsByTag.getResults().size());
        
        DataHubDocument firstDoc = docsByTag.getResults().get(0);
        assertEquals("Spring Boot Guide", firstDoc.getTitle());
        assertEquals("doc123", firstDoc.getDocId());
        assertEquals(2, firstDoc.getTags().length);
        assertEquals("java", firstDoc.getTags()[0]);
        assertEquals("spring", firstDoc.getTags()[1]);
        assertEquals("john.doe", firstDoc.getOwner());
        
        DataHubDocument secondDoc = docsByTag.getResults().get(1);
        assertEquals("Java Concurrency Tutorial", secondDoc.getTitle());
        assertEquals("doc789", secondDoc.getDocId());
        assertEquals(2, secondDoc.getTags().length);
        assertEquals("jane.smith", secondDoc.getOwner());
    }
    
    @Test
    public void testGetSnippets() {
        // Arrange
        String jsonResponse = """
            [
                {
                    "title": "Spring REST Controller",
                    "snippet_id": "snippet123",
                    "language": "java",
                    "type": "controller",
                    "service": "user-service"
                },
                {
                    "title": "Database Query",
                    "snippet_id": "snippet456",
                    "language": "java",
                    "type": "repository",
                    "service": "data-service"
                }
            ]
        """;
        
        // Configure MockServer expectation
        mockServer
            .when(
                request()
                    .withMethod("GET")
                    .withPath("/snippets")
            )
            .respond(
                response()
                    .withStatusCode(200)
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withBody(jsonResponse)
            );
        
        // Act
        SnippetSearchResponse snippets = dataHubClient.getSnippets(null, null);
        
        // Assert
        assertNotNull(snippets);
        assertEquals(2, snippets.getTotal());
        assertEquals(2, snippets.getResults().size());
        
        DataHubSnippet firstSnippet = snippets.getResults().get(0);
        assertEquals("Spring REST Controller", firstSnippet.getTitle());
        assertEquals("snippet123", firstSnippet.getSnippetId());
        assertEquals("java", firstSnippet.getLanguage());
        assertEquals("controller", firstSnippet.getType());
        assertEquals("user-service", firstSnippet.getService());
        
        DataHubSnippet secondSnippet = snippets.getResults().get(1);
        assertEquals("Database Query", secondSnippet.getTitle());
        assertEquals("snippet456", secondSnippet.getSnippetId());
        assertEquals("java", secondSnippet.getLanguage());
        assertEquals("repository", secondSnippet.getType());
        assertEquals("data-service", secondSnippet.getService());
    }
    
    @Test
    public void testGetSnippetsWithTypeFilter() {
        // Arrange
        String jsonResponse = """
            [
                {
                    "title": "Spring REST Controller",
                    "snippet_id": "snippet123",
                    "language": "java",
                    "type": "controller",
                    "service": "user-service"
                }
            ]
        """;
        
        // Configure MockServer expectation
        mockServer
            .when(
                request()
                    .withMethod("GET")
                    .withPath("/snippets")
                    .withQueryStringParameter("type", "controller")
            )
            .respond(
                response()
                    .withStatusCode(200)
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withBody(jsonResponse)
            );
        
        // Act
        SnippetSearchResponse snippets = dataHubClient.getSnippets("controller", null);
        
        // Assert
        assertNotNull(snippets);
        assertEquals(1, snippets.getTotal());
        assertEquals(1, snippets.getResults().size());
        assertEquals("controller", snippets.getResults().get(0).getType());
    }
    
    @Test
    public void testGetSnippetsWithServiceFilter() {
        // Arrange
        String jsonResponse = """
            [
                {
                    "title": "Database Query",
                    "snippet_id": "snippet456",
                    "language": "java",
                    "type": "repository",
                    "service": "data-service"
                }
            ]
        """;
        
        // Configure MockServer expectation
        mockServer
            .when(
                request()
                    .withMethod("GET")
                    .withPath("/snippets")
                    .withQueryStringParameter("service", "data-service")
            )
            .respond(
                response()
                    .withStatusCode(200)
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withBody(jsonResponse)
            );
        
        // Act
        SnippetSearchResponse snippets = dataHubClient.getSnippets(null, "data-service");
        
        // Assert
        assertNotNull(snippets);
        assertEquals(1, snippets.getTotal());
        assertEquals(1, snippets.getResults().size());
        assertEquals("data-service", snippets.getResults().get(0).getService());
    }
    
    @Test
    public void testGetSnippetsWithBothFilters() {
        // Arrange
        String jsonResponse = """
            [
                {
                    "title": "Spring REST Controller",
                    "snippet_id": "snippet123",
                    "language": "java",
                    "type": "controller",
                    "service": "user-service"
                }
            ]
        """;
        
        // Configure MockServer expectation
        mockServer
            .when(
                request()
                    .withMethod("GET")
                    .withPath("/snippets")
                    .withQueryStringParameter("type", "controller")
                    .withQueryStringParameter("service", "user-service")
            )
            .respond(
                response()
                    .withStatusCode(200)
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withBody(jsonResponse)
            );
        
        // Act
        SnippetSearchResponse snippets = dataHubClient.getSnippets("controller", "user-service");
        
        // Assert
        assertNotNull(snippets);
        assertEquals(1, snippets.getTotal());
        assertEquals(1, snippets.getResults().size());
        assertEquals("controller", snippets.getResults().get(0).getType());
        assertEquals("user-service", snippets.getResults().get(0).getService());
    }
    
    @Test
    public void testGetDocumentById() {
        // Arrange
        String docId = "doc123";
        
        String jsonResponse = """
            {
                "title": "Spring Boot Guide",
                "doc_id": "doc123",
                "tags": ["java", "spring"],
                "owner": "john.doe",
                "content": "This is a comprehensive guide to Spring Boot...",
                "created_at": "2024-01-15T10:30:00Z",
                "updated_at": "2024-01-20T14:45:00Z"
            }
            """;
        
        // Configure MockServer expectation
        mockServer
            .when(
                request()
                    .withMethod("GET")
                    .withPath("/docs/" + docId)
            )
            .respond(
                response()
                    .withStatusCode(200)
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withBody(jsonResponse)
            );
        
        // Act
        DataHubDocument document = dataHubClient.getDocumentById(docId);
        
        // Assert
        assertNotNull(document);
        assertEquals("Spring Boot Guide", document.getTitle());
        assertEquals("doc123", document.getDocId());
        assertEquals("john.doe", document.getOwner());
        assertEquals(2, document.getTags().length);
        assertEquals("java", document.getTags()[0]);
        assertEquals("spring", document.getTags()[1]);
        assertEquals("This is a comprehensive guide to Spring Boot...", document.getContent());
        assertEquals("2024-01-15T10:30:00Z", document.getCreatedAt());
        assertEquals("2024-01-20T14:45:00Z", document.getUpdatedAt());
    }

    @Test
    public void testGetSnippetById() {
        // Arrange
        String snippetId = "snippet123";
        
        String jsonResponse = """
            {
                "title": "REST API Example",
                "snippet_id": "snippet123",
                "language": "java",
                "type": "controller",
                "service": "user-service",
                "description": "Example of a REST API controller",
                "code": "@RestController\\npublic class UserController {\\n    // implementation\\n}"
            }
            """;
        
        // Configure MockServer expectation
        mockServer
            .when(
                request()
                    .withMethod("GET")
                    .withPath("/snippets/" + snippetId)
            )
            .respond(
                response()
                    .withStatusCode(200)
                    .withContentType(MediaType.APPLICATION_JSON)
                    .withBody(jsonResponse)
            );
        
        // Act
        DataHubSnippet snippet = dataHubClient.getSnippetById(snippetId);
        
        // Assert
        assertNotNull(snippet);
        assertEquals("REST API Example", snippet.getTitle());
        assertEquals("snippet123", snippet.getSnippetId());
        assertEquals("java", snippet.getLanguage());
        assertEquals("controller", snippet.getType());
        assertEquals("user-service", snippet.getService());
        assertEquals("Example of a REST API controller", snippet.getDescription());
        assertEquals("@RestController\npublic class UserController {\n    // implementation\n}", snippet.getCode());
    }
}
