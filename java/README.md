# DataHub MCP Server

## Project Structure

The project is organized into the following directories:
```
src/main/java/com/datahub/
└── DataHubMCPApplication.java    # Spring Boot application entry point

src/main/resources/
├── application.properties      # Application configuration
└── application.yml             # Alternative YAML configuration

pom.xml                         # Maven dependencies and project configuration
```


## Prerequisites

Before running this project, ensure you have the following installed:

- **JDK 17**: [Download JDK 17](https://www.oracle.com/java/technologies/downloads/#java17) or use [OpenJDK 17](https://jdk.java.net/17/)
- **Maven 3.6+**: [Download Maven](https://maven.apache.org/download.cgi) and follow the [installation guide](https://maven.apache.org/install.html)

Verify your installations:
```bash
java -version  # Should show Java 17
mvn -version   # Should show Maven 3.6 or higher
```


## Dev guide

To set up the project, you must follow these steps:
```bash
mvn clean install
```


To launch the Spring Boot application, use the following command:

```bash
mvn spring-boot:run
```

You should see some logs indicating the MCP server is up and running:
```logs
[...]
Registered tools: 1
[...]
Tomcat started on port 8001 (http) with context path '/'
[...]
Started McpServerApplication
```