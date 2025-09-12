package com.example.restaurant.mapper;

import com.example.restaurant.dto.CategoryDTO;
import com.example.restaurant.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(target = "itemIds", expression = "java(category.getItems() != null ? category.getItems().stream().map(i -> i.getItemId()).collect(Collectors.toList()) : null)")
    CategoryDTO toDTO(Category category);

    @Mapping(target = "items", ignore = true)
    Category toEntity(CategoryDTO dto);
}
