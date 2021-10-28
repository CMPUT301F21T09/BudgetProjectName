package com.cmput301f21t09.budgetprojectname;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewHabitEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit_event);

        // TODO: Get Targeted Habit Event

        TextView habitTitle = findViewById(R.id.view_habit_event_habit_title);
        TextView habitEventLocation = findViewById(R.id.view_habit_event_habit_event_location);
        TextView habitEventDescription = findViewById(R.id.view_habit_event_habit_event_description);
        TextView habitEventDate = findViewById(R.id.view_habit_event_habit_event_date);
        // TODO: Set Proper Name of Habit, Location of Habit Event to the TextViews
//        habitTitle.setText();
//        habitEventLocation.setText();
//        habitEventDescription.setText();
//        habitEventDate.setText();

        ImageButton editHabitEvent = findViewById(R.id.view_habit_event_habit_event_edit_button);
        editHabitEvent.setOnClickListener(v -> {
            // TODO: Open Create/Edit Habit Event View for Targeted Habit Event
        });

        ImageView imageHabitEvent = findViewById(R.id.image);
        // TODO: Set Proper Image of Habit Event to the ImageView
//        imageHabitEvent.setImageBitmap();


        Button deleteHabitEvent = findViewById(R.id.view_habit_event_habit_event_delete_button);
        deleteHabitEvent.setOnClickListener(v -> {
            // TODO: Delete Targeted Habit Event
        });
    }
}
