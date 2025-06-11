package com.example.quotation.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateQuotationRequest {
    private String customerName;
    private List<QuotationItem> items;
    private String currency;

    @Data
    public static class QuotationItem {
        private String product;
        private int quantity;
    }
}
