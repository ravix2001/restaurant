package com.example.restaurant.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ItemDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal basePrice;
    private Long categoryId;
    private List<ItemSizeDTO> sizes; // embed sizes for quick read

}
