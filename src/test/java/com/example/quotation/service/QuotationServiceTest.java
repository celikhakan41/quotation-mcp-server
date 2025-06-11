package com.example.quotation.service;

import com.example.quotation.dto.CreateQuotationRequest;
import com.example.quotation.dto.QuotationResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuotationServiceTest {

    private final QuotationService service = new QuotationService();

    @Test
    void shouldCalculateTotalPriceCorrectly() {
        CreateQuotationRequest request = new CreateQuotationRequest();
        request.setCustomerName("ABC Ltd.");
        request.setCurrency("TRY");

        CreateQuotationRequest.QuotationItem item1 = new CreateQuotationRequest.QuotationItem();
        item1.setProduct("Premium Package");
        item1.setQuantity(2);

        CreateQuotationRequest.QuotationItem item2 = new CreateQuotationRequest.QuotationItem();
        item2.setProduct("Extra Module");
        item2.setQuantity(3);

        request.setItems(List.of(item1, item2));

        QuotationResponse response = service.createQuotation(request);

        assertEquals(3750.0, response.getTotalPrice());
        assertEquals(2, response.getItems().size());
        assertEquals("ABC Ltd.", response.getCustomerName());
    }
}