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
        switch (currentGrade.getGrade()) {
            case 2:
                holder.binding.gradesRadioGroup.check(R.id.grade2RadioButton);
                break;
            case 3:
                holder.binding.gradesRadioGroup.check(R.id.grade3RadioButton);
                break;
            case 4:
                holder.binding.gradesRadioGroup.check(R.id.grade4RadioButton);
                break;
            case 5:
                holder.binding.gradesRadioGroup.check(R.id.grade5RadioButton);
                break;
            default:
                holder.binding.gradesRadioGroup.clearCheck();
                break;
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
                int newGrade = 2;
                if (checkedId == R.id.grade2RadioButton) {
                    newGrade = 2;
                } else if (checkedId == R.id.grade3RadioButton) {
                    newGrade = 3;
                } else if (checkedId == R.id.grade4RadioButton) {
                    newGrade = 4;
                } else if (checkedId == R.id.grade5RadioButton) {
                    newGrade = 5;
                }
                mGradeList.get(position).setGrade(newGrade);
            }
        }
    }
}
