package com.example.restaurant.dto;

import lombok.Data;

@Data
public class ItemDto {

    private Long id;
    private String name;
    private String description;
    private int quantity;
    private Double price;

}
