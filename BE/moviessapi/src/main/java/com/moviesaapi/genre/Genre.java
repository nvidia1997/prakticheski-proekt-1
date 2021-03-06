package com.moviesaapi.genre;

import com.google.gson.Gson;

import javax.persistence.*;

@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 90, nullable = false)
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genre() {
    }

    public Genre(String json) {
        Genre _genre = new Gson().fromJson(json, Genre.class);
        this.id = _genre.getId();
        this.name = _genre.getName();
    }

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
