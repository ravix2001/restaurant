package com.example.restaurant.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ModifierMapper {
    ModifierMapper INSTANCE = Mappers.getMapper(ModifierMapper.class);

    @Mapping(target = "itemId", source = "item.itemId")
    ModifierDTO toDTO(Modifier modifier);

    @Mapping(target = "item", ignore = true)
    Modifier toEntity(ModifierDTO dto);
}
