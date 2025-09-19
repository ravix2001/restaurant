package com.example.restaurant.repository;

import com.example.restaurant.entity.MenuOptionDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuOptionRepository extends JpaRepository<MenuOptionDB, Long> {

    List<MenuOptionDB> findByMenuId(Long menuId);

    void deleteByMenuIdAndOptionId(Long menuId, Long optionId);

    MenuOptionDB findByOptionId(Long optionId);
}
