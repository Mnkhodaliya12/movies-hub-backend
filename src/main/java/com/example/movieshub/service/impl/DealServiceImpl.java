package com.example.movieshub.service.impl;

import com.example.movieshub.dto.DealRequestDto;
import com.example.movieshub.dto.ResponseModel;
import com.example.movieshub.entity.Deal;
import com.example.movieshub.entity.Movie;
import com.example.movieshub.repository.DealRepository;
import com.example.movieshub.repository.MovieRepository;
import com.example.movieshub.service.DealService;
import com.example.movieshub.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;
    private final MovieRepository movieRepository;

    @Override
    public ResponseModel findAll() {
        List<Deal> deals = dealRepository.findAll();
        return CommonUtil.createResponse(HttpStatus.OK, "Deals fetched successfully", deals);
    }

    @Override
    public ResponseModel create(DealRequestDto dto) {
        Movie movie = movieRepository.findById(dto.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Deal deal = dto.toEntity(movie);
        Deal saved = dealRepository.save(deal);

        return CommonUtil.createResponse(HttpStatus.CREATED, "Deal created successfully", saved);
    }

    @Override
    public ResponseModel delete(Long id) {
        if (!dealRepository.existsById(id)) {
            return CommonUtil.createResponse(HttpStatus.NOT_FOUND, "Deal not found");
        }
        dealRepository.deleteById(id);
        return CommonUtil.createResponse(HttpStatus.NO_CONTENT, "Deal deleted successfully");
    }
}
