package com.example.restaurant.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PriceMapper {
    PriceMapper INSTANCE = Mappers.getMapper(PriceMapper.class);

    @Mapping(target = "itemId", source = "item.itemId")
    PriceDTO toDTO(Price price);

    @Mapping(target = "item", ignore = true)
    Price toEntity(PriceDTO dto);
}
