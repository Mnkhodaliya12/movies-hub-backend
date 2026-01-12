package com.example.movieshub.service;

import com.example.movieshub.dto.ResponseModel;
import com.example.movieshub.entity.Movie;

public interface MovieService {

    ResponseModel findAll();

    ResponseModel findById(Long id);

    ResponseModel create(Movie movie);

    ResponseModel update(Long id, Movie movie);

    ResponseModel delete(Long id);
}
