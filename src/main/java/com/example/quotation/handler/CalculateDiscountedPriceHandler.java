package com.example.quotation.handler;

import com.example.quotation.dto.DiscountCalculationRequest;
import com.example.quotation.dto.DiscountCalculationResponse;
import com.example.quotation.service.QuotationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CalculateDiscountedPriceHandler implements MCPHandler {

    private final QuotationService service;
    private final ObjectMapper mapper = new ObjectMapper();

    public CalculateDiscountedPriceHandler(QuotationService service) {
        this.service = service;
    }

    @Override
    public String methodName() {
        return "calculateDiscountedPrice";
    }

    @Override
    public Map<String, Object> handle(Map<String, Object> params) {
        DiscountCalculationRequest request = mapper.convertValue(params, DiscountCalculationRequest.class);
        DiscountCalculationResponse response = service.calculateDiscountedPrice(request);
        return Map.of("result", response);
    }
}
