package com.example.restaurant.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ItemDTO {
    private Long itemId;
    private String name;
    private String description;
    private BigDecimal price;
    private Long categoryId;
    private Long menuId;

    private List<Long> ingredientIds;
    private List<Long> modifierIds;
    private List<Long> allergenIds;
    private List<Long> priceIds;

}
