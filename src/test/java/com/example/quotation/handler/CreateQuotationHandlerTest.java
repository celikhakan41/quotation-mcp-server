package com.example.quotation.handler;

import com.example.quotation.dto.QuotationResponse;
import com.example.quotation.service.QuotationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateQuotationHandlerTest {

    private QuotationService quotationService;
    private CreateQuotationHandler handler;

    @BeforeEach
    void setUp() {
        quotationService = mock(QuotationService.class);
        handler = new CreateQuotationHandler(quotationService);
    }

    @Test
    void testHandleValidRequest() {
        Map<String, Object> params = Map.of(
                "customerName", "ABC Ltd.",
                "currency", "TRY",
                "items", List.of(
                        Map.of("product", "Premium Package", "quantity", 2),
                        Map.of("product", "Extra Module", "quantity", 1)
                )
        );

        QuotationResponse mockResponse = new QuotationResponse(
                "mock-id", "ABC Ltd.", "TRY",
                List.of(
                        new QuotationResponse.ItemPrice("Premium Package", 2, 1200.0, 2400.0),
                        new QuotationResponse.ItemPrice("Extra Module", 1, 450.0, 450.0)
                ),
                2850.0
        );

        when(quotationService.createQuotation(any())).thenReturn(mockResponse);

        Map<String, Object> result = handler.handle(params);

        assertNotNull(result.get("result"));
        QuotationResponse response = (QuotationResponse) result.get("result");
        assertEquals("ABC Ltd.", response.getCustomerName());
        assertEquals(2850.0, response.getTotalPrice());
        verify(quotationService, times(1)).createQuotation(any());
    }

    @Test
    void testHandleWithEmptyItems() {
        Map<String, Object> params = Map.of(
                "customerName", "XYZ Inc.",
                "currency", "USD",
                "items", List.of()
        );

        QuotationResponse emptyResponse = new QuotationResponse(
                "mock-id", "XYZ Inc.", "USD",
                List.of(),
                0.0
        );

        when(quotationService.createQuotation(any())).thenReturn(emptyResponse);

        Map<String, Object> result = handler.handle(params);
        QuotationResponse response = (QuotationResponse) result.get("result");

        assertEquals("XYZ Inc.", response.getCustomerName());
        assertEquals(0.0, response.getTotalPrice());
        assertTrue(response.getItems().isEmpty());
    }
}