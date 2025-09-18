package com.example.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal basePrice;
    private Long categoryId;
    private Long sizeGroupId;

    private List<MenuSizeDTO> menuSizes;

    private List<SizeDTO> sizes;

    private List<OptionDTO> menuOptions;

    private List<OptionGroupDTO> optionGroups;

    private List<OptionDTO> removedOptions;


}
