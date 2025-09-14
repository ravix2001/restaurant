package com.example.restaurant.service;

import com.example.restaurant.dto.MenuSizeDTO;
import com.example.restaurant.entity.MenuSizeDB;

import java.util.List;

public interface MenuSizeService {

    MenuSizeDB setPrice(Long menuId, MenuSizeDTO menuSizeDTO);
    void removePrice(Long menuId, Long sizeId);
    List<MenuSizeDB> listForMenu(Long menuId);

}
