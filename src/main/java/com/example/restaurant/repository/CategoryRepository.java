package com.example.restaurant.repository;

import com.example.restaurant.dto.CategoryDTO;
import com.example.restaurant.entity.CategoryDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryDB, Long> {

    @Query("SELECT new com.example.restaurant.dto.CategoryDTO(c.id, c.name, c.description) FROM CategoryDB c")
    List<CategoryDTO> getAllCategories();

}
