package nvidia1997.movies.app.persistence.movie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import nvidia1997.movies.app.core.movie.MovieDomain;
import nvidia1997.movies.app.persistence.Repository;
import nvidia1997.movies.app.persistence.genre.GenreRepository;
import nvidia1997.movies.app.persistence.year.YearRepository;

public class MovieRepository extends Repository<MovieDomain, Integer> {
    public static final String COLUMN_GENRE_ID = "genreId";
    public static final String COLUMN_RELEASE_YEAR_ID = "releaseYearId";
    public static final String COLUMN_POSTER_URL = "posterUrl";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_OVERVIEW = "overview";

    @Override
    protected String getTableName() {
        return "movie";
    }

    @Override
    protected String getCreateTableSql() {
        return "CREATE TABLE " + getTableName() + " " +
                "(" + getColumnId() + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_GENRE_ID + " INTEGER NOT NULL," +
                COLUMN_RELEASE_YEAR_ID + " INTEGER NOT NULL," +
                COLUMN_POSTER_URL + " varchar(255) NOT NULL," +
                COLUMN_TITLE + " varchar(100) NOT NULL," +
                COLUMN_OVERVIEW + " varchar(500) NOT NULL," +
                "FOREIGN KEY (" + COLUMN_GENRE_ID + ") REFERENCES " + GenreRepository.TABLE_NAME + "(" + GenreRepository.COLUMN_ID + ")," +
                "FOREIGN KEY (" + COLUMN_RELEASE_YEAR_ID + ") REFERENCES " + YearRepository.TABLE_NAME + "(" + YearRepository.COLUMN_ID + "))";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
    }

    public MovieRepository(@Nullable Context context) {
        super(context);
    }

    @Override
    protected void mapContentValues(MovieDomain movieDomain, ContentValues cv) {
        cv.put(COLUMN_GENRE_ID, movieDomain.getGenreId());
        cv.put(COLUMN_RELEASE_YEAR_ID, movieDomain.getReleaseYearId());
        cv.put(COLUMN_POSTER_URL, movieDomain.getPosterUrl());
        cv.put(COLUMN_TITLE, movieDomain.getTitle());
        cv.put(COLUMN_OVERVIEW, movieDomain.getOverview());
    }

    @Override
    public MovieDomain mapCursorValues(Cursor c) {
        MovieDomain movieDomain = new MovieDomain();
        movieDomain.setId(c.getInt(c.getColumnIndex(getColumnId())));
        movieDomain.setGenreId(c.getInt(c.getColumnIndex(COLUMN_GENRE_ID)));
        movieDomain.setReleaseYearId(c.getInt(c.getColumnIndex(COLUMN_RELEASE_YEAR_ID)));
        movieDomain.setPosterUrl(c.getString(c.getColumnIndex(COLUMN_POSTER_URL)));
        movieDomain.setTitle(c.getString(c.getColumnIndex(COLUMN_TITLE)));
        movieDomain.setOverview(c.getString(c.getColumnIndex(COLUMN_OVERVIEW)));

        return movieDomain;
    }
}
