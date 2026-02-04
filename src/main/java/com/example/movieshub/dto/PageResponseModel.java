package com.example.movieshub.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class PageResponseModel {

    private HttpStatus status;
    private int statusCode;
    private Object data;
    private PageResultModel pageResult;
    private String message;
}