package com.example.movieshub.service;

import com.example.movieshub.dto.ResponseModel;
import com.example.movieshub.entity.Category;

public interface CategoryService {

    ResponseModel findAll();

    ResponseModel create(Category category);

    ResponseModel delete(Long id);
    
    ResponseModel update(long id,Category update);
}
