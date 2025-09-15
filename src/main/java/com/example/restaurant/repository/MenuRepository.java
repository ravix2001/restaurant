package com.example.restaurant.repository;

import com.example.restaurant.entity.MenuDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<MenuDB, Long> {
    List<MenuDB> findByCategoryId(Long categoryId);
}
