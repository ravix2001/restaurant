package com.example.restaurant.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PriceDTO {
    private Long priceId;
    private Long itemId;
    private BigDecimal price;
    private String currency;

}
