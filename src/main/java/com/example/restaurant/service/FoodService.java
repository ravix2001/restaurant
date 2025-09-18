package com.example.restaurant.service;

import com.example.restaurant.dto.*;

import java.util.List;
import java.util.Map;

public interface FoodService {

    String createMenuAndSize(MenuDTO menuDTO);

    String updateMenuAndSize(MenuDTO menuDTO);

    MenuDTO getMenuWithOptions(Long menuId);

    MenuDTO getMenuOptionsDetailed(Long menuId);

    MenuDTO handleMenuOptionsDetailed(MenuDTO menuDTO);

    MenuDTO handleMenuOptions(MenuDTO menuDTO);

    List<MenuDTO> getAllMenu();

    List<CategoryDTO> getAllCategory();

//    Map<String, Object> getExtraPrices(Long sizeGroupOptionGroupId);

    SizeGroupOptionGroupDTO getExtraPrices(Long sizeGroupOptionGroupId);

    SizeGroupOptionGroupDTO handleExtraPrices(SizeGroupOptionGroupDTO options);

//    String handleExtraPrices(SizeGroupOptionGroupDTO options);

//    List<OptionDTO> getExtraPrices(Long sizeGroupOptionGroupId);

//    void handleExtraPrices(SizeGroupOptionGroupDTO options);
}
