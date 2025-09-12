package com.example.restaurant.dto;

import lombok.Data;

@Data
public class IngredientDTO {
    private Long ingredientId;
    private String name;
    private String description;
    private Long itemId;

}
