package com.example.movieshub.dto;

import com.example.movieshub.entity.Category;
import com.example.movieshub.entity.Movie;
import com.example.movieshub.entity.Movie.Status;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieCreateRequestDto {

    @NotBlank
    private String title;

    @Size(max = 2000)
    private String overview;

    //@NotNull
    private LocalDate releaseDate;

    @Min(0)
    @Max(10)
    private Double rating;

    @Min(0)
    private Integer popularity;

    // Optional: can be null and set to default in service if needed
    private Status status;

    private String posterPath;

    private String videoUrl;

    // IDs of categories chosen from admin side
   // @NotEmpty(message = "At least one category must be selected")
    private Set<Long> categoryIds;

    public Movie toMovie(Set<Category> categories) {
        return Movie.builder()
                .title(this.title)
                .overview(this.overview)
                .releaseDate(this.releaseDate)
                .rating(this.rating)
                .popularity(this.popularity)
                .status(this.status != null ? this.status : Movie.Status.DRAFT)
                .posterPath(this.posterPath)
                .videoUrl(this.videoUrl)
                .categories(categories)
                .build();
    }

    public void updateEntity(Movie existing, Set<Category> categories) {
        existing.setTitle(this.title);
        existing.setOverview(this.overview);
        existing.setReleaseDate(this.releaseDate);
        existing.setRating(this.rating);
        existing.setPopularity(this.popularity);
        existing.setStatus(this.status != null ? this.status : existing.getStatus());
        existing.setPosterPath(this.posterPath);
        existing.setVideoUrl(this.videoUrl);
        existing.setCategories(categories);
    }

    // Backwards-compatibility: accept legacy payload where frontend sent an array of category objects:
    // "categories": [{"id": 1}, {"id": 2}]
    @com.fasterxml.jackson.annotation.JsonProperty("categories")
    public void setCategoriesLegacy(java.util.Collection<?> categories) {
        if (categories == null) return;
        java.util.Set<Long> ids = new java.util.HashSet<>();
        for (Object obj : categories) {
            if (obj == null) continue;
            if (obj instanceof java.util.Map) {
                Object idVal = ((java.util.Map<?, ?>) obj).get("id");
                if (idVal instanceof Number) {
                    ids.add(((Number) idVal).longValue());
                } else if (idVal instanceof String) {
                    try {
                        ids.add(Long.parseLong((String) idVal));
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        }
        if (!ids.isEmpty()) {
            this.categoryIds = ids;
        }
    }
}
