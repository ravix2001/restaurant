package com.example.restaurant.repository;

import com.example.restaurant.dto.OptionGroupDTO;
import com.example.restaurant.entity.OptionGroupDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OptionGroupRepository extends JpaRepository<OptionGroupDB, Long> {
}
