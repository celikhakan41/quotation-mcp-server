package com.example.quotation.handler;

import java.util.Map;

public interface MCPHandler {
    String methodName();
    Map<String, Object> handle(Map<String, Object> params);
}
