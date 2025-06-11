package com.example.quotation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Map;

@Data
@AllArgsConstructor
public class ToolDescriptor {
    private String name;
    private String description;
    private Map<String, String> params;
}