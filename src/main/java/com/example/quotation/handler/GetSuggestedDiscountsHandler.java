package com.example.quotation.handler;

import com.example.quotation.dto.DiscountSuggestionRequest;
import com.example.quotation.dto.DiscountSuggestionResponse;
import com.example.quotation.service.QuotationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GetSuggestedDiscountsHandler implements MCPHandler {

    private final QuotationService service;
    private final ObjectMapper mapper = new ObjectMapper();

    public GetSuggestedDiscountsHandler(QuotationService service) {
        this.service = service;
    }

    @Override
    public String methodName() {
        return "getSuggestedDiscounts";
    }

    @Override
    public Map<String, Object> handle(Map<String, Object> params) {
        DiscountSuggestionRequest request = mapper.convertValue(params, DiscountSuggestionRequest.class);
        DiscountSuggestionResponse response = service.getSuggestedDiscounts(request);
        return Map.of("result", response);
    }
}
