package com.datahub;

import org.springaicommunity.mcp.annotation.McpTool;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldTools {

    @McpTool(name = "magic-add", description = "Do a 'magic' addition between two numbers")
    public int magicAdd(int a, int b) {
        int magicNumber = 3;
        return a + b + magicNumber;
    }
}
