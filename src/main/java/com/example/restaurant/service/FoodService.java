package com.example.restaurant.service;

import com.example.restaurant.dto.*;

import java.util.List;

public interface FoodService {

    MenuDTO createMenuAndSize(MenuDTO menuDTO);

    MenuDTO updateMenuAndSize(MenuDTO menuDTO);

    MenuDTO getMenuWithOptions(Long menuId);

    MenuDTO getMenuOptionsDetailed(Long menuId);

    MenuDTO handleMenuOptionsDetailed(MenuDTO menuDTO);

    MenuDTO handleMenuOptions(MenuDTO menuDTO);

    List<MenuDTO> getAllMenu();

    List<CategoryDTO> getAllCategory();

    SizeGroupOptionGroupDTO getExtraPrices(Long sizeGroupOptionGroupId);

    SizeGroupOptionGroupDTO handleExtraPrices(SizeGroupOptionGroupDTO options);

}
