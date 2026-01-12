package com.example.movieshub.dto;

 


import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseModel {
    private String message;
    private HttpStatus status;
    private int statusCode;
    private Object data;
}
