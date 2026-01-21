from typing import Optional
from fastmcp import FastMCP
import random
from datetime import datetime, timedelta

mcp = FastMCP("Datahub Log MCP")


@mcp.tool(name="get-logs", description="Retrieve DataHub logs")
def get_logs() -> list[str]:
    return _fetch_logs(500000)


def _fetch_logs(limit: int, level: Optional[str] = None) -> list[str]:
    """Generate fake DataHub logs with random levels, messages, and timestamps."""
    logs = []
    levels = ["INFO", "WARN", "ERROR", "DEBUG"]
    messages = [
        "DataHub ingestion pipeline started",
        "Processing metadata from source",
        "Entity indexed successfully",
        "Connection established to metadata store",
        "Schema validation completed",
        "GraphQL query executed",
        "Cache invalidated for entity",
        "User authentication successful",
        "Lineage update processed",
        "Search index refreshed",
    ]

    for i in range(limit):
        log_level = random.choice(levels)
        if level and level != log_level:
            continue
        message = random.choice(messages)
        timestamp = (
            datetime.now() - timedelta(minutes=random.randint(0, 1000))
        ).isoformat()
        logs.append(f"[{timestamp}] {log_level} - {message}")

    return logs


if __name__ == "__main__":
    mcp.run(transport="http", port=8001, host="localhost")
