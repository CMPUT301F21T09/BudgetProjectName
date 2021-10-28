package com.cmput301f21t09.budgetprojectname;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        Button viewHabitEventDetail = findViewById(R.id.example_view_habit_event_detail);
        viewHabitEventDetail.setOnClickListener(v -> {
            startActivity(new Intent(this, ViewHabitEventActivity.class));
        });
    }
}
