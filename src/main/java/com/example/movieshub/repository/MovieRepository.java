package com.example.movieshub.repository;

import com.example.movieshub.entity.Movie;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
   boolean existsByTitleIgnoreCase(String title);

   // Fetch movies including categories to avoid lazy-loading issues during JSON serialization
    @EntityGraph(attributePaths = "categories")
    @Query("select m from Movie m")
    List<Movie> findAllWithCategories();

    @EntityGraph(attributePaths = "categories")
    @Query("select m from Movie m where m.id = :id")
    Optional<Movie> findByIdWithCategories(Long id);

    @EntityGraph(attributePaths = "categories")
    @Query("select m from Movie m where lower(m.title) like lower(concat('%', :query, '%')) " +
            "or lower(coalesce(m.overview, '')) like lower(concat('%', :query, '%'))")
    List<Movie> searchMovies(@Param("query") String query);

}
