package nvidia1997.movies.app;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import java.util.List;

import nvidia1997.movies.app.core.genre.Genre;
import nvidia1997.movies.app.persistence.UnitOfWork;

public class GenresActivity extends AppCompatActivity {
    Button buttonGenresSave;
    Button buttonGenresDelete;
    Button buttonGenresAddNew;
    EditText editTextGenreName;
    Spinner spinnerGenres;

    List<Genre> genres;
    UnitOfWork unitOfWork;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genres);

        unitOfWork = new UnitOfWork();
        initUI();
        loadData();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadData() {
        unitOfWork.getGenreRepository().findAll(genres1 -> {
            genres = genres1;

            populateGenreSpinner();
            updateUI();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateUI() {
        int _genresCount = genres.size();
        buttonGenresDelete.setEnabled(_genresCount > 0);
        buttonGenresSave.setEnabled(_genresCount > 0);

        if (_genresCount == 0) {
            return;
        }

        Genre _currentGenre = getCurrentGenre();

        editTextGenreName.setText(_currentGenre.getName());
    }

    private Genre getUIDataAsGenre(boolean isNew) {
        Genre _genre = isNew ? null : getCurrentGenre();
        if (_genre == null) {
            _genre = new Genre();
        }

        _genre.setName(editTextGenreName.getText().toString());

        return _genre;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initUI() {
        buttonGenresSave = findViewById(R.id.buttonYearsSave);
        buttonGenresDelete = findViewById(R.id.buttonYearsDelete);
        buttonGenresAddNew = findViewById(R.id.buttonYearsAddNew);
        editTextGenreName = findViewById(R.id.editTextGenreName);
        spinnerGenres = findViewById(R.id.spinnerYears);

        buttonGenresSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Genre _genre = getUIDataAsGenre(false);
                unitOfWork.getGenreRepository().update(_genre, o -> {
                    Toast.makeText(getApplicationContext(), "Saved !", Toast.LENGTH_LONG).show();
                    refresh();
                });
            }
        });

        spinnerGenres.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateUI();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        buttonGenresAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unitOfWork.getGenreRepository().add(getUIDataAsGenre(true), o -> {
                    Toast.makeText(getApplicationContext(), "Added a copy as last element", Toast.LENGTH_LONG).show();
                    refresh();
                });
            }
        });

        buttonGenresDelete.setOnClickListener(new View.OnClickListener() {
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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(GenresActivity.this, android.R.layout.simple_spinner_dropdown_item, genreNames);
        spinnerGenres.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void refresh() {
        int _oldGenresCount = genres.size();
        loadData();
        int _newGenresCount = genres.size();
        populateGenreSpinner();
        if (_newGenresCount > 0) {
            if (_newGenresCount > _oldGenresCount) {
                spinnerGenres.setSelection(_newGenresCount - 1);
            }
        }

        updateUI();
    }

    private Genre getCurrentGenre() {
        int _pos = spinnerGenres.getSelectedItemPosition();
        return _pos > -1 ? genres.get(_pos) : null;
    }

    private void deleteDialog() {
        final Dialog dialog = new Dialog(GenresActivity.this);
        dialog.setTitle("Are you sure?");
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.custom_delete_dialog);

        Button okButton = dialog.findViewById(R.id.okButton);
        Button cancelButton = dialog.findViewById(R.id.cancelButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                unitOfWork.getGenreRepository().removeById(getCurrentGenre().getId(), o -> {
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