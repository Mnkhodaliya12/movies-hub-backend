package com.example.movieshub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.movieshub.dto.ResponseModel;
import com.example.movieshub.service.MovieService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MovieController {

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

    @GetMapping("/movies/search")
    public ResponseEntity<ResponseModel> searchMovies(@RequestParam("query") String query) {
        ResponseModel response = movieService.search(query);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
