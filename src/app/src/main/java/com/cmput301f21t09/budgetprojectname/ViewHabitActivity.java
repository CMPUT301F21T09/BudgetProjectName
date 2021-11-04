package com.cmput301f21t09.budgetprojectname;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ViewHabitActivity extends AppCompatActivity {
    private TextView habitName;
    private ListView habitEventList;
    ArrayAdapter<HabitEventModel> habitEventAdapter;
    ArrayList<HabitEventModel> habitEventDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit_screen);

        // The specific habitID is fetched from the previous activity
        Intent intent = getIntent();
        int habitID = intent.getIntExtra("HABIT_ID", -1);

        final TextView habitTitle = (TextView) findViewById(R.id.habitTitle);
        final TextView habitDescription = (TextView) findViewById(R.id.habitDescription);
        final TextView habitDate = (TextView) findViewById(R.id.habitDate);

        // Todo: Implement edit Habit function
        final Button editHabitBtn = findViewById(R.id.editHabitButton);
        editHabitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });

        // Todo: Change the habit title and description to actual data from Firestore using habitID
        habitTitle.setText("Wash Dishes");
        habitDescription.setText("Dirty dishes really suck :(");

        // Todo: Change the frequency of habit to actual data from Firestore using habitID
        ImageView sundayIcon = findViewById(R.id.sunday_icon);
        ImageView mondayIcon = findViewById(R.id.monday_icon);
        ImageView tuesdayIcon = findViewById(R.id.tuesday_icon);
        ImageView wednesdayIcon = findViewById(R.id.wednesday_icon);
        ImageView thursdayIcon = findViewById(R.id.thursday_icon);
        ImageView fridayIcon = findViewById(R.id.friday_icon);
        ImageView saturdayIcon = findViewById(R.id.saturday_icon);

        // If a user chooses to commit to the habit on a particular day, the "ic_<Day>_positive" icon
        // will be shown. Similarly, the <Day>negative icon will be shown if the user chooses not to commit
        sundayIcon.setImageResource(R.drawable.ic_sunday_negative);
        mondayIcon.setImageResource(R.drawable.ic_monday_positive);
        tuesdayIcon.setImageResource(R.drawable.ic_tuesday_positive);
        wednesdayIcon.setImageResource(R.drawable.ic_wednesday_positive);
        thursdayIcon.setImageResource(R.drawable.ic_thursday_negative);
        fridayIcon.setImageResource(R.drawable.ic_friday_negative);
        saturdayIcon.setImageResource(R.drawable.ic_saturday_negative);

        // Todo: Change the habit date to the actual date from Firestore using habitID
        habitDate.setText(new SimpleDateFormat("MM/dd/yyyy").format(new Date()));

        // The code below deals with the past habit events. The HabitEventCustomList is used
        // to arrange and output the details
        // Todo: Change the past habit events' data below to actual data from Firestore using HabitID and HabitEventID
        String[] locations = {"", "", "", "", "", "", "", "", "", "", "", ""};
        Date[] dates = {new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date()};
        String[] descriptions = {"", "", "", "", "", "", "", "", "", "", "", ""};

        habitEventList = findViewById(R.id.past_habit_event_list);
        habitEventDataList = new ArrayList<>();

        for (int i = 0; i < locations.length; i++) {
            habitEventDataList.add(new HabitEventModel(null, locations[i], dates[i],
                    descriptions[i], null, habitIDs[i]));
        }

        habitEventAdapter = new HabitEventCustomList(this, habitEventDataList);
        habitEventList.setAdapter(habitEventAdapter);

        // Go Back
        ImageButton back = findViewById(R.id.view_habit_back_button);
        back.setOnClickListener(v -> {
            finish();
        });

        // Delete Habit
        ImageButton remove = findViewById(R.id.view_habit_remove_button);
        remove.setOnClickListener(v -> {
            // TODO: Remove Targeted Habit. Dialog To Confirm?
        });
    }
}
