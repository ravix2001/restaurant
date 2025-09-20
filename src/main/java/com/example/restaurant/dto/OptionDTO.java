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
    private Boolean isSelected;
    private List<OptionDTO> options;
    private List<SizeOptionDTO> sizes;      // per size pricing

    public OptionDTO() {}

    public OptionDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public OptionDTO(Long optionId, String name, Long dummy) {
        this.optionId = optionId;
        this.name = name;
    }

}
