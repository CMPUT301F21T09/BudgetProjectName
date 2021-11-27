package com.cmput301f21t09.budgetprojectname.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.cmput301f21t09.budgetprojectname.R;

/**
 * This is an activity to be used for testing.
 * To begin execution/testing from this activity instead of mainActivity change AndroidManifest.xml
 * android:name=".MainActivity" to android:name=".ExampleActivity"
 */
public class ExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        Button viewHabitEventDetail = findViewById(R.id.example_view_habit_event_detail);
        viewHabitEventDetail.setOnClickListener(v -> startActivity(new Intent(this, ViewHabitEventActivity.class)));

        Button userLogin = findViewById(R.id.user_login);
        userLogin.setOnClickListener(v -> startActivity(new Intent(this, UserLoginActivity.class)));


        // TODO: move button into habitlist activity and pass habitID to intent
        final Button habitEventBtn = findViewById(R.id.createhabiteventBtn);
        habitEventBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DefineHabitEventActivity.class);
                // TODO: use actual habit event ID from habit screen
                final String HABIT_EVENT_ID = "HABIT_EVENT_ID";
                final String HABIT_ID = "HABIT_ID";
                String habitEventID = "OIrJQ32PmTPCiufwKsPF"; // example habit event I created
                String habitID = "m8AhoYbaYIyTi2dGua8t"; // example habit from firestore
                // intent.putExtra(HABIT_EVENT_ID, habitEventID);
                intent.putExtra(HABIT_ID, habitID);
                startActivity(intent);
            }
        });

        // TODO: Move this function to relate to each habit that a user has and pass habitID to intent
        final Button habitBtn = findViewById(R.id.viewHabitDetailsBtn);
        habitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewHabitActivity.class);
                final String HABIT_ID = "HABIT_ID";
                String habitID = "IdninLJ01WM1PD8e4NzX";
                intent.putExtra(HABIT_ID, habitID);
                startActivity(intent);
            }
        });

        // TODO: Move this function to its appropriate place
        final Button profileBtn = findViewById(R.id.another_user_profile);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AnotherUserProfileActivity.class);
                final String USER_ID = "USER_ID";
                String another_userID = "tFNyfQdVKahhh8kE38SdAHSSGz92";
                intent.putExtra(USER_ID, another_userID);
                startActivity(intent);
            }
        });
    }
}