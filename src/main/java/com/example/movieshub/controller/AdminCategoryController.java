package com.example.movieshub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.movieshub.dto.ResponseModel;
import com.example.movieshub.entity.Category;
import com.example.movieshub.service.CategoryService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminCategoryController {
	private final CategoryService categoryService;
	
	
	@GetMapping("/categories")
	public ResponseEntity<ResponseModel> getAllCategories(){
		
		ResponseModel response = categoryService.findAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping("/categories")
    public ResponseEntity<ResponseModel> createCategory(@RequestBody Category category) {
        ResponseModel response = categoryService.create(category);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<ResponseModel> deleteCategory(@PathVariable Long id) {
        ResponseModel response = categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    
    @PutMapping("/categories/{id}")
    public ResponseEntity<ResponseModel> updateCategory(@PathVariable Long id , @RequestBody Category update){
    	ResponseModel response = categoryService.update(id, update);
    	return ResponseEntity.status(HttpStatus.OK).body(response);
    	
    }
	
}
