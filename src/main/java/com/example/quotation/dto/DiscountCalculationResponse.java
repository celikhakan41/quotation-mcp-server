package com.example.quotation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DiscountCalculationResponse {
    private List<ItemDetail> items;
    private double totalBeforeDiscount;
    private double totalDiscount;
    private double totalAfterDiscount;

    @Data
    @AllArgsConstructor
    public static class ItemDetail {
        private String product;
        private int quantity;
        private double unitPrice;
        private double discountRate;
        private double lineTotal;
    }
}
