package com.cmput301f21t09.budgetprojectname;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DailyHabitFragment extends Fragment {

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
        TextView dateText = view.findViewById(R.id.today_date);
        dateText.setText(new SimpleDateFormat("EEEE, MMMM d").format(new Date()));

        // Spinner Setup
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Monday");
        arrayList.add("Tuesday");
        arrayList.add("Wednesday");
        arrayList.add("Thursday");
        arrayList.add("Friday");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, arrayList);

        Spinner spinner = view.findViewById(R.id.day_spinner);
        spinner.setAdapter(spinnerAdapter);

        // ListView setup
        ListView habitList = view.findViewById(R.id.habit_listview);

        ArrayList<HabitModel> habitDataList = new ArrayList<>();

        // This is mock data
        for (int i = 0; i < 10; i++) {
            habitDataList.add(new HabitModel("TestID", "TestString" + 1, "TestReason", new Date(), new Date(), i));
        }

        ArrayAdapter<HabitModel> habitAdapter = new DailyHabitCustomList(getContext(), habitDataList);
        habitList.setAdapter(habitAdapter);

        habitList.setOnItemClickListener((parent, view1, position, id) -> {
            // TODO: Pass targeted Habit to ViewHabitActivity
        });
    }
}
