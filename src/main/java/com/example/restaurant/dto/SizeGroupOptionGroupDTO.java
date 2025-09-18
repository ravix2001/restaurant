package com.example.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SizeGroupOptionGroupDTO {

    private Long sizeGroupOptionGroupId;
    private Long sizeGroupId;
    private Long optionGroupId;
//    private List<OptionDTO> options;
    private List<SizeOptionDTO> options;
}
