package com.example.quotation.controller;

import com.example.quotation.dto.CreateQuotationRequest;
import com.example.quotation.dto.QuotationResponse;
import com.example.quotation.service.QuotationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mcp")
public class MCPController {

    @Autowired
    private QuotationService quotationService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping
    public Map<String, Object> handleMCPRequest(@RequestBody Map<String, Object> payload) {
        String method = (String) payload.get("method");
        Map<String, Object> params = (Map<String, Object>) payload.get("params");
        String id = payload.get("id").toString();

        Map<String, Object> response = new HashMap<>();
        response.put("jsonrpc", "2.0");
        response.put("id", id);

        if ("createQuotation".equals(method)) {
            CreateQuotationRequest request = objectMapper.convertValue(params, CreateQuotationRequest.class);
            QuotationResponse result = quotationService.createQuotation(request);
            response.put("result", result);
        } else {
            response.put("error", Map.of(
                    "code", -32601,
                    "message", "Method not found: " + method
            ));
        }

        return response;
    }
}
