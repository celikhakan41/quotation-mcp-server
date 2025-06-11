package com.example.quotation.dto;

import lombok.Data;

import java.util.List;

@Data
public class DiscountSuggestionRequest {
    private List<String> products;
}
