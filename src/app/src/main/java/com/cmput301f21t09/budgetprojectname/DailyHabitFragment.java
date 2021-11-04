package com.cmput301f21t09.budgetprojectname;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Fragment that shows User's Daily Habit
 */
public class DailyHabitFragment extends Fragment {

    /**
     * Controller for fetching habit events
     */
    private HabitListController habitListController = new HabitListController();


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

        // Set Today's Day to set to the Spinner
        ArrayList<String> dayList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.dayArray)));
        String day = new SimpleDateFormat("EEEE").format(new Date());
        int todayIndex = 0;
        switch (day) {
            case "Monday":
                todayIndex = 0;
                break;
            case "Tuesday":
                todayIndex = 1;
                break;
            case "Wednesday":
                todayIndex = 2;
                break;
            case "Thursday":
                todayIndex = 3;
                break;
            case "Friday":
                todayIndex = 4;
        }
        dayList.set(todayIndex, "Today");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, dayList);

        Spinner spinner = view.findViewById(R.id.day_spinner);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(todayIndex);

        // Retrieve the specific view
        ListView habitList = view.findViewById(R.id.habit_listview);

        // Set up the list and send an empty list to the view
        ArrayList<HabitModel> habitDataList = new ArrayList<>();
        ArrayAdapter<HabitModel> habitAdapter = new DailyHabitCustomList(getContext(), habitDataList);
        habitList.setAdapter(habitAdapter);

        // Fetches the past habit events related to the current habit from Firestore using habitID
        // and update the view
        habitListController.readHabitList(new HabitListController.HabitListCallback() {
            @Override
            public void onCallback(ArrayList<HabitModel> hbEvtLst) {
                habitDataList.clear();
                habitDataList.addAll(hbEvtLst);
                habitAdapter.notifyDataSetChanged();
            }
        });

        habitList.setOnItemClickListener((parent, view1, position, id) -> {
            // TODO: Pass targeted Habit to ViewHabitActivity
            Log.v("TAG", "CLICKED row number: ");
        });

        habitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Log.v("TAG", "CLICKED row number: " + arg2);
                HabitModel selectedHM = habitAdapter.getItem(arg2);
                System.out.println("selected element id " + selectedHM.getID());

            }

        });



    }
}
