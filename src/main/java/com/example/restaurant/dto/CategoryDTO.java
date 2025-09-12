package com.example.restaurant.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {
    private Long categoryId;
    private String name;
    private String description;
    private List<Long> itemIds;

}
