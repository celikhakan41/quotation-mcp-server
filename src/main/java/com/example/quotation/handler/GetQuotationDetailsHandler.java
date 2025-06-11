package com.example.quotation.handler;

import com.example.quotation.dto.GetQuotationRequest;
import com.example.quotation.dto.QuotationResponse;
import com.example.quotation.service.QuotationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GetQuotationDetailsHandler implements MCPHandler {

    private final QuotationService service;
    private final ObjectMapper mapper = new ObjectMapper();

    public GetQuotationDetailsHandler(QuotationService service) {
        this.service = service;
    }

    @Override
    public String methodName() {
        return "getQuotationDetails";
    }

    @Override
    public Map<String, Object> handle(Map<String, Object> params) {
        GetQuotationRequest request = mapper.convertValue(params, GetQuotationRequest.class);
        QuotationResponse response = service.getQuotationDetails(request.getQuotationId());
        return Map.of("result", response);
    }
}
