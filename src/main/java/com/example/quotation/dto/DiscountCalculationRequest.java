package com.example.quotation.dto;

import lombok.Data;

import java.util.List;

@Data
public class DiscountCalculationRequest {
    private List<Item> items;

    @Data
    public static class Item {
        private String product;
        private int quantity;
    }
}
