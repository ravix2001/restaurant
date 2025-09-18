package com.example.restaurant.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SizeDTO {

    private Long id;
    private String name;
    private Long sizeGroupId;
    private BigDecimal price;
}
