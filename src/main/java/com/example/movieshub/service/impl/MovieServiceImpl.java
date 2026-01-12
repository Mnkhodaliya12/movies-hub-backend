package com.example.movieshub.service.impl;

import com.example.movieshub.dto.ResponseModel;
import com.example.movieshub.entity.Movie;
import com.example.movieshub.repository.MovieRepository;
import com.example.movieshub.service.MovieService;
import com.example.movieshub.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

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
    public ResponseModel create(Movie movie) {
    	
    	if(movieRepository.existsByTitleIgnoreCase(movie.getTitle())) {
    		 return CommonUtil.createResponse(
                    HttpStatus.CONFLICT,
                    "Movie with this title already exists"
            );
    	}
    	
        Movie saved = movieRepository.save(movie);
        return CommonUtil.createResponse(HttpStatus.CREATED, "Movie created successfully", saved);
    }

    @Override
    public ResponseModel update(Long id, Movie updated) {
        Movie existing = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        existing.setTitle(updated.getTitle());
        existing.setOverview(updated.getOverview());
        existing.setReleaseDate(updated.getReleaseDate());
        existing.setRating(updated.getRating());
        existing.setPopularity(updated.getPopularity());
        existing.setStatus(updated.getStatus());
        existing.setPosterPath(updated.getPosterPath());
        existing.setCategories(updated.getCategories());

        Movie saved = movieRepository.save(existing);
        return CommonUtil.createResponse(HttpStatus.OK, "Movie updated successfully", saved);
    }

    @Override
    public ResponseModel delete(Long id) {
        movieRepository.deleteById(id);
        return CommonUtil.createResponse(HttpStatus.NO_CONTENT, "Movie deleted successfully");
    }
}
