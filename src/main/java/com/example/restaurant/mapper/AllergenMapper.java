package com.example.restaurant.mapper;

import com.example.restaurant.dto.AllergenDTO;
import com.example.restaurant.entity.Allergen;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AllergenMapper {
    AllergenMapper INSTANCE = Mappers.getMapper(AllergenMapper.class);

    @Mapping(target = "itemId", source = "item.itemId")
    AllergenDTO toDTO(Allergen allergen);

    @Mapping(target = "item", ignore = true)
    Allergen toEntity(AllergenDTO dto);
}
