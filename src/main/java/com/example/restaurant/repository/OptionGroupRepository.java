package com.example.restaurant.repository;

import com.example.restaurant.entity.OptionGroupDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionGroupRepository extends JpaRepository<OptionGroupDB, Long> {
}
