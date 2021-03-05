package com.moviesaapi.year;

import com.moviesaapi.Constants;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class YearController {
    YearRepo yearRepo;

    YearController(YearRepo yearRepo) {
        this.yearRepo = yearRepo;
    }

    @GetMapping(path = "/years", produces = "application/json")
    @CrossOrigin(origins = Constants.ORIGINS, exposedHeaders = {Constants.EXPOSED_HEADERS})
    @ResponseBody
    public List<Year> list() {
        return yearRepo.findAll();
    }

    @GetMapping(path = "/years/{id}", produces = "application/json")
    @CrossOrigin(origins = Constants.ORIGINS, exposedHeaders = {Constants.EXPOSED_HEADERS})
    @ResponseBody
    public Optional<Year> findById(@PathVariable int id) {
        return yearRepo
                .findById(id);

//        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PutMapping(path = "/years")
    @CrossOrigin(origins = Constants.ORIGINS, exposedHeaders = {Constants.EXPOSED_HEADERS})
    @ResponseBody
    public Year editOrCreate(@RequestBody Year year) {
        yearRepo.save(year);
        return year;
    }

    @DeleteMapping(path = "/years/{id}")
    @CrossOrigin(origins = Constants.ORIGINS, exposedHeaders = {Constants.EXPOSED_HEADERS})
    public int deleteById(@PathVariable int id) {
        yearRepo.deleteById(id);
        return id;
    }
}

