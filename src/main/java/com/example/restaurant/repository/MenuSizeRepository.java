package com.example.restaurant.repository;

import com.example.restaurant.entity.MenuSizeDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuSizeRepository extends JpaRepository<MenuSizeDB, Long> {

    List<MenuSizeDB> findByMenuId(Long menuId);

}
