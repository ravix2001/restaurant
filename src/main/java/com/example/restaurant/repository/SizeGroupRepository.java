package com.example.restaurant.repository;

import com.example.restaurant.entity.SizeGroupDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SizeGroupRepository extends JpaRepository<SizeGroupDB, Long> {

    List<SizeGroupDB> findByCategoryId(Long categoryId);

}
