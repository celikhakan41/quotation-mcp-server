package com.example.quotation.service;

import com.example.quotation.dto.*;
import com.example.quotation.model.QuotationEntity;
import com.example.quotation.model.QuotationItemEntity;
import com.example.quotation.repository.QuotationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuotationService {

    @Autowired
    private QuotationRepository quotationRepository;

    public QuotationResponse createQuotation(CreateQuotationRequest request) {
        String quotationId = UUID.randomUUID().toString();

        QuotationEntity quotationEntity = new QuotationEntity();
        quotationEntity.setId(quotationId);
        quotationEntity.setCustomerName(request.getCustomerName());
        quotationEntity.setCurrency(request.getCurrency());

        List<QuotationItemEntity> itemEntities = new ArrayList<>();
        double total = 0.0;

        for (CreateQuotationRequest.QuotationItem item : request.getItems()) {
            double unitPrice = getPriceForProduct(item.getProduct());
            double lineTotal = unitPrice * item.getQuantity();

            QuotationItemEntity itemEntity = QuotationItemEntity.builder()
                    .product(item.getProduct())
                    .quantity(item.getQuantity())
                    .unitPrice(unitPrice)
                    .lineTotal(lineTotal)
                    .quotation(quotationEntity)
                    .build();

            itemEntities.add(itemEntity);
            total += lineTotal;
        }

        quotationEntity.setItems(itemEntities);
        quotationEntity.setTotalPrice(total);

        quotationRepository.save(quotationEntity);

        List<QuotationResponse.ItemPrice> items = itemEntities.stream()
                .map(e -> new QuotationResponse.ItemPrice(
                        e.getProduct(),
                        e.getQuantity(),
                        e.getUnitPrice(),
                        e.getLineTotal()
                ))
                .toList();

        return new QuotationResponse(
                quotationId,
                request.getCustomerName(),
                request.getCurrency(),
                items,
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

    public QuotationResponse getQuotationDetails(String quotationId) {
        QuotationEntity quotation = quotationRepository.findById(quotationId)
                .orElseThrow(() -> new IllegalArgumentException("No proposal found: " + quotationId));

        List<QuotationResponse.ItemPrice> items = quotation.getItems().stream()
                .map(e -> new QuotationResponse.ItemPrice(
                        e.getProduct(),
                        e.getQuantity(),
                        e.getUnitPrice(),
                        e.getLineTotal()
                ))
                .toList();

        return new QuotationResponse(
                quotation.getId(),
                quotation.getCustomerName(),
                quotation.getCurrency(),
                items,
                quotation.getTotalPrice()
        );
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

    public DiscountSuggestionResponse getSuggestedDiscounts(DiscountSuggestionRequest request) {
        List<DiscountSuggestionResponse.Suggestion> result = new ArrayList<>();

        for (String product : request.getProducts()) {
            String strategy;
            String note;

            switch (product.toLowerCase()) {
                case "premium package" -> {
                    strategy = "Buy 3 or more → 15% off";
                    note = "Special advantage for businesses.";
                }
                case "extra module" -> {
                    strategy = "Buy 5+ → 10% discount + free installation";
                    note = "Special incentive for modular construction.";
                }
                default -> {
                    strategy = "Standard pricing";
                    note = "There is no special discount for this product.";
                }
            }

            result.add(new DiscountSuggestionResponse.Suggestion(product, strategy, note));
        }

        return new DiscountSuggestionResponse(result);
    }
}
