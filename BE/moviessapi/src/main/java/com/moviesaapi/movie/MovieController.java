package com.moviesaapi.movie;

import com.moviesaapi.genre.GenreRepo;
import com.moviesaapi.year.YearRepo;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class MovieController {
    MovieRepo movieRepo;
    GenreRepo genreRepo;
    YearRepo yearRepo;

    MovieController(MovieRepo movieRepo, GenreRepo genreRepo, YearRepo yearRepo) {
        this.movieRepo = movieRepo;
        this.genreRepo = genreRepo;
        this.yearRepo = yearRepo;
    }


    @GetMapping(path = "/movies", produces = "application/json")
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
                    .filter(movie -> selectedGenres.contains(movie.getGenre())
                    )
                    .collect(Collectors.toList());
        }

        return movies;
    }

    @GetMapping(path = "/movies/{id}", produces = "application/json")
    @ResponseBody
    public Optional<Movie> findById(@PathVariable int id) {
        return movieRepo
                .findById(id);
    }

    @PutMapping(path = "/movies")
    @ResponseBody
    public Movie editOrCreate(@RequestBody Movie movie) {
        movieRepo.save(movie);
        return movie;
    }

    @DeleteMapping(path = "/movies/{id}")
    public int deleteById(@PathVariable int id) {
        movieRepo.deleteById(id);
        return id;
    }
}
