package com.example.quotation.handler;

import com.example.quotation.dto.DiscountCalculationResponse;
import com.example.quotation.service.QuotationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CalculateDiscountedPriceHandlerTest {

    private QuotationService quotationService;
    private CalculateDiscountedPriceHandler handler;

    @BeforeEach
    void setUp() {
        quotationService = mock(QuotationService.class);
        handler = new CalculateDiscountedPriceHandler(quotationService);
    }

    @Test
    void testHandleValidDiscountCalculation() {
        Map<String, Object> params = Map.of(
                "items", List.of(
                        Map.of("product", "premium package", "quantity", 5)
                )
        );

        DiscountCalculationResponse mockResponse = new DiscountCalculationResponse(
                Collections.emptyList(), 1000.0, 100.0, 900.0
        );

        when(quotationService.calculateDiscountedPrice(any())).thenReturn(mockResponse);

        Map<String, Object> result = handler.handle(params);
        DiscountCalculationResponse response = (DiscountCalculationResponse) result.get("result");

        assertNotNull(response);
        assertEquals(900.0, response.getTotalAfterDiscount());
        verify(quotationService).calculateDiscountedPrice(any());
    }

    @Test
    void testHandleWith100PercentDiscount() {
        Map<String, Object> params = Map.of(
                "items", List.of(
                        Map.of("product", "extra module", "quantity", 10)
                )
        );

        DiscountCalculationResponse mockResponse = new DiscountCalculationResponse(
                Collections.emptyList(), 500.0, 500.0, 0.0
        );

        when(quotationService.calculateDiscountedPrice(any())).thenReturn(mockResponse);

        Map<String, Object> result = handler.handle(params);
        DiscountCalculationResponse response = (DiscountCalculationResponse) result.get("result");

        assertEquals(0.0, response.getTotalAfterDiscount());
    }

    @Test
    void testHandleWithInvalidItem() {
        Map<String, Object> params = Map.of(
                "items", List.of(
                        Map.of("product", "unknown", "quantity", 1)
                )
        );

        when(quotationService.calculateDiscountedPrice(any()))
                .thenThrow(new IllegalArgumentException("Product not found"));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            handler.handle(params);
        });

        assertTrue(ex.getMessage().contains("Product not found"));
    }
}