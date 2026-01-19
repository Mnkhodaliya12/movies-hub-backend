package com.example.movieshub.repository;

import com.example.movieshub.entity.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DealRepository extends JpaRepository<Deal, Long> {

    List<Deal> findByActiveTrue();
}
