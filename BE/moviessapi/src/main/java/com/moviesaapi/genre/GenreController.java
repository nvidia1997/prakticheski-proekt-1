package com.moviesaapi.genre;

import com.moviesaapi.Constants;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GenreController {
    GenreRepo genreRepo;

    GenreController(GenreRepo genreRepo) {
        this.genreRepo = genreRepo;
    }

    @GetMapping(path = "/genres", produces = "application/json")
    @CrossOrigin(origins = Constants.ORIGINS, exposedHeaders = {Constants.EXPOSED_HEADERS})
    @ResponseBody
    public List<Genre> list() {
        return genreRepo.findAll();
    }

    @GetMapping(path = "/genres/{id}", produces = "application/json")
    @CrossOrigin(origins = Constants.ORIGINS, exposedHeaders = {Constants.EXPOSED_HEADERS})
    @ResponseBody
    public Genre findById(@PathVariable int id) {
        return genreRepo
                .findById(id)
                .orElse(new Genre());
    }

    @PutMapping(path = "/genres", consumes = "application/json")
    @CrossOrigin(origins = Constants.ORIGINS, exposedHeaders = {Constants.EXPOSED_HEADERS})
    public void editOrCreate(@RequestBody Genre genre) {
        genreRepo.save(genre);
    }

    @DeleteMapping(path = "/genres/{id}")
    @CrossOrigin(origins = Constants.ORIGINS, exposedHeaders = {Constants.EXPOSED_HEADERS})
    public void deleteById(@PathVariable int id) {
        genreRepo.deleteById(id);
    }
}

