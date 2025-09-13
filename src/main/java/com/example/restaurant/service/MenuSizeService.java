package com.example.restaurant.service;

import com.example.restaurant.dto.MenuSizeDTO;

import java.util.List;

public interface MenuSizeService {

    MenuSizeDTO setPrice(Long itemId, MenuSizeDTO dto);
    void removePrice(Long itemId, Long sizeId);
    List<MenuSizeDTO> listForItem(Long itemId);

}
