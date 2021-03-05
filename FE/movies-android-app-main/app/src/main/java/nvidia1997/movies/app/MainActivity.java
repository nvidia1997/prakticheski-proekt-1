package nvidia1997.movies.app;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import nvidia1997.movies.app.persistence.UnitOfWork;

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
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onResume() {
        super.onResume();

        updateMoviesButtonState();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateMoviesButtonState() {
        UnitOfWork unitOfWork = new UnitOfWork();
        unitOfWork.getGenreRepository().findAll(genres1 -> {
            unitOfWork.getYearRepository().findAll(years1 -> {
                buttonShowMovies.setEnabled(genres1.size() > 0 && years1.size() > 0);
            });
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initUI() {
        updateMoviesButtonState();

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