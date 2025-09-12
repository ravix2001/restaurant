package com.example.restaurant.dto;

import lombok.Data;

import java.util.List;

@Data
public class SizeGroupDTO {
    private Long id;
    private String name;
    private String description;
    private Long categoryId;
    private List<Long> sizeIds;
}
