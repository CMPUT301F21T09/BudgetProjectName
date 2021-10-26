package com.cmput301f21t09.budgetprojectname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Move this function to relate to each habit that a user has and pass habitID to intent
        final Button habitEventBtn = findViewById(R.id.viewHabitDetailsBtn);
        habitEventBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewHabitDetails.class);
                final String HABIT_ID = "HABIT_ID";
                int habitid = 1;
                intent.putExtra(HABIT_ID, habitid);
                startActivity(intent);
            }
        });
    }
}