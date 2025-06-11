package com.example.quotation.service;

import com.example.quotation.dto.CreateQuotationRequest;
import com.example.quotation.dto.DiscountCalculationRequest;
import com.example.quotation.dto.DiscountCalculationResponse;
import com.example.quotation.dto.QuotationResponse;
import com.example.quotation.dto.QuotationResponse.ItemPrice;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuotationService {

    private final Map<String, QuotationResponse> quotationStore = new HashMap<>();

    public QuotationResponse createQuotation(CreateQuotationRequest request) {
        List<ItemPrice> itemPrices = new ArrayList<>();
        double total = 0.0;

        for (CreateQuotationRequest.QuotationItem item : request.getItems()) {
            double unitPrice = getPriceForProduct(item.getProduct());
            double lineTotal = unitPrice * item.getQuantity();
            itemPrices.add(new ItemPrice(item.getProduct(), item.getQuantity(), unitPrice, lineTotal));
            total += lineTotal;
        }

        String id = UUID.randomUUID().toString();
        QuotationResponse response = new QuotationResponse(
                id,
                request.getCustomerName(),
                request.getCurrency(),
                itemPrices,
                total
        );

        quotationStore.put(id, response);
        return response;
    }

    private double getPriceForProduct(String product) {
        return switch (product.toLowerCase()) {
            case "premium package" -> 1200.0;
            case "extra module" -> 450.0;
            default -> 999.0;
        };
    }

    public QuotationResponse getQuotationDetails(String quotationId) {
        QuotationResponse result = quotationStore.get(quotationId);
        if (result == null) {
            throw new IllegalArgumentException("Teklif bulunamadı: " + quotationId);
        }
        return result;
    }

    public DiscountCalculationResponse calculateDiscountedPrice(DiscountCalculationRequest request) {
        List<DiscountCalculationResponse.ItemDetail> resultItems = new ArrayList<>();
        double totalBefore = 0.0;
        double totalDiscount = 0.0;

        for (DiscountCalculationRequest.Item item : request.getItems()) {
            double unitPrice = getPriceForProduct(item.getProduct());
            double discountRate = item.getQuantity() >= 5 ? 0.10 : 0.0;
            double lineTotal = unitPrice * item.getQuantity() * (1 - discountRate);
            double lineBefore = unitPrice * item.getQuantity();

            resultItems.add(new DiscountCalculationResponse.ItemDetail(
                    item.getProduct(),
                    item.getQuantity(),
                    unitPrice,
                    discountRate,
                    lineTotal
            ));

            totalBefore += lineBefore;
            totalDiscount += (lineBefore - lineTotal);
        }

        return new DiscountCalculationResponse(resultItems, totalBefore, totalDiscount, totalBefore - totalDiscount);
    }
}
