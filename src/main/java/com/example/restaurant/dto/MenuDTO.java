package com.example.restaurant.dto;

import lombok.Data;

import java.util.List;

@Data
public class MenuDTO {
    private Long menuId;
    private String name;
    private String description;
    private String availability;
    private List<Long> itemIds; // Only send item IDs to avoid recursion

}
