package com.example.restaurant.repository;

import com.example.restaurant.entity.SizeGroupDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SizeGroupRepository extends JpaRepository<SizeGroupDB, Long> {
}
