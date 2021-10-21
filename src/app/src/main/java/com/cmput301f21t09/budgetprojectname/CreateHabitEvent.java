package com.cmput301f21t09.budgetprojectname;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class CreateHabitEvent extends AppCompatActivity {

    private TextView habitEventName;
    private EditText location;
    private EditText description;
    private DatePicker date;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_habit_event);

        // TODO: get ID from Intent (from HabitListActivity)

        // TODO: query ID in Firestore, populate this field with habitName
        habitEventName = (TextView) findViewById(R.id.habiteventName);
        location = (EditText) findViewById(R.id.location);
        description = (EditText) findViewById(R.id.description);
        date = (DatePicker) findViewById(R.id.datePicker);
        image = (ImageView) findViewById(R.id.image);

        // TODO: add onclick event
    }
}