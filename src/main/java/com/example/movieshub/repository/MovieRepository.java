package com.example.movieshub.repository;

import com.example.movieshub.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
	
	boolean existsByTitleIgnoreCase(String title);

}
