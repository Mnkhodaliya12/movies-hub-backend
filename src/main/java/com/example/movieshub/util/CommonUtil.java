package com.example.movieshub.util;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import com.example.movieshub.dto.PageResponseModel;
import com.example.movieshub.dto.PageResultModel;
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
	 public static PageResponseModel createResponse(HttpStatus status, String message, Object data, Page<?> page){
	      return  PageResponseModel.builder()
	                .status(status)
	                .statusCode(status.value())
	                .data(data)
	                .message(message)
	                .pageResult(pageResult(page))
	                .build();
	    }

    public static PageResultModel pageResult(Page<?> page) {

        return page.getTotalPages() == 0
                ? PageResultModel.builder()
                        .totalElements(0L)
                        .totalPages(0)
                        .currentPageNumber(0)
                        .numberOfElements(0)
                        .build()
                : PageResultModel.builder()
                        .totalElements(page.getTotalElements())
                        .totalPages(page.getTotalPages())
                        .currentPageNumber(page.getNumber() + 1)
                        .numberOfElements(page.getNumberOfElements())
                        .build();
    }

}
