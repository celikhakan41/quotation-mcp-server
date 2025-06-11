package com.example.quotation.handler;

import com.example.quotation.dto.DiscountSuggestionResponse;
import com.example.quotation.service.QuotationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetSuggestedDiscountsHandlerTest {

    private QuotationService quotationService;
    private GetSuggestedDiscountsHandler handler;

    @BeforeEach
    void setUp() {
        quotationService = mock(QuotationService.class);
        handler = new GetSuggestedDiscountsHandler(quotationService);
    }

    @Test
    void testHandleWithValidProducts() {
        Map<String, Object> params = Map.of(
                "products", List.of("premium package", "extra module")
        );

        DiscountSuggestionResponse.Suggestion suggestion1 = new DiscountSuggestionResponse.Suggestion(
                "premium package", "Buy 3 or more → 15% off", "Special advantage for businesses."
        );
        DiscountSuggestionResponse.Suggestion suggestion2 = new DiscountSuggestionResponse.Suggestion(
                "extra module", "Buy 5+ → 10% discount + free installation", "Special incentive for modular construction."
        );

        DiscountSuggestionResponse mockResponse = new DiscountSuggestionResponse(List.of(suggestion1, suggestion2));
        when(quotationService.getSuggestedDiscounts(any())).thenReturn(mockResponse);

        Map<String, Object> result = handler.handle(params);

        assertNotNull(result);
        assertTrue(result.containsKey("result"));

        DiscountSuggestionResponse response = (DiscountSuggestionResponse) result.get("result");
        assertEquals(2, response.getSuggestions().size());
        assertEquals("premium package", response.getSuggestions().get(0).getProduct());
        assertEquals("extra module", response.getSuggestions().get(1).getProduct());

        verify(quotationService).getSuggestedDiscounts(any());
    }

    @Test
    void testHandleWithUnknownProduct() {
        Map<String, Object> params = Map.of(
                "products", List.of("unknown product")
        );

        DiscountSuggestionResponse.Suggestion suggestion = new DiscountSuggestionResponse.Suggestion(
                "unknown product", "Standard pricing", "There is no special discount for this product."
        );

        DiscountSuggestionResponse mockResponse = new DiscountSuggestionResponse(List.of(suggestion));
        when(quotationService.getSuggestedDiscounts(any())).thenReturn(mockResponse);

        Map<String, Object> result = handler.handle(params);
        DiscountSuggestionResponse response = (DiscountSuggestionResponse) result.get("result");

        assertEquals(1, response.getSuggestions().size());
        assertEquals("unknown product", response.getSuggestions().get(0).getProduct());
        assertEquals("Standard pricing", response.getSuggestions().get(0).getStrategy());
    }

    @Test
    void testHandleWithEmptyProductList() {
        Map<String, Object> params = Map.of("products", List.of());

        DiscountSuggestionResponse mockResponse = new DiscountSuggestionResponse(List.of());
        when(quotationService.getSuggestedDiscounts(any())).thenReturn(mockResponse);

        Map<String, Object> result = handler.handle(params);
        DiscountSuggestionResponse response = (DiscountSuggestionResponse) result.get("result");

        assertTrue(response.getSuggestions().isEmpty());
        verify(quotationService).getSuggestedDiscounts(any());
    }
}
