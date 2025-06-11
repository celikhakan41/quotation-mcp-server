package com.example.quotation.service;

import com.example.quotation.dto.CreateQuotationRequest;
import com.example.quotation.dto.QuotationResponse;
import com.example.quotation.dto.QuotationResponse.ItemPrice;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class QuotationService {

    public QuotationResponse createQuotation(CreateQuotationRequest request) {
        List<ItemPrice> itemPrices = new ArrayList<>();
        double total = 0.0;

        for (CreateQuotationRequest.QuotationItem item : request.getItems()) {
            double unitPrice = getPriceForProduct(item.getProduct());
            double lineTotal = unitPrice * item.getQuantity();
            itemPrices.add(new ItemPrice(item.getProduct(), item.getQuantity(), unitPrice, lineTotal));
            total += lineTotal;
        }

        return new QuotationResponse(
                UUID.randomUUID().toString(),
                request.getCustomerName(),
                request.getCurrency(),
                itemPrices,
                total
        );
    }

    private double getPriceForProduct(String product) {
        return switch (product.toLowerCase()) {
            case "premium package" -> 1200.0;
            case "extra module" -> 450.0;
            default -> 999.0;
        };
    }
}
