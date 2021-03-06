package com.moviesaapi.year;

import com.google.gson.Gson;

import javax.persistence.*;

@Entity
public class Year {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Year() {
    }

    public Year(String json) {
        Year _year = new Gson().fromJson(json, Year.class);
        this.id = _year.getId();
        this.value = _year.getValue();
    }

    public Year(int id, int value) {
        this.id = id;
        this.value = value;
    }
}
