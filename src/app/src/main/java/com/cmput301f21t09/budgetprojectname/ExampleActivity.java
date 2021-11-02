package com.cmput301f21t09.budgetprojectname;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This is an activity to be used for testing.
 * To begin execution/testing from this activity instead of mainActivity change AndroidManifest.xml
 * android:name=".MainActivity" --> android:name=".ExampleActivity"
 */
public class ExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        Button viewHabitEventDetail = findViewById(R.id.example_view_habit_event_detail);
        viewHabitEventDetail.setOnClickListener(v -> {
            startActivity(new Intent(this, ViewHabitEventActivity.class));
        });

        // TODO: move button into habitlist activity and pass habitID to intent
        final Button habitEventBtn = findViewById(R.id.createhabiteventBtn);
        habitEventBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DefineHabitEventActivity.class);
                // TODO: use actual habit event ID
                final String HABIT_EVENT_ID = "HABIT_EVENT_ID";
                String habitEventID = "v5BYLwHC2W4wGHkjff9K"; // example habit event I created
                // intent.putExtra(HABIT_EVENT_ID, habitEventID);
                startActivity(intent);
            }
        });
    }
}
