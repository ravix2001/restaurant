package com.example.restaurant.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface MenuMapper {
    MenuMapper INSTANCE = Mappers.getMapper(MenuMapper.class);

    @Mapping(target = "itemIds", expression = "java(menu.getItems() != null ? menu.getItems().stream().map(i -> i.getItemId()).collect(Collectors.toList()) : null)")
    MenuDTO toDTO(Menu menu);

    @Mapping(target = "items", ignore = true) // handled in Service
    Menu toEntity(MenuDTO dto);
}
