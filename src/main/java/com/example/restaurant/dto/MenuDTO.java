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

    // Add this field to include sizes in the response
    private List<SizeDTO> sizes;

    // Add this field to include optionGroups
    private List<OptionGroupDTO> optionGroups;

    private List<OptionDTO> options;
    private List<OptionDTO> removedOptions;


}
