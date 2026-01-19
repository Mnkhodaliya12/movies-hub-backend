package com.example.movieshub.service;

import com.example.movieshub.dto.MovieCreateRequestDto;
import com.example.movieshub.dto.ResponseModel;
import org.springframework.web.multipart.MultipartFile;

public interface MovieService {

    ResponseModel findAll();

    ResponseModel findById(Long id);

    ResponseModel search(String query);

    ResponseModel create(MovieCreateRequestDto movieDto, MultipartFile posterFile);

    ResponseModel update(Long id, MovieCreateRequestDto movieDto, MultipartFile posterFile);

    ResponseModel delete(Long id);

    ResponseModel addScreenshots(Long id, MultipartFile[] screenshots);
}
