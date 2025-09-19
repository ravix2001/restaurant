package com.example.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OptionDTO {

    private Long id;
    private String name;
    private Long optionId;
    private Long optionGroupId;
    private List<SizeOptionDTO> sizes;
    private boolean isSelected;
    private List<OptionDTO> options;
}
