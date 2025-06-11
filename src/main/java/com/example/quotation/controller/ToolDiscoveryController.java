package com.example.quotation.controller;

import com.example.quotation.dto.ToolDescriptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ToolDiscoveryController {

    @GetMapping("/mcp/tools")
    public List<ToolDescriptor> listTools() {
        return List.of(
                new ToolDescriptor(
                        "createQuotation",
                        "Creates a new proposal.",
                        Map.of(
                                "customerName", "Customer name",
                                "currency", "Currency (TRY, USD...)",
                                "items", "Product and quantity list"
                        )
                ),
                new ToolDescriptor(
                        "getQuotationDetails",
                        "Gets details according to the offer ID.",
                        Map.of("quotationId", "Offer UUID")
                ),
                new ToolDescriptor(
                        "calculateDiscountedPrice",
                        "Calculates the discounted price.",
                        Map.of("items", "Product and quantity list")
                ),
                new ToolDescriptor(
                        "getSuggestedDiscounts",
                        "Rotates the suggested discount strategies according to the product list.",
                        Map.of("products", "Product name list")
                )
        );
    }
}