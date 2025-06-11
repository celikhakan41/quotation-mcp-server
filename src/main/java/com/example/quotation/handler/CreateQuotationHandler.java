package com.example.quotation.handler;

import com.example.quotation.dto.CreateQuotationRequest;
import com.example.quotation.dto.QuotationResponse;
import com.example.quotation.service.QuotationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CreateQuotationHandler implements MCPHandler {

    private final QuotationService service;
    private final ObjectMapper mapper;

    public CreateQuotationHandler(QuotationService service) {
        this.service = service;
        this.mapper = new ObjectMapper();
    }

    @Override
    public String methodName() {
        return "createQuotation";
    }

    @Override
    public Map<String, Object> handle(Map<String, Object> params) {
        CreateQuotationRequest request = mapper.convertValue(params, CreateQuotationRequest.class);
        QuotationResponse response = service.createQuotation(request);
        return Map.of("result", response);
    }
}
