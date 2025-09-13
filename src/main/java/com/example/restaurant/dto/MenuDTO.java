package com.example.restaurant.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MenuDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal basePrice;
    private Long categoryId;

}
