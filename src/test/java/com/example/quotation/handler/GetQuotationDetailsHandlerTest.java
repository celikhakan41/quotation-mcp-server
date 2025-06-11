package com.example.quotation.handler;

import com.example.quotation.dto.QuotationResponse;
import com.example.quotation.service.QuotationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetQuotationDetailsHandlerTest {

    private QuotationService quotationService;
    private GetQuotationDetailsHandler handler;

    @BeforeEach
    void setUp() {
        quotationService = mock(QuotationService.class);
        handler = new GetQuotationDetailsHandler(quotationService);
    }

    @Test
    void testHandleValidQuotationId() {
        String testId = "valid-id";
        Map<String, Object> params = Map.of("quotationId", testId);

        QuotationResponse mockResponse = new QuotationResponse(
                testId, "Test A.Ş.", "EUR",
                List.of(new QuotationResponse.ItemPrice("Service", 1, 1000.0, 1000.0)),
                1000.0
        );

        when(quotationService.getQuotationDetails(testId)).thenReturn(mockResponse);

        Map<String, Object> result = handler.handle(params);

        assertNotNull(result.get("result"));
        QuotationResponse response = (QuotationResponse) result.get("result");
        assertEquals("Test A.Ş.", response.getCustomerName());
        assertEquals("EUR", response.getCurrency());
        verify(quotationService).getQuotationDetails(testId);
    }

    @Test
    void testHandleWithNonExistentQuotationId() {
        String invalidId = "not-found-id";
        Map<String, Object> params = Map.of("quotationId", invalidId);

        when(quotationService.getQuotationDetails(invalidId)).thenThrow(new IllegalArgumentException("Quotation not found"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            handler.handle(params);
        });

        assertTrue(exception.getMessage().contains("Quotation not found"));
    }
}
