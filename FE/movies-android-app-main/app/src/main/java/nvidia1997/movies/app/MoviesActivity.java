package nvidia1997.movies.app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import nvidia1997.movies.app.core.genre.GenreDomain;
import nvidia1997.movies.app.core.movie.MovieDomain;
import nvidia1997.movies.app.core.year.YearDomain;
import nvidia1997.movies.app.persistence.genre.GenreRepository;
import nvidia1997.movies.app.persistence.movie.MovieRepository;
import nvidia1997.movies.app.persistence.year.YearRepository;

public class MoviesActivity extends AppCompatActivity {
    ImageView imageViewMoviePoster;
    Button buttonMoviePrev;
    Button buttonMoviesSave;
    Button buttonMovieNext;
    Button buttonMovieDelete;
    Button buttonMovieAddNew;
    EditText editTextMovieTitle;
    EditText editTextMoviePosterUrl;
    Spinner spinnerMovieGenre;
    Spinner spinnerMovieYear;
    EditText editTextMultilineMovieOverview;
    TextView textViewMoviesSelectedNumber;
    TextView textViewMoviesCount;
    int activeMovieIndex = 0;

    List<MovieDomain> movieDomains;
    List<YearDomain> yearDomains;
    List<GenreDomain> genreDomains;
    MovieRepository movieRepository;
    GenreRepository genreRepository;
    YearRepository yearRepository;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        movieRepository = new MovieRepository(this);
        genreRepository = new GenreRepository(this);
        yearRepository = new YearRepository(this);
        loadData();
        initUI();
    }

    private void loadData() {
        movieDomains = movieRepository.findAll();
        genreDomains = genreRepository.findAll();
        yearDomains = yearRepository.findAll();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateUI() {
        int _movieDomainsCount = movieDomains.size();
        textViewMoviesSelectedNumber.setText(Integer.valueOf(activeMovieIndex + 1).toString());
        textViewMoviesCount.setText(Integer.valueOf(_movieDomainsCount).toString());

        MovieDomain _currentMovie = getCurrentMovie();

        editTextMovieTitle.setText(_currentMovie.getTitle());
        editTextMoviePosterUrl.setText(_currentMovie.getPosterUrl());
        editTextMultilineMovieOverview.setText(_currentMovie.getOverview());

        GenreDomain _currentGenre = null;
        for (GenreDomain genreDomain : genreDomains) {
            if (genreDomain.getId().equals(_currentMovie.getGenreId())) {
                _currentGenre = genreDomain;
            }
        }
        if (_currentGenre != null) {
            int _currentGenreIndex = genreDomains.indexOf(_currentGenre);
            if (_currentGenreIndex >= 0) {
                spinnerMovieYear.setSelection(_currentGenreIndex, true);
            }
        }

        YearDomain _currentYear = null;
        for (YearDomain yearDomain : yearDomains) {
            if (yearDomain.getId() == _currentMovie.getReleaseYearId()) {
                _currentYear = yearDomain;
            }
        }
        if (_currentYear != null) {
            int _currentYearIndex = yearDomains.indexOf(_currentYear);
            if (_currentYearIndex >= 0) {
                spinnerMovieYear.setSelection(_currentYearIndex, true);
            }
        }

        Picasso
                .get()
                .load(_currentMovie.getPosterUrl())
                .into(imageViewMoviePoster);

        buttonMoviePrev.setEnabled(_movieDomainsCount > 0 && activeMovieIndex > 0);
        buttonMovieNext.setEnabled(activeMovieIndex < _movieDomainsCount - 1);
    }

    private MovieDomain getUIDataAsMovie() {
        MovieDomain movieDomain = getCurrentMovie();
        movieDomain.setPosterUrl(editTextMoviePosterUrl.getText().toString());
        movieDomain.setTitle(editTextMovieTitle.getText().toString());
        movieDomain.setOverview(editTextMultilineMovieOverview.getText().toString());

        return movieDomain;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initUI() {
        textViewMoviesSelectedNumber = findViewById(R.id.textViewMoviesSelectedNumber);
        textViewMoviesCount = findViewById(R.id.textViewMoviesCount);
        imageViewMoviePoster = findViewById(R.id.imageViewMoviePoster);
        buttonMoviePrev = findViewById(R.id.buttonMoviesPrev);
        buttonMoviesSave = findViewById(R.id.buttonMoviesSave);
        buttonMovieNext = findViewById(R.id.buttonMoviesNext);
        buttonMovieDelete = findViewById(R.id.buttonMovieDelete);
        buttonMovieAddNew = findViewById(R.id.buttonMovieAddNew);
        editTextMovieTitle = findViewById(R.id.editTextMovieTitle);
        editTextMoviePosterUrl = findViewById(R.id.editTextMoviePosterUrl);
        editTextMultilineMovieOverview = findViewById(R.id.editTextTextMultiLine);

        buttonMoviePrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movieDomains.size() > 0 && activeMovieIndex > 0) {
                    activeMovieIndex--;
                    updateUI();
                }
            }
        });

        buttonMoviesSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDomain _currentMovie = getUIDataAsMovie();
                movieRepository.update(_currentMovie);
                Toast.makeText(getApplicationContext(), "Saved !", Toast.LENGTH_LONG).show();
                refresh();
            }
        });

        buttonMovieNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activeMovieIndex < movieDomains.size() - 1) {
                    activeMovieIndex++;
                    updateUI();
                }
            }
        });

        spinnerMovieGenre = findViewById(R.id.spinnerMovieGenre);
        spinnerMovieGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getCurrentMovie()
                        .setGenreId(genreDomains.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        populateGenreSpinner();

        spinnerMovieYear = findViewById(R.id.spinnerMovieYear);
        spinnerMovieYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getCurrentMovie()
                        .setReleaseYearId(yearDomains.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        populateYearSpinner();

        buttonMovieAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieRepository.add(getUIDataAsMovie());
                Toast.makeText(getApplicationContext(), "Added a copy as last element", Toast.LENGTH_LONG).show();
                refresh();
            }
        });

        buttonMovieDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog();
            }
        });

        updateUI();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void populateGenreSpinner() {
        String[] genreNames = genreDomains
                .stream()
                .map(g -> g.getName())
                .toArray(String[]::new);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MoviesActivity.this, android.R.layout.simple_spinner_dropdown_item, genreNames);
        spinnerMovieGenre.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void populateYearSpinner() {
        String[] yearValues = yearDomains
                .stream()
                .map(g -> {
                            Integer a = g.getValue();
                            return a.toString();
                        }
                )
                .toArray(String[]::new);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MoviesActivity.this, android.R.layout.simple_spinner_dropdown_item, yearValues);
        spinnerMovieYear.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)

    private void refresh() {
        int oldMoviesSize = movieDomains.size();
        loadData();
        int newMoviesSize = movieDomains.size();
        if (oldMoviesSize < newMoviesSize) {
            activeMovieIndex = newMoviesSize - 1;
        }

        populateGenreSpinner();
        populateYearSpinner();
        updateUI();
    }

    private MovieDomain getCurrentMovie() {
        return movieDomains.get(activeMovieIndex);
    }

    private void deleteDialog() {
        final Dialog dialog = new Dialog(MoviesActivity.this);
        dialog.setTitle("Are you sure?");
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.custom_delete_dialog);

        Button okButton = dialog.findViewById(R.id.okButton);
        Button cancelButton = dialog.findViewById(R.id.cancelButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Deleting in progress.....", Toast.LENGTH_LONG).show();
                movieRepository.removeById(getCurrentMovie().getId());
                if (activeMovieIndex - 1 <= 0) {
                    activeMovieIndex++;
                } else {
                    activeMovieIndex--;
                }

                refresh();
                dialog.hide();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }
}