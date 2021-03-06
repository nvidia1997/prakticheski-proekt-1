package nvidia1997.movies.app;

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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.List;

import nvidia1997.movies.app.core.genre.Genre;
import nvidia1997.movies.app.core.movie.Movie;
import nvidia1997.movies.app.core.year.Year;
import nvidia1997.movies.app.persistence.UnitOfWork;

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

    List<Movie> movies;
    List<Year> years;
    List<Genre> genres;
    UnitOfWork unitOfWork;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        unitOfWork = new UnitOfWork();
        loadData();
        initUI();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadData() {
        unitOfWork.getGenreRepository().findAll(genres1 -> {
            genres = genres1;
            populateGenreSpinner();

            unitOfWork.getYearRepository().findAll(years1 -> {
                years = years1;
                populateYearSpinner();

                unitOfWork.getMovieRepository().findAll(movies1 -> {
                    movies = movies1;
                    updateUI();
                });
            });
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateUI() {
        int _moviesCount = movies.size();
        buttonMoviePrev.setEnabled(_moviesCount > 0 && activeMovieIndex > 0);
        buttonMovieNext.setEnabled(activeMovieIndex < _moviesCount - 1);
        buttonMovieDelete.setEnabled(_moviesCount > 0 && getCurrentMovie() != null);
        buttonMoviesSave.setEnabled(_moviesCount > 0);

        int _moviesSelectedNumber = _moviesCount == 0 ? 0 : activeMovieIndex + 1;
        textViewMoviesSelectedNumber.setText(Integer.valueOf(_moviesSelectedNumber).toString());
        textViewMoviesCount.setText(Integer.valueOf(_moviesCount).toString());

        if (_moviesCount == 0) {
            return;
        }

        Movie _currentMovie = getCurrentMovie();

        editTextMovieTitle.setText(_currentMovie.getTitle());
        editTextMoviePosterUrl.setText(_currentMovie.getPosterUrl());
        editTextMultilineMovieOverview.setText(_currentMovie.getOverview());

        spinnerMovieYear.setSelection(genres.indexOf(_currentMovie.getGenre()));
        spinnerMovieYear.setSelection(years.indexOf(_currentMovie.getReleaseYear()));

        Picasso
                .get()
                .load(_currentMovie.getPosterUrl())
                .into(imageViewMoviePoster);
    }

    private Movie getUIDataAsMovie(boolean isNew) {
        Movie _movie = getCurrentMovie();
        if (_movie == null || isNew) {
            _movie = new Movie();
            _movie.setReleaseYear(years.get(spinnerMovieYear.getSelectedItemPosition()));
            _movie.setGenre(genres.get(spinnerMovieGenre.getSelectedItemPosition()));
        }

        _movie.setPosterUrl(editTextMoviePosterUrl.getText().toString());
        _movie.setTitle(editTextMovieTitle.getText().toString());
        _movie.setOverview(editTextMultilineMovieOverview.getText().toString());


        return _movie;
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
                if (movies.size() > 0 && activeMovieIndex > 0) {
                    activeMovieIndex--;
                    updateUI();
                }
            }
        });

        buttonMoviesSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Movie _currentMovie = getUIDataAsMovie(false);
                unitOfWork.getMovieRepository().update(_currentMovie, o -> {
                    Toast.makeText(getApplicationContext(), "Saved !", Toast.LENGTH_LONG).show();
                    refresh();
                });
            }
        });

        buttonMovieNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activeMovieIndex < movies.size() - 1) {
                    activeMovieIndex++;
                    updateUI();
                }
            }
        });

        spinnerMovieGenre = findViewById(R.id.spinnerMovieGenre);
        spinnerMovieGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Movie _movie = getCurrentMovie();

                if (_movie != null) {
                    _movie.setGenre(genres.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerMovieYear = findViewById(R.id.spinnerMovieYear);
        spinnerMovieYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Movie _movie = getCurrentMovie();

                if (_movie != null) {
                    _movie.setReleaseYear(years.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        buttonMovieAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unitOfWork.getMovieRepository().add(getUIDataAsMovie(true), o -> {
                    Toast.makeText(getApplicationContext(), "Added a copy as last element", Toast.LENGTH_LONG).show();
                    refresh();
                });
            }
        });

        buttonMovieDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void populateGenreSpinner() {
        String[] genreNames = genres
                .stream()
                .map(g -> g.getName())
                .toArray(String[]::new);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MoviesActivity.this, android.R.layout.simple_spinner_dropdown_item, genreNames);
        spinnerMovieGenre.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void populateYearSpinner() {
        String[] yearValues = years
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
        int oldMoviesSize = movies.size();
        loadData();
        int newMoviesSize = movies.size();
        if (oldMoviesSize < newMoviesSize) {
            activeMovieIndex = newMoviesSize - 1;
        }

        populateGenreSpinner();
        populateYearSpinner();
        updateUI();
    }

    private Movie getCurrentMovie() {
        return movies != null && movies.size() > 0
                ? movies.get(activeMovieIndex)
                : null;
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
                unitOfWork.getMovieRepository().removeById(getCurrentMovie().getId(), o -> {
                    if (activeMovieIndex - 1 <= 0) {
                        activeMovieIndex++;
                    } else {
                        activeMovieIndex--;
                    }

                    refresh();
                    dialog.hide();
                    Toast.makeText(getApplicationContext(), "Deleted !", Toast.LENGTH_SHORT).show();
                });
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