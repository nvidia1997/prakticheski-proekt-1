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

import nvidia1997.movies.app.core.year.Year;
import nvidia1997.movies.app.persistence.UnitOfWork;

public class YearsActivity extends AppCompatActivity {
    Button buttonYearsSave;
    Button buttonYearsDelete;
    Button buttonYearsAddNew;
    EditText editTextNumberYearValue;
    Spinner spinnerYears;

    List<Year> years;
    UnitOfWork unitOfWork;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_years);

        unitOfWork = new UnitOfWork();
        initUI();
        loadData();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadData() {
        unitOfWork.getYearRepository().findAll(years1 -> {
            years = years1;

            populateYearsSpinner();
            updateUI();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateUI() {
        int _yearsCount = years.size();
        buttonYearsDelete.setEnabled(_yearsCount > 0);
        buttonYearsSave.setEnabled(_yearsCount > 0);

        if (_yearsCount == 0) {
            return;
        }

        Year _currentYear = getCurrentYear();

        editTextNumberYearValue.setText("" + _currentYear.getValue());
    }

    private Year getUIDataAsYear(boolean isNew) {
        Year _year = isNew ? null : getCurrentYear();
        if (_year == null) {
            _year = new Year();
        }

        _year.setValue(Integer.parseInt(editTextNumberYearValue.getText().toString()));

        return _year;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initUI() {
        buttonYearsSave = findViewById(R.id.buttonYearsSave);
        buttonYearsDelete = findViewById(R.id.buttonYearsDelete);
        buttonYearsAddNew = findViewById(R.id.buttonYearsAddNew);
        editTextNumberYearValue = findViewById(R.id.editTextNumberYearValue);
        spinnerYears = findViewById(R.id.spinnerYears);

        buttonYearsSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Year _year = getUIDataAsYear(false);
                unitOfWork.getYearRepository().update(_year, o -> {
                    Toast.makeText(getApplicationContext(), "Saved !", Toast.LENGTH_LONG).show();
                    refresh();
                });
            }
        });

        spinnerYears.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateUI();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        buttonYearsAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unitOfWork.getYearRepository().add(getUIDataAsYear(true), o -> {
                    Toast.makeText(getApplicationContext(), "Added a copy as last element", Toast.LENGTH_LONG).show();
                    refresh();
                });
            }
        });

        buttonYearsDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void populateYearsSpinner() {
        String[] yearValues = years
                .stream()
                .map(y -> new Integer(y.getValue()).toString())
                .toArray(String[]::new);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(YearsActivity.this, android.R.layout.simple_spinner_dropdown_item, yearValues);
        spinnerYears.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void refresh() {
        int _oldYearsCount = years.size();
        loadData();
        int _newYearsCount = years.size();
        populateYearsSpinner();
        if (_newYearsCount > 0) {
            if (_newYearsCount > _oldYearsCount) {
                spinnerYears.setSelection(_newYearsCount - 1);
            }
        }

        updateUI();
    }

    private Year getCurrentYear() {
        int _pos = spinnerYears.getSelectedItemPosition();
        return _pos > -1 ? years.get(_pos) : null;
    }

    private void deleteDialog() {
        final Dialog dialog = new Dialog(YearsActivity.this);
        dialog.setTitle("Are you sure?");
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.custom_delete_dialog);

        Button okButton = dialog.findViewById(R.id.okButton);
        Button cancelButton = dialog.findViewById(R.id.cancelButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                unitOfWork.getYearRepository().removeById(getCurrentYear().getId(), o -> {
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