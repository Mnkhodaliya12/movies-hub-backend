package com.example.movieshub.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.movieshub.dto.PageableDTO;

public final class PaginationUtil {

	public static Pageable createPageable(PageableDTO pageableDTO) {

	    int requestedPage = pageableDTO.getPageNumber();
	    int requestedSize = pageableDTO.getPageSize();

	    int pageIndex = requestedPage <= 0 ? 0 : requestedPage - 1;
	    int pageSize = requestedSize <= 0 ? 20 : requestedSize;

	    String sortingParameter = pageableDTO.getSortingParameter();
	    if (sortingParameter == null || sortingParameter.isBlank()) {
	        return PageRequest.of(pageIndex, pageSize);   // no sorting
	    }

	    Sort.Direction direction = pageableDTO.isSortDesc() ? Sort.Direction.DESC : Sort.Direction.ASC;
	    Sort sort = Sort.by(direction, sortingParameter);

	    return PageRequest.of(pageIndex, pageSize, sort);
	}
}
