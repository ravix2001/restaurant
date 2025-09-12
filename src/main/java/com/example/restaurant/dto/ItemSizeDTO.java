package com.example.restaurant.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemSizeDTO {
    private Long id;       // optional for create
    private Long sizeId;
    private BigDecimal price;
}
