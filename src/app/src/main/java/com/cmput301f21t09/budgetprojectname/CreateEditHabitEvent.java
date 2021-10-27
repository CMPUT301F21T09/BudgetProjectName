package com.cmput301f21t09.budgetprojectname;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

public class CreateEditHabitEvent extends AppCompatActivity {

    private TextView screenTitle;
    private TextView habitEventName;
    private EditText location;
    private EditText description;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_habit_event);

        Intent intent = getIntent();
        int habitID = intent.getIntExtra("HABIT_ID", -1);
        int mode = intent.getIntExtra("MODE", R.string.createHabitEventMode);
        String modeStr = getString(mode);

        // update title according to mode selected: "add" or "edit"
        screenTitle = (TextView) findViewById(R.id.CreateEditHabitEventTitle);
        screenTitle.setText(modeStr);

        // TODO: query habitID in Firestore and populate this field with corresponding habitName
        habitEventName = (TextView) findViewById(R.id.habiteventName);
        location = (EditText) findViewById(R.id.location);
        description = (EditText) findViewById(R.id.description);
        image = (ImageView) findViewById(R.id.image);

        final Button checkBtn = findViewById(R.id.checkBtn);
        checkBtn.setOnClickListener(new View.OnClickListener() {
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