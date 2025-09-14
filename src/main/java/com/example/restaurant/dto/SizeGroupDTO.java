package com.example.restaurant.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SizeGroupDTO {

    private Long id;
    private String name;
    private String description;
    private List<SizeDTO> sizes;

}
