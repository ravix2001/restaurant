package com.example.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SizeOptionDTO {

    private Long id;
    private BigDecimal price;
    private Long sizeId;
    private Long optionId;
//    private List<SizeOptionDTO> sizes;

    public SizeOptionDTO() {}

    public SizeOptionDTO(Long sizeId, BigDecimal price) {
        this.id = sizeId;
        this.price = price;
    }

}
