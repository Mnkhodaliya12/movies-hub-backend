package com.example.movieshub.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.movieshub.dto.ResponseModel;
import com.example.movieshub.entity.Category;
import com.example.movieshub.repository.CategoryRepository;
import com.example.movieshub.service.CategoryService;
import com.example.movieshub.util.CommonUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    
    private final CategoryRepository categoryRepository;

     

    @Override
    public ResponseModel findAll() {
        List<Category> categories = categoryRepository.findAll();
        return CommonUtil.createResponse(HttpStatus.OK, "Categories fetched successfully", categories);
    }

    @Override
    public ResponseModel create(Category category) {
    	
    	if(categoryRepository.existsByNameIgnoreCase(category.getName())) {
    		return CommonUtil.createResponse(
    				HttpStatus.CONFLICT,
    				"Category Already Exists");
    	}
    	
        Category saved = categoryRepository.save(category);
        return CommonUtil.createResponse(HttpStatus.CREATED, "Category created successfully", saved);
    }

    @Override
    public ResponseModel delete(Long id) {
        categoryRepository.deleteById(id);
        return CommonUtil.createResponse(HttpStatus.NO_CONTENT, "Category deleted successfully");
    }

	@Override
	public ResponseModel update(long id, Category update) {
		Category exist = categoryRepository.findById(id)
		.orElseThrow(() -> new RuntimeException("category not found"));
		
		exist.setName(update.getName());
		exist.setDescription(update.getDescription());
		
		Category save = categoryRepository.save(exist);
		
        return CommonUtil.createResponse(HttpStatus.OK, "category updated successfully", save);

	}
    
    
}
