package com.example.restaurant.repository;

import com.example.restaurant.entity.MenuOptionDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuOptionRepository extends JpaRepository<MenuOptionDB, Long> {
}
