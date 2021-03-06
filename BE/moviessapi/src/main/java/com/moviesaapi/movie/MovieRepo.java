package com.moviesaapi.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepo extends JpaRepository<Movie, Integer> {
    List<Movie> findByReleaseYear_Id(int id);

    List<Movie> findByGenre_Id(int id);
}
