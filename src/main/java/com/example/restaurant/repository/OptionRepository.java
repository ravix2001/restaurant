package com.example.restaurant.repository;

import com.example.restaurant.entity.OptionDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<OptionDB, Long> {
}
