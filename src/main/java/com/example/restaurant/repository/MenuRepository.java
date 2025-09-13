package com.example.restaurant.repository;

import com.example.restaurant.entity.MenuDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<MenuDB, Long> {
}
