package com.example.restaurant.repository;

import com.example.restaurant.entity.MenuOptionDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuOptionRepository extends JpaRepository<MenuOptionDB, Long> {

    List<MenuOptionDB> findByMenuId(Long menuId);

    List<MenuOptionDB> findByMenuIdAndOptionId(Long menuId, Long optionId);

    void deleteByMenuIdAndOptionId(Long menuId, Long optionId);

    List<MenuOptionDB> findAllByMenuId(Long menuId);

    MenuOptionDB findByOptionId(Long optionId);
}
