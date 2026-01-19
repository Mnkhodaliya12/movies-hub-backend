package com.example.movieshub.dto;

import com.example.movieshub.entity.Deal;
import com.example.movieshub.entity.Movie;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DealRequestDto {

    @NotBlank
    private String title;

    @NotNull
    private Long movieId;

    private String badgeColor = "orange";

    private Boolean active = true;

    public Deal toEntity(Movie movie) {
        if (badgeColor == null || badgeColor.isBlank()) {
            badgeColor = "orange";
        }
        if (active == null) {
            active = true;
        }
        return Deal.builder()
                .title(title)
                .badgeColor(badgeColor)
                .active(active)
                .movie(movie)
                .build();
    }
}
