package com.cmput301f21t09.budgetprojectname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

public class DefineHabitEventActivity extends AppCompatActivity {

    private TextView toolbarTitle;
    private TextView habitEventName;
    private EditText location;
    private EditText description;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_habit_event);

        Intent intent = getIntent();
        int habitEventID = intent.getIntExtra("HABIT_EVENT_ID", -1);

        String modeStr;
        // if intent not have habit event ID, then create a new habit event
        if (habitEventID == -1) {
            modeStr = getString(R.string.createHabitEventMode);
        } else {
            modeStr = getString(R.string.editHabitEventMode);
        }

        // update title according to mode selected: "add" or "edit"
        //  toolbarTitle = findViewById(R.id.toolbar_title);
        //  toolbarTitle.setText(modeStr);

        // TODO: query habitID in Firestore and populate this field with corresponding habitName
        habitEventName = (TextView) findViewById(R.id.habitName);
        location = (EditText) findViewById(R.id.location);
        description = (EditText) findViewById(R.id.description);
        image = (ImageView) findViewById(R.id.image);

        ImageButton doneBtn = findViewById(R.id.done);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String nameStr = habitEventName.getText().toString();
                String locationStr = location.getText().toString();
                String descriptionStr = description.getText().toString();

                HabitEventModel habitEvent = new
                        HabitEventModel(nameStr, locationStr, new Date(), descriptionStr);
                // TODO: save HabitEvent in Firestore DB
            }
        });
    }
}