package com.example.restaurant.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class MenuDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal basePrice;
    private Long categoryId;
    private Long sizeGroupId;

    private List<MenuSizeDTO> menuSizes;
    private List<OptionDTO> options;
    private List<OptionDTO> removedOptions;

}
