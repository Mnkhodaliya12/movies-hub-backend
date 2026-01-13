package com.example.movieshub.service;

import com.example.movieshub.dto.MovieCreateRequestDto;
import com.example.movieshub.dto.ResponseModel;
public interface MovieService {

    ResponseModel findAll();

    ResponseModel findById(Long id);

    ResponseModel search(String query);

    ResponseModel create(MovieCreateRequestDto movieDto);

    ResponseModel update(Long id, MovieCreateRequestDto movieDto);

    ResponseModel delete(Long id);
}
