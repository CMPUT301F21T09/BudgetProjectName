package com.cmput301f21t09.budgetprojectname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        Intent intent = getIntent();
        int habitID = intent.getIntExtra("HABIT_ID", -1);

        // TODO: query habitID in Firestore and populate this field with corresponding habitName
        habitEventName = (TextView) findViewById(R.id.habiteventName);
        location = (EditText) findViewById(R.id.location);
        description = (EditText) findViewById(R.id.description);
        date = (DatePicker) findViewById(R.id.datePicker);
        image = (ImageView) findViewById(R.id.image);

        // TODO: add onclick event to save as HabitEvent object
    }
}