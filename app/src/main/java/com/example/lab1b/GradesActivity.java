package com.example.lab1b;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lab1b.databinding.ActivityGradesBinding;

import java.util.ArrayList;
import java.util.List;
public class GradesActivity extends AppCompatActivity{
    private ActivityGradesBinding binding;
    private List<Grade> mgradeList;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityGradesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.topAppBar.setNavigationOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        Bundle extras = getIntent().getExtras();
        int gradesCount = 5;
        if (extras != null) {
            gradesCount = extras.getInt("GRADES_COUNT", 5);
        }

        String[] classNames = getResources().getStringArray(R.array.course_names);

        mgradeList = new ArrayList<>();
        for (int i = 0; i < gradesCount; i++) {
            String subjectName = (i < classNames.length) ? classNames[i] : "Przedmiot " + (i + 1);
            mgradeList.add(new Grade((subjectName), 2.0));
        }

        GradesAdapter adapter = new GradesAdapter(this, mgradeList);
        binding.gradesRecyclerView.setAdapter(adapter);
        binding.gradesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.calculateAverageButton.setOnClickListener(v -> {
            double sum = 0;

            for (Grade g: mgradeList) {
                sum += g.getGrade();
            }

            double average = sum / mgradeList.size();
             Bundle bundle = new Bundle();
             bundle.putDouble("AVERAGE", average);
             Intent intent = new Intent();
             intent.putExtras(bundle);
             setResult(RESULT_OK, intent);
             finish();
        });




    }
}
