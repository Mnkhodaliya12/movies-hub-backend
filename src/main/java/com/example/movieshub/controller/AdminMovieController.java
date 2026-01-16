package com.example.movieshub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
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

	    @PostMapping(value = "/movies", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	    public ResponseEntity<ResponseModel> createMovie(
	            @Valid @RequestPart("movie") MovieCreateRequestDto movieDto,
	            @RequestPart(value = "poster", required = false) MultipartFile posterFile) {
	        ResponseModel response = movieService.create(movieDto, posterFile);
	        return ResponseEntity.status(HttpStatus.OK).body(response);
	    }

	    @PutMapping(value = "/movies/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	    public ResponseEntity<ResponseModel> updateMovie(@PathVariable Long id,
	                                                   @Valid @RequestPart("movie") MovieCreateRequestDto updatedDto,
	                                                   @RequestPart(value = "poster", required = false) MultipartFile posterFile) {
	        ResponseModel response = movieService.update(id, updatedDto, posterFile);
	        return ResponseEntity.status(HttpStatus.OK).body(response);
	    }

	    @DeleteMapping("/movies/{id}")
	    public ResponseEntity<ResponseModel> deleteMovie(@PathVariable Long id) {
	        ResponseModel response = movieService.delete(id);
	        return ResponseEntity.status(HttpStatus.OK).body(response);
	    }
}
