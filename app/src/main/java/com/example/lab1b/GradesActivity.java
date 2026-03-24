package com.example.lab1b;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.lab1b.databinding.ActivityGradesBinding;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GradesActivity extends AppCompatActivity {
    private ActivityGradesBinding binding;
    private ArrayList<Grade> mgradeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGradesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.topAppBar.setNavigationOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        if (savedInstanceState != null) {
            mgradeList = (ArrayList<Grade>) savedInstanceState.getSerializable("LIST_STATE");
        } else {
            int gradesCount = getIntent().getIntExtra("GRADES_COUNT", 5);
            String[] classNames = getResources().getStringArray(R.array.course_names);
            mgradeList = new ArrayList<>();
            for (int i = 0; i < gradesCount; i++) {
                String subjectName = (i < classNames.length) ? classNames[i] : "Przedmiot " + (i + 1);
                mgradeList.add(new Grade(subjectName, 2.0));
            }
        }

        GradesAdapter adapter = new GradesAdapter(this, mgradeList);
        binding.gradesRecyclerView.setAdapter(adapter);
        binding.gradesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.calculateAverageButton.setOnClickListener(v -> {
            double sum = 0;
            for (Grade g : mgradeList) {
                sum += g.getGrade();
            }
            double average = sum / mgradeList.size();
            Intent intent = new Intent();
            intent.putExtra("AVERAGE", average);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("LIST_STATE", mgradeList);
    }
}