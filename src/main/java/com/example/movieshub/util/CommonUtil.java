package com.example.movieshub.util;

import org.springframework.http.HttpStatus;

import com.example.movieshub.dto.ResponseModel;

 

public class CommonUtil {

	public static ResponseModel createResponse(HttpStatus status , String message , Object data) {
		return ResponseModel.builder()
                .status(status)
                .statusCode(status.value())
                .message(message)
                .data(data)
                .build();
	}
	
	
	public static ResponseModel createResponse(HttpStatus status, String message) {
        return ResponseModel.builder()
                .status(status)
                .statusCode(status.value())
                .message(message)
                .build();
    }

}
