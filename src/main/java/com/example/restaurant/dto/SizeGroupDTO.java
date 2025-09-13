package com.example.restaurant.dto;

import com.example.restaurant.entity.CategoryDB;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SizeGroupDTO {

    private Long id;
    private String name;
    private String description;
    private Long categoryId;

}
