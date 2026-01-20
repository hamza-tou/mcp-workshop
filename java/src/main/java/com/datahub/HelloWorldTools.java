package com.datahub;

import org.springaicommunity.mcp.annotation.McpTool;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldTools {

    @McpTool(name = "hello", description = "A simple hello world tool that returns a greeting message")
    public String hello() {
        return "Hello mcp server here !";
    }
}
