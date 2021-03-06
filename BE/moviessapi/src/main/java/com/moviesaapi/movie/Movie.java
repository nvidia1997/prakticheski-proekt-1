package com.moviesaapi.movie;

import com.moviesaapi.genre.Genre;
import com.moviesaapi.year.Year;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Genre genre = new Genre();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Year releaseYear = new Year();

    @Column(length = 25000, nullable = false)
    private String posterUrl;

    @Column(length = 250, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String overview;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Year getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Year releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
