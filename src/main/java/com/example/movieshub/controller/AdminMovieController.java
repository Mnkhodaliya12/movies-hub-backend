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
import jakarta.validation.Valid;

import com.example.movieshub.dto.MovieCreateRequestDto;
import com.example.movieshub.dto.ResponseModel;
import com.example.movieshub.service.MovieService;

import lombok.RequiredArgsConstructor;



@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminMovieController {
	
	
	private final MovieService movieService;

	 @GetMapping("/movies")
	    public ResponseEntity<ResponseModel> getAllMovies() {
	        ResponseModel response = movieService.findAll();
	        return ResponseEntity.status(HttpStatus.OK).body(response);
	    }

	    @GetMapping("/movies/{id}")
	    public ResponseEntity<ResponseModel> getMovie(@PathVariable Long id) {
	        ResponseModel response = movieService.findById(id);
	        return ResponseEntity.status(HttpStatus.OK).body(response);
	    }

	    @PostMapping("/movies")
	    public ResponseEntity<ResponseModel> createMovie(@Valid @RequestBody MovieCreateRequestDto movieDto) {
	        ResponseModel response = movieService.create(movieDto);
	        return ResponseEntity.status(HttpStatus.OK).body(response);
	    }

	    @PutMapping("/movies/{id}")
	    public ResponseEntity<ResponseModel> updateMovie(@PathVariable Long id,
	                                                   @Valid @RequestBody MovieCreateRequestDto updatedDto) {
	        ResponseModel response = movieService.update(id, updatedDto);
	        return ResponseEntity.status(HttpStatus.OK).body(response);
	    }

	    @DeleteMapping("/movies/{id}")
	    public ResponseEntity<ResponseModel> deleteMovie(@PathVariable Long id) {
	        ResponseModel response = movieService.delete(id);
	        return ResponseEntity.status(HttpStatus.OK).body(response);
	    }
}
