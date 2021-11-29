package com.cmput301f21t09.budgetprojectname.views.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cmput301f21t09.budgetprojectname.R;
import com.cmput301f21t09.budgetprojectname.models.HabitModel;
import com.cmput301f21t09.budgetprojectname.views.lists.DailyHabitCustomList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Fragment that shows User's Daily Habit
 */
public class DailyHabitFragment extends Fragment {

    ArrayList<HabitModel> habitDataList;
    ArrayAdapter<HabitModel> habitAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_daily_habit, container, false);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set Today's Date
        Date date = new Date();
        TextView dateText = view.findViewById(R.id.today_date);
        dateText.setText(new SimpleDateFormat("EEEE, MMMM d").format(date));

        // Retrieve the specific view
        ListView habitList = view.findViewById(R.id.habit_listview);

        // Set up the list and send an empty list to the view
        habitDataList = new ArrayList<>();
        habitAdapter = new DailyHabitCustomList(getContext(), habitDataList);
        habitList.setAdapter(habitAdapter);

        updateDailyHabitList();

        habitList.setOnItemClickListener((parent, view1, position, id) -> {
            // TODO: Pass targeted Habit to ViewHabitActivity
            Log.v("TAG", "CLICKED row number: ");
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        updateDailyHabitList();
    }

    /**
     * Fetches the habits related to the current day from Firestore
     */
    private void updateDailyHabitList() {
        HabitModel.getTodoForCurrentUser().addTaskCompleteListener(task -> {
            habitDataList.clear();
            if (task.isSuccessful()) {
                habitDataList.addAll(task.getResult());
            }
            habitAdapter.notifyDataSetChanged();
        });
    }
}
