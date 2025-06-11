package com.example.quotation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DiscountSuggestionResponse {
    private List<Suggestion> suggestions;

    @Data
    @AllArgsConstructor
    public static class Suggestion {
        private String product;
        private String strategy;
        private String note;
    }
}
