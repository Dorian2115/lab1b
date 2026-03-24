package com.example.lab1b;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab1b.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private static final String KEY_BUTTON_VISIBILITY = "button_visibility";
    private static final String KEY_ERROR_FIRST_NAME = "error_first_name";
    private static final String KEY_ERROR_LAST_NAME = "error_last_name";
    private static final String KEY_ERROR_GRADES_COUNT = "error_grades_count";
    private static final String KEY_AVG_TEXT = "avg_text";
    private static final String KEY_AVG_VISIBILITY = "avg_visibility";
    private static final String KEY_SUPER_VISIBILITY = "super_visibility";
    private static final String KEY_FAILED_VISIBILITY = "failed_visibility";

    private ActivityResultLauncher<Intent> mActivityLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        mActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this::handleActivityResult);

        if (savedInstanceState != null) {
            binding.buttonGrades.setVisibility(savedInstanceState.getInt(KEY_BUTTON_VISIBILITY, View.GONE));
            binding.textAverage.setVisibility(savedInstanceState.getInt(KEY_AVG_VISIBILITY, View.GONE));
            binding.buttonSuper.setVisibility(savedInstanceState.getInt(KEY_SUPER_VISIBILITY, View.GONE));
            binding.buttonFailed.setVisibility(savedInstanceState.getInt(KEY_FAILED_VISIBILITY, View.GONE));

            String avgText = savedInstanceState.getString(KEY_AVG_TEXT);
            if (avgText != null) binding.textAverage.setText(avgText);

            binding.editFirstName.setError(savedInstanceState.getString(KEY_ERROR_FIRST_NAME));
            binding.editLastName.setError(savedInstanceState.getString(KEY_ERROR_LAST_NAME));
            binding.editGradesCount.setError(savedInstanceState.getString(KEY_ERROR_GRADES_COUNT));
        }

        setupTextWatchers();
        setupFocusListeners();
        setupButtons();
    }

    private void handleActivityResult(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
            double average = result.getData().getDoubleExtra("AVERAGE", 0.0);
            String avgText = String.format("Średnia to: %.2f", average);
            binding.textAverage.setText(avgText);
            binding.textAverage.setVisibility(View.VISIBLE);

            if (average >= 3.0) {
                binding.buttonSuper.setVisibility(View.VISIBLE);
                binding.buttonFailed.setVisibility(View.GONE);
            } else {
                binding.buttonSuper.setVisibility(View.GONE);
                binding.buttonFailed.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setupButtons() {
        binding.buttonGrades.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GradesActivity.class);
            int count = Integer.parseInt(binding.editGradesCount.getText().toString());
            intent.putExtra("GRADES_COUNT", count);
            mActivityLauncher.launch(intent);
        });

        binding.buttonSuper.setOnClickListener(v -> {
            Toast.makeText(this, "Gratulacje! Otrzymujesz zaliczenie!", Toast.LENGTH_LONG).show();
            finish();
        });

        binding.buttonFailed.setOnClickListener(v -> {
            Toast.makeText(this, "Wysyłam podanie o zaliczenie warunkowe", Toast.LENGTH_LONG).show();
            finish();
        });
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
            if (!hasFocus && binding.editFirstName.getText().toString().isEmpty()) {
                binding.editFirstName.setError(getString(R.string.error_empty));
                Toast.makeText(this, R.string.error_empty, Toast.LENGTH_SHORT).show();
            }
        });
        binding.editLastName.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && binding.editLastName.getText().toString().isEmpty()) {
                binding.editLastName.setError(getString(R.string.error_empty));
                Toast.makeText(this, R.string.error_empty, Toast.LENGTH_SHORT).show();
            }
        });
        binding.editGradesCount.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String s = binding.editGradesCount.getText().toString();
                if (s.isEmpty()) {
                    binding.editGradesCount.setError(getString(R.string.error_empty));
                } else {
                    int c = Integer.parseInt(s);
                    if (c < 5 || c > 15) {
                        binding.editGradesCount.setError(getString(R.string.error_invalid_count));
                        Toast.makeText(this, R.string.error_invalid_count, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void checkFields() {
        String f = binding.editFirstName.getText().toString();
        String l = binding.editLastName.getText().toString();
        String c = binding.editGradesCount.getText().toString();
        boolean ok = !f.isEmpty() && !l.isEmpty() && !c.isEmpty();
        if (ok) {
            int val = Integer.parseInt(c);
            ok = (val >= 5 && val <= 15);
        }
        binding.buttonGrades.setVisibility(ok ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_BUTTON_VISIBILITY, binding.buttonGrades.getVisibility());
        outState.putInt(KEY_AVG_VISIBILITY, binding.textAverage.getVisibility());
        outState.putInt(KEY_SUPER_VISIBILITY, binding.buttonSuper.getVisibility());
        outState.putInt(KEY_FAILED_VISIBILITY, binding.buttonFailed.getVisibility());
        outState.putString(KEY_AVG_TEXT, binding.textAverage.getText().toString());
        if (binding.editFirstName.getError() != null) outState.putString(KEY_ERROR_FIRST_NAME, binding.editFirstName.getError().toString());
        if (binding.editLastName.getError() != null) outState.putString(KEY_ERROR_LAST_NAME, binding.editLastName.getError().toString());
        if (binding.editGradesCount.getError() != null) outState.putString(KEY_ERROR_GRADES_COUNT, binding.editGradesCount.getError().toString());
    }
}