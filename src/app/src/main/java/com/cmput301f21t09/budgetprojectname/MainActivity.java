package com.cmput301f21t09.budgetprojectname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: move button into habitlist activity and pass habitID to intent
        final Button habitEventBtn = findViewById(R.id.createhabiteventBtn);
        habitEventBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateEditHabitEvent.class);
                // TODO: use actual habitid
                final String HABIT_ID = "HABIT_ID";
                // Modes are: "createHabitEventMode" OR "editHabitEventMode"
                final String MODE = "MODE";
                int habitid = 1;
                intent.putExtra(HABIT_ID, habitid);
                intent.putExtra(MODE, R.string.editHabitEventMode);
                startActivity(intent);
            }
        });
    }
}