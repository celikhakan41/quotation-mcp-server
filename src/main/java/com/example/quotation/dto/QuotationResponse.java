package com.example.quotation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QuotationResponse {
    private String quotationId;
    private String customerName;
    private String currency;
    private List<ItemPrice> items;
    private double totalPrice;

    @Data
    @AllArgsConstructor
    public static class ItemPrice {
        private String product;
        private int quantity;
        private double unitPrice;
        private double lineTotal;
    }
}
