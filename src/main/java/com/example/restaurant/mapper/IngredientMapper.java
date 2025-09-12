package com.example.restaurant.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    IngredientMapper INSTANCE = Mappers.getMapper(IngredientMapper.class);

    @Mapping(target = "itemId", source = "item.itemId")
    IngredientDTO toDTO(Ingredient ingredient);

    @Mapping(target = "item", ignore = true)
    Ingredient toEntity(IngredientDTO dto);
}
