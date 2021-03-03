package nvidia1997.movies.app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import nvidia1997.movies.app.persistence.genre.GenreRepository;
import nvidia1997.movies.app.persistence.movie.MovieRepository;
import nvidia1997.movies.app.persistence.year.YearRepository;
import nvidia1997.movies.app.services.MoviesService;
import nvidia1997.movies.app.services.YearsService;

public class MainActivity extends AppCompatActivity {

    Button buttonShowMovies;
    Button buttonShowGenres;
    Button buttonShowYears;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();

        MoviesService moviesService = new MoviesService();
        YearsService yearsService = new YearsService();

        YearRepository yearRepository = new YearRepository(this);
        yearRepository.dropTableIfExists(yearRepository.getWritableDatabase(), false);
        yearRepository.onCreate(yearRepository.getWritableDatabase());

        GenreRepository genreRepository = new GenreRepository(this);
        genreRepository.dropTableIfExists(genreRepository.getWritableDatabase(), false);
        genreRepository.onCreate(genreRepository.getWritableDatabase());

        MovieRepository movieRepository = new MovieRepository(this);
        movieRepository.dropTableIfExists(movieRepository.getWritableDatabase(), false);
        movieRepository.onCreate(movieRepository.getWritableDatabase());


        yearsService.fetchYears((years) -> {
            yearRepository.addMany(years);

            moviesService.fetchGenres((genres) -> {
                genreRepository.addMany(genres);

                moviesService.fetchMovies(
                        yearRepository,
                        genreRepository,
                        (movies) -> {
                            movieRepository.addMany(movies);

                            buttonShowMovies.setEnabled(true);
                            buttonShowGenres.setEnabled(true);
                            buttonShowYears.setEnabled(true);
                        }, null);
            });
        });
    }

    private void initUI() {
        View.OnClickListener onClick = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = null;

                switch (v.getId()) {
                    case R.id.buttonShowGenres:
                        intent = new Intent(MainActivity.this, GenresActivity.class);
                        break;
                    case R.id.buttonShowMovies:
                        intent = new Intent(MainActivity.this, MoviesActivity.class);
                        break;
                    case R.id.buttonShowYears:
                        intent = new Intent(MainActivity.this, YearsActivity.class);
                        break;
                }

                if (intent != null) {
                    startActivity(intent);
                }
            }
        };

        buttonShowMovies = findViewById(R.id.buttonShowMovies);
        buttonShowMovies.setOnClickListener(onClick);

        buttonShowGenres = findViewById(R.id.buttonShowGenres);
        buttonShowGenres.setOnClickListener(onClick);

        buttonShowYears = findViewById(R.id.buttonShowYears);
        buttonShowYears.setOnClickListener(onClick);
    }
}