package com.example.restaurant.mapper;

import com.example.restaurant.dto.ItemDTO;
import com.example.restaurant.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    @Mapping(target = "categoryId", source = "category.categoryId")
    @Mapping(target = "menuId", source = "menu.menuId")
    @Mapping(target = "ingredientIds", expression = "java(item.getIngredients() != null ? item.getIngredients().stream().map(i -> i.getIngredientId()).collect(Collectors.toList()) : null)")
    @Mapping(target = "modifierIds", expression = "java(item.getModifiers() != null ? item.getModifiers().stream().map(m -> m.getModifierId()).collect(Collectors.toList()) : null)")
    @Mapping(target = "allergenIds", expression = "java(item.getAllergens() != null ? item.getAllergens().stream().map(a -> a.getAllergenId()).collect(Collectors.toList()) : null)")
    @Mapping(target = "priceIds", expression = "java(item.getPrices() != null ? item.getPrices().stream().map(p -> p.getPriceId()).collect(Collectors.toList()) : null)")
    ItemDTO toDTO(Item item);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "menu", ignore = true)
    @Mapping(target = "ingredients", ignore = true)
    @Mapping(target = "modifiers", ignore = true)
    @Mapping(target = "allergens", ignore = true)
    @Mapping(target = "prices", ignore = true)
    Item toEntity(ItemDTO dto);
}
