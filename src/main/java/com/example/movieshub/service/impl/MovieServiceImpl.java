package com.example.movieshub.service.impl;

import com.example.movieshub.dto.MovieCreateRequestDto;
import com.example.movieshub.dto.ResponseModel;
import com.example.movieshub.entity.Category;
import com.example.movieshub.entity.Movie;
import com.example.movieshub.repository.CategoryRepository;
import com.example.movieshub.repository.MovieRepository;
import com.example.movieshub.service.MovieService;
import com.example.movieshub.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ResponseModel findAll() {
        List<Movie> movies = movieRepository.findAll();
        return CommonUtil.createResponse(HttpStatus.OK, "Movies fetched successfully", movies);
    }

    @Override
    public ResponseModel findById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        return CommonUtil.createResponse(HttpStatus.OK, "Movie fetched successfully", movie);
    }

    @Override
    public ResponseModel search(String query) {
        List<Movie> movies = movieRepository.searchMovies(query);
        return CommonUtil.createResponse(HttpStatus.OK, "Movies searched successfully", movies);
    }

    @Override
    public ResponseModel create(MovieCreateRequestDto movieDto) {

        if (movieRepository.existsByTitleIgnoreCase(movieDto.getTitle())) {
            return CommonUtil.createResponse(
                    HttpStatus.CONFLICT,
                    "Movie with this title already exists"
            );
        }

        Set<Category> categories = new HashSet<>();
        if (movieDto.getCategoryIds() != null && !movieDto.getCategoryIds().isEmpty()) {
            categories.addAll(categoryRepository.findAllById(movieDto.getCategoryIds()));
        }

        Movie movie = movieDto.toMovie(categories);

        Movie saved = movieRepository.save(movie);
        return CommonUtil.createResponse(HttpStatus.CREATED, "Movie created successfully", saved);
    }

    @Override
    public ResponseModel update(Long id, MovieCreateRequestDto updatedDto) {
        Movie existing = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Set<Category> categories = new HashSet<>();
        if (updatedDto.getCategoryIds() != null && !updatedDto.getCategoryIds().isEmpty()) {
            categories.addAll(categoryRepository.findAllById(updatedDto.getCategoryIds()));
        }

        updatedDto.updateEntity(existing, categories);

        Movie saved = movieRepository.save(existing);
        return CommonUtil.createResponse(HttpStatus.OK, "Movie updated successfully", saved);
    }

    @Override
    public ResponseModel delete(Long id) {
        movieRepository.deleteById(id);
        return CommonUtil.createResponse(HttpStatus.NO_CONTENT, "Movie deleted successfully");
    }
}
