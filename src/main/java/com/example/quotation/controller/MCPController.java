package com.example.quotation.controller;

import com.example.quotation.handler.MCPHandler;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/mcp")
public class MCPController {

    private final Map<String, MCPHandler> handlerMap = new HashMap<>();

    public MCPController(List<MCPHandler> handlers) {
        for (MCPHandler handler : handlers) {
            handlerMap.put(handler.methodName(), handler);
        }
    }

    @PostMapping
    public Map<String, Object> handle(@RequestBody Map<String, Object> payload) {
        String method = (String) payload.get("method");
        Object id = payload.get("id");
        Map<String, Object> params = (Map<String, Object>) payload.get("params");

        Map<String, Object> response = new HashMap<>();
        response.put("jsonrpc", "2.0");
        response.put("id", id);

        try {
            MCPHandler handler = handlerMap.get(method);
            if (handler == null) {
                response.put("error", Map.of("code", -32601, "message", "Method not found: " + method));
            } else {
                response.putAll(handler.handle(params));
            }
        } catch (Exception e) {
            response.put("error", Map.of("code", -32000, "message", e.getMessage()));
        }

        return response;
    }
}
