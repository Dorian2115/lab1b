package com.example.lab1b;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab1b.databinding.GradeRawBinding;

import java.util.List;

public class GradesAdapter extends RecyclerView.Adapter<GradesAdapter.GradeViewHolder> {
    private List<Grade> mGradeList;
    private LayoutInflater mInflater;

    public GradesAdapter(Context context, List<Grade> gradeList) {
        mInflater = LayoutInflater.from(context);
        this.mGradeList = gradeList;
    }

    @NonNull
    @Override
    public GradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GradeRawBinding binding = GradeRawBinding.inflate(mInflater, parent, false);
        return new GradeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GradeViewHolder holder, int position) {
        Grade currentGrade = mGradeList.get(position);
        holder.binding.subjectNameTextView.setText(currentGrade.getName());
        holder.binding.gradesRadioGroup.setOnCheckedChangeListener(null);
        if (currentGrade.getGrade() == 2) {
            holder.binding.gradesRadioGroup.check(R.id.grade2RadioButton);
        } else if (currentGrade.getGrade() == 3) {
            holder.binding.gradesRadioGroup.check(R.id.grade3RadioButton);
        } else if (currentGrade.getGrade() == 3.5) {
            holder.binding.gradesRadioGroup.check(R.id.grade3_5RadioButton);
        } else if (currentGrade.getGrade() == 4) {
            holder.binding.gradesRadioGroup.check(R.id.grade4RadioButton);
        } else if (currentGrade.getGrade() == 4.5) {
            holder.binding.gradesRadioGroup.check(R.id.grade4_5RadioButton);
        } else if (currentGrade.getGrade() == 5) {
            holder.binding.gradesRadioGroup.check(R.id.grade5RadioButton);
        } else {
            holder.binding.gradesRadioGroup.clearCheck();
        }
        holder.binding.gradesRadioGroup.setOnCheckedChangeListener(holder);
    }

    @Override
    public int getItemCount() {
        return mGradeList.size();
    }


    public class GradeViewHolder extends RecyclerView.ViewHolder implements RadioGroup.OnCheckedChangeListener {
        public GradeRawBinding binding;

        public GradeViewHolder(GradeRawBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.gradesRadioGroup.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                double newGrade = 2.0;

                if (checkedId == R.id.grade2RadioButton) {
                    newGrade = 2.0;
                } else if (checkedId == R.id.grade3RadioButton) {
                    newGrade = 3.0;
                } else if (checkedId == R.id.grade3_5RadioButton) {
                    newGrade = 3.5;
                } else if (checkedId == R.id.grade4RadioButton) {
                    newGrade = 4.0;
                } else if (checkedId == R.id.grade4_5RadioButton) {
                    newGrade = 4.5;
                } else if (checkedId == R.id.grade5RadioButton) {
                    newGrade = 5.0;
                }

                mGradeList.get(position).setGrade(newGrade);
            }
        }
    }
}
