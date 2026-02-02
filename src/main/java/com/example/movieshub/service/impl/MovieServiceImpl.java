package com.example.movieshub.service.impl;

import com.example.movieshub.dto.MovieCreateRequestDto;
import com.example.movieshub.dto.ResponseModel;
import com.example.movieshub.entity.Category;
import com.example.movieshub.entity.Movie;
import com.example.movieshub.repository.CategoryRepository;
import com.example.movieshub.repository.MovieRepository;
import com.example.movieshub.service.MovieService;
import com.example.movieshub.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ResponseModel findAll() {
        List<Movie> movies = movieRepository.findAll();
        return CommonUtil.createResponse(HttpStatus.OK, "Movies fetched successfully", movies);
    }

    @Override
    public ResponseModel findById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        return CommonUtil.createResponse(HttpStatus.OK, "Movie fetched successfully", movie);
    }

    @Override
    public ResponseModel search(String query) {
        List<Movie> movies = movieRepository.searchMovies(query);
        return CommonUtil.createResponse(HttpStatus.OK, "Movies searched successfully", movies);
    }

    private static final String POSTER_UPLOAD_DIR = "uploads/posters";
    private static final String SCREENSHOT_UPLOAD_DIR = "uploads/screenshots";

    // 2 MB per image
    private static final long MAX_IMAGE_SIZE_BYTES = 2L * 1024 * 1024;
    // 10 MB total for all screenshots in a single request
    private static final long MAX_TOTAL_SCREENSHOTS_SIZE_BYTES = 10L * 1024 * 1024;

    private String storePosterFile(MultipartFile posterFile) {
        if (posterFile == null || posterFile.isEmpty()) {
            return null;
        }

        try {
            Path uploadPath = Paths.get(POSTER_UPLOAD_DIR).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);

            String originalFilename = posterFile.getOriginalFilename();
            String fileName = System.currentTimeMillis() + "_" + (originalFilename != null ? originalFilename : "poster");

            Path targetLocation = uploadPath.resolve(fileName);
            Files.copy(posterFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return "/" + POSTER_UPLOAD_DIR + "/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Could not store poster file", e);
        }
    }

    private String storeScreenshotFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        try {
            Path uploadPath = Paths.get(SCREENSHOT_UPLOAD_DIR).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);

            String originalFilename = file.getOriginalFilename();
            String fileName = System.currentTimeMillis() + "_" + (originalFilename != null ? originalFilename : "screenshot");

            Path targetLocation = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return "/" + SCREENSHOT_UPLOAD_DIR + "/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Could not store screenshot file", e);
        }
    }

    @Override
    public ResponseModel create(MovieCreateRequestDto movieDto, MultipartFile posterFile) {

        if (movieRepository.existsByTitleIgnoreCase(movieDto.getTitle())) {
            return CommonUtil.createResponse(
                    HttpStatus.CONFLICT,
                    "Movie with this title already exists"
            );
        }

        Set<Category> categories = new HashSet<>();
        if (movieDto.getCategoryIds() != null && !movieDto.getCategoryIds().isEmpty()) {
            categories.addAll(categoryRepository.findAllById(movieDto.getCategoryIds()));
        }

        if (posterFile != null && !posterFile.isEmpty() && posterFile.getSize() > MAX_IMAGE_SIZE_BYTES) {
            return CommonUtil.createResponse(
                    HttpStatus.BAD_REQUEST,
                    "Poster image is too large. Maximum allowed size is 2MB."
            );
        }

        String storedPosterPath = storePosterFile(posterFile);
        if (storedPosterPath != null) {
            movieDto.setPosterPath(storedPosterPath);
        }

        Movie movie = movieDto.toMovie(categories);

        Movie saved = movieRepository.save(movie);
        return CommonUtil.createResponse(HttpStatus.CREATED, "Movie created successfully", saved);
    }

    @Override
    public ResponseModel update(Long id, MovieCreateRequestDto updatedDto, MultipartFile posterFile) {
        Movie existing = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Set<Category> categories = new HashSet<>();
        if (updatedDto.getCategoryIds() != null && !updatedDto.getCategoryIds().isEmpty()) {
            categories.addAll(categoryRepository.findAllById(updatedDto.getCategoryIds()));
        }

        if (posterFile != null && !posterFile.isEmpty() && posterFile.getSize() > MAX_IMAGE_SIZE_BYTES) {
            return CommonUtil.createResponse(
                    HttpStatus.BAD_REQUEST,
                    "Poster image is too large. Maximum allowed size is 2MB."
            );
        }

        String storedPosterPath = storePosterFile(posterFile);
        if (storedPosterPath != null) {
            updatedDto.setPosterPath(storedPosterPath);
        } else {
            updatedDto.setPosterPath(existing.getPosterPath());
        }

        updatedDto.updateEntity(existing, categories);

        Movie saved = movieRepository.save(existing);
        return CommonUtil.createResponse(HttpStatus.OK, "Movie updated successfully", saved);
    }

    @Override
    public ResponseModel delete(Long id) {
        movieRepository.deleteById(id);
        return CommonUtil.createResponse(HttpStatus.NO_CONTENT, "Movie deleted successfully");
    }

    @Override
    public ResponseModel addScreenshots(Long id, MultipartFile[] screenshots) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        if (screenshots != null) {
            if (screenshots.length > 6) {
                return CommonUtil.createResponse(
                        HttpStatus.BAD_REQUEST,
                        "You can upload a maximum of 6 screenshots."
                );
            }

            long totalSize = 0L;
            for (MultipartFile file : screenshots) {
                if (file == null || file.isEmpty()) {
                    continue;
                }
                long size = file.getSize();
                if (size > MAX_IMAGE_SIZE_BYTES) {
                    return CommonUtil.createResponse(
                            HttpStatus.BAD_REQUEST,
                            "One or more screenshots are too large. Maximum allowed size is 2MB per image."
                    );
                }
                totalSize += size;
                if (totalSize > MAX_TOTAL_SCREENSHOTS_SIZE_BYTES) {
                    return CommonUtil.createResponse(
                            HttpStatus.BAD_REQUEST,
                            "Total screenshots size is too large. Maximum combined size is 10MB."
                    );
                }
            }

            if (movie.getScreenshots() == null) {
                movie.setScreenshots(new HashSet<>());
            }

            for (MultipartFile file : screenshots) {
                String storedPath = storeScreenshotFile(file);
                if (storedPath != null) {
                    movie.getScreenshots().add(storedPath);
                }
            }
        }

        Movie saved = movieRepository.save(movie);
        return CommonUtil.createResponse(HttpStatus.OK, "Screenshots added successfully", saved);
    }
}
