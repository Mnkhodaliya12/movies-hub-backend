package com.example.movieshub.dto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageResultModel {

    private long totalElements;
    private int currentPageNumber;
    private int numberOfElements;
    private int totalPages;
}