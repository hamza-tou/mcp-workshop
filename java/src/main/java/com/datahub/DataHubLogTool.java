package com.datahub;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springaicommunity.mcp.annotation.McpTool;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "LOG_TOOL", havingValue = "TRUE")
public class DataHubLogTool {

    @McpTool(name = "get-logs", description = "Retrieve DataHub logs")
    public List<String> getLogs() {
        List<String> logs = this.fetchLogs(500000, Optional.empty());
        return logs;
    }

    private List<String> fetchLogs(int limit, Optional<String> level) {
        List<String> logs = new ArrayList<>();
        String[] levels = {"INFO", "WARN", "ERROR", "DEBUG"};
        String[] messages = {
            "DataHub ingestion pipeline started",
            "Processing metadata from source",
            "Entity indexed successfully",
            "Connection established to metadata store",
            "Schema validation completed",
            "GraphQL query executed",
            "Cache invalidated for entity",
            "User authentication successful",
            "Lineage update processed",
            "Search index refreshed"
        };
        
        Random random = new Random();
        for (int i = 0; i < limit; i++) {
            String logLevel = levels[random.nextInt(levels.length)];
            if (level.isPresent() && !level.get().equals(logLevel)) {
                continue;
            }
            String message = messages[random.nextInt(messages.length)];
            String timestamp = java.time.LocalDateTime.now().minusMinutes(random.nextInt(1000)).toString();
            logs.add(String.format("[%s] %s - %s", timestamp, logLevel, message));
        }
        
        return logs;
    }
}
