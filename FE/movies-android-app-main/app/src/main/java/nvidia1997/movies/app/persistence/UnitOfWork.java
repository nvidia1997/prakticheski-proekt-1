package nvidia1997.movies.app.persistence;

import nvidia1997.movies.app.persistence.genre.GenreRepository;
import nvidia1997.movies.app.persistence.movie.MovieRepository;
import nvidia1997.movies.app.persistence.year.YearRepository;

public class UnitOfWork {
    private GenreRepository genreRepository;
    private MovieRepository movieRepository;
    private YearRepository yearRepository;

    public GenreRepository getGenreRepository() {
        if (genreRepository == null) {
            genreRepository = new GenreRepository();
        }

        return genreRepository;
    }

    public MovieRepository getMovieRepository() {
        if (movieRepository == null) {
            movieRepository = new MovieRepository();
        }

        return movieRepository;
    }

    public YearRepository getYearRepository() {
        if (yearRepository == null) {
            yearRepository = new YearRepository();
        }

        return yearRepository;
    }
}
