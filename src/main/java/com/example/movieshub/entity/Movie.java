package com.example.movieshub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String overview;

    private LocalDate releaseDate;

    private Double rating;

    private Integer popularity;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String posterPath;
    
    private String videoUrl;

    @ElementCollection
    @CollectionTable(
            name = "movie_screenshots",
            joinColumns = @JoinColumn(name = "movie_id")
    )
    @Column(name = "screenshot_path")
    private Set<String> screenshots = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "movie_categories",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public enum Status {
        DRAFT,
        PUBLISHED,
        FEATURED
    }
}
