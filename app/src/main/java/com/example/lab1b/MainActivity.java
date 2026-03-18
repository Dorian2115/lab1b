package com.example.lab1b;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab1b.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final String KEY_BUTTON_VISIBILITY = "button_visibility";
    private static final String KEY_ERROR_FIRST_NAME = "error_first_name";
    private static final String KEY_ERROR_LAST_NAME = "error_last_name";
    private static final String KEY_ERROR_GRADES_COUNT = "error_grades_count";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        if (savedInstanceState != null) {
            int buttonVisibility = savedInstanceState.getInt(KEY_BUTTON_VISIBILITY, View.GONE);
            binding.buttonGrades.setVisibility(buttonVisibility);

            String errorFN = savedInstanceState.getString(KEY_ERROR_FIRST_NAME);
            String errorLN = savedInstanceState.getString(KEY_ERROR_LAST_NAME);
            String errorGC = savedInstanceState.getString(KEY_ERROR_GRADES_COUNT);

            if (errorFN != null) {
                binding.editFirstName.setError(errorFN);
            }
            if (errorLN != null) {
                binding.editLastName.setError(errorLN);
            }
            if (errorGC != null) {
                binding.editGradesCount.setError(errorGC);
            }
        }

        setupTextWatchers();
        setupFocusListeners();
    }

    private void setupTextWatchers() {
        TextWatcher commonWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkFields();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        binding.editFirstName.addTextChangedListener(commonWatcher);
        binding.editLastName.addTextChangedListener(commonWatcher);
        binding.editGradesCount.addTextChangedListener(commonWatcher);
    }

    private void setupFocusListeners() {
        binding.editFirstName.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String fName = binding.editFirstName.getText().toString();
                if (fName.isEmpty()) {
                    binding.editFirstName.setError(getString(R.string.error_empty));
                    Toast.makeText(MainActivity.this, R.string.error_empty, Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.editLastName.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String lName = binding.editLastName.getText().toString();
                if (lName.isEmpty()) {
                    binding.editLastName.setError(getString(R.string.error_empty));
                    Toast.makeText(MainActivity.this, R.string.error_empty, Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.editGradesCount.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String countStr = binding.editGradesCount.getText().toString();
                if (countStr.isEmpty()) {
                    binding.editGradesCount.setError(getString(R.string.error_empty));
                    Toast.makeText(MainActivity.this, R.string.error_empty, Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        int count = Integer.parseInt(countStr);
                        if (count < 5 || count > 15) {
                            binding.editGradesCount.setError(getString(R.string.error_invalid_count));
                            Toast.makeText(MainActivity.this, R.string.error_invalid_count, Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        binding.editGradesCount.setError(getString(R.string.error_invalid_count));
                        Toast.makeText(MainActivity.this, R.string.error_invalid_count, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void checkFields() {
        String fName = binding.editFirstName.getText().toString();
        String lName = binding.editLastName.getText().toString();
        String countStr = binding.editGradesCount.getText().toString();

        boolean isValid = !fName.isEmpty() && !lName.isEmpty() && !countStr.isEmpty();

        if (isValid) {
            try {
                int count = Integer.parseInt(countStr);
                if (count >= 5 && count <= 15) {
                    binding.buttonGrades.setVisibility(View.VISIBLE);
                } else {
                    binding.buttonGrades.setVisibility(View.GONE);
                }
            } catch (NumberFormatException e) {
                binding.buttonGrades.setVisibility(View.GONE);
            }
        } else {
            binding.buttonGrades.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(KEY_BUTTON_VISIBILITY, binding.buttonGrades.getVisibility());

        if (binding.editFirstName.getError() != null) {
            outState.putString(KEY_ERROR_FIRST_NAME, binding.editFirstName.getError().toString());
        }
        if (binding.editLastName.getError() != null) {
            outState.putString(KEY_ERROR_LAST_NAME, binding.editLastName.getError().toString());
        }
        if (binding.editGradesCount.getError() != null) {
            outState.putString(KEY_ERROR_GRADES_COUNT, binding.editGradesCount.getError().toString());
        }
    }
}