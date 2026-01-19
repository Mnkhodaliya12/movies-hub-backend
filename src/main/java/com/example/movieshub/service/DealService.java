package com.example.movieshub.service;

import com.example.movieshub.dto.DealRequestDto;
import com.example.movieshub.dto.ResponseModel;

public interface DealService {

    ResponseModel findAll();

    ResponseModel create(DealRequestDto dto);

    ResponseModel delete(Long id);
}
