package com.example.restaurant.repository;

import com.example.restaurant.entity.CategoryDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryDB, Long> {
}
