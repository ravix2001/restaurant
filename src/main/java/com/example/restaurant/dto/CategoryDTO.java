package com.example.restaurant.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryDTO {

    private Long id;
    private String name;
    private String description;
    List<MenuDTO> menus;

}
