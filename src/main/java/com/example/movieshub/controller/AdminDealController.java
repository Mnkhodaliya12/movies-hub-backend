package com.example.movieshub.controller;

import com.example.movieshub.dto.DealRequestDto;
import com.example.movieshub.dto.ResponseModel;
import com.example.movieshub.service.DealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/deals")
public class AdminDealController {

    private final DealService dealService;

    @GetMapping
    public ResponseEntity<ResponseModel> getAllDeals() {
        ResponseModel response = dealService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<ResponseModel> createDeal(@Valid @RequestBody DealRequestDto dto) {
        ResponseModel response = dealService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModel> deleteDeal(@PathVariable Long id) {
        ResponseModel response = dealService.delete(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
