package com.moviesaapi.genre;

import com.moviesaapi.movie.Movie;
import com.moviesaapi.movie.MovieRepo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class GenreController {
    GenreRepo genreRepo;
    MovieRepo movieRepo;

    GenreController(GenreRepo genreRepo, MovieRepo movieRepo) {
        this.genreRepo = genreRepo;
        this.movieRepo = movieRepo;
    }

    @GetMapping(path = "/genres", produces = "application/json")
    @ResponseBody
    public List<Genre> list() {
        return genreRepo.findAll();
    }

    @GetMapping(path = "/genres/{id}", produces = "application/json")
    @ResponseBody
    public Optional<Genre> findById(@PathVariable int id) {
        return genreRepo
                .findById(id);
    }

    @PutMapping(path = "/genres", consumes = "application/json")
    public Genre editOrCreate(@RequestBody Genre genre) {
        genreRepo.save(genre);
        return genre;
    }

    @DeleteMapping(path = "/genres/{id}")
    public int deleteById(@PathVariable int id) {
        List<Movie> _moviesToDelete = movieRepo.findByGenre_Id(id);
        movieRepo.deleteAll(_moviesToDelete);
        genreRepo.deleteById(id);
        return id;
    }
}

