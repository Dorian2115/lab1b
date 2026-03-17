package com.example.lab1b;

import android.os.Bundle;

import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lab1b.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final String KEY_BUTTON_VISIBILITY = "button_visibility";
    private static final String KEY_ERROR_FIRST_NAME = "error_first_name";
    private static final String KEY_ERROR_LAST_NAME = "error_last_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        if (savedInstanceState != null) {
            int buttonVisibility = savedInstanceState.getInt(KEY_BUTTON_VISIBILITY);
            binding.buttonGrades.setVisibility(buttonVisibility);

            String errorFN = savedInstanceState.getString(KEY_ERROR_FIRST_NAME);
            String errorLN = savedInstanceState.getString(KEY_ERROR_LAST_NAME);
            if (errorFN != null) {
                binding.editFirstName.setError(errorFN);
            }
            if (errorLN != null) {
                binding.editFirstName.setError(errorLN);
            }
        }

    }

    private void checkFields(){
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
            binding.buttonGrades.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(KEY_BUTTON_VISIBILITY, binding.buttonGrades.getVisibility());

        if (binding.editFirstName.getError() != null) {
            outState.putString(KEY_ERROR_FIRST_NAME, binding.editFirstName.getError().toString());
        }
    }
}