package com.moviesaapi.year;

import com.moviesaapi.movie.Movie;
import com.moviesaapi.movie.MovieRepo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class YearController {
    YearRepo yearRepo;
    MovieRepo movieRepo;

    YearController(YearRepo yearRepo, MovieRepo movieRepo) {
        this.yearRepo = yearRepo;
        this.movieRepo = movieRepo;
    }

    @GetMapping(path = "/years", produces = "application/json")
    @ResponseBody
    public List<Year> list() {
        return yearRepo.findAll();
    }

    @GetMapping(path = "/years/{id}", produces = "application/json")
    @ResponseBody
    public Optional<Year> findById(@PathVariable int id) {
        return yearRepo
                .findById(id);

//        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PutMapping(path = "/years")
    @ResponseBody
    public Year editOrCreate(@RequestBody Year year) {
        yearRepo.save(year);
        return year;
    }

    @DeleteMapping(path = "/years/{id}")
    public int deleteById(@PathVariable int id) {
        List<Movie> _moviesToDelete = movieRepo.findByReleaseYear_Id(id);
        movieRepo.deleteAll(_moviesToDelete);
        yearRepo.deleteById(id);
        return id;
    }
}

