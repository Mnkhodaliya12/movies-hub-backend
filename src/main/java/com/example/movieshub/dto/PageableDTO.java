package com.example.movieshub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageableDTO  {

   private int pageNumber;
    private int pageSize;
    private String sortingParameter;
    private boolean sortDesc;

}
