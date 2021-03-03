package com.moviesaapi.movie;

import com.moviesaapi.Constants;
import com.moviesaapi.genre.GenreRepo;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class MovieController {
    MovieRepo movieRepo;
    GenreRepo genreRepo;

    MovieController(MovieRepo movieRepo, GenreRepo genreRepo) {
        this.movieRepo = movieRepo;
        this.genreRepo = genreRepo;
    }


    @GetMapping(path = "/movies", produces = "application/json")
    @CrossOrigin(origins = Constants.ORIGINS, exposedHeaders = {Constants.EXPOSED_HEADERS})
    @ResponseBody
    public List<Movie> list(@RequestParam Optional<Integer> yearId, Optional<Integer[]> genreIds) {
        List<Movie> movies = movieRepo.findAll();

        if (yearId.isPresent()) {
            movies = movies
                    .stream()
                    .filter(movie -> movie
                            .getReleaseYear()
                            .getId() == yearId.get().intValue())
                    .collect(Collectors.toList());
        }

        if (genreIds.isPresent()) {
            var selectedGenres = genreRepo
                    .findAllById(Arrays.asList(genreIds.get()));

            movies = movies
                    .stream()
                    .filter(movie -> movie
                            .getGenres()
                            .containsAll(selectedGenres))
                    .collect(Collectors.toList());
        }

        return movies;
    }

    @GetMapping(path = "/movies/{id}", produces = "application/json")
    @CrossOrigin(origins = Constants.ORIGINS, exposedHeaders = {Constants.EXPOSED_HEADERS})
    @ResponseBody
    public Movie findById(@PathVariable int id) {
        return movieRepo
                .findById(id)
                .orElse(new Movie());
    }

    @PutMapping(path = "/movies")
    @CrossOrigin(origins = Constants.ORIGINS, exposedHeaders = {Constants.EXPOSED_HEADERS})
    @ResponseBody
    public void editOrCreate(@RequestBody Movie movie) {
        movieRepo.save(movie);
    }

    @DeleteMapping(path = "/movies/{id}")
    @CrossOrigin(origins = Constants.ORIGINS, exposedHeaders = {Constants.EXPOSED_HEADERS})
    public void deleteById(@PathVariable int id) {
        movieRepo.deleteById(id);
    }
}
