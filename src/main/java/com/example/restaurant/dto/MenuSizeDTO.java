package com.example.restaurant.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MenuSizeDTO {

    private Long id;
    private Long menuId;
    private Long sizeId;
    private BigDecimal price;

}
