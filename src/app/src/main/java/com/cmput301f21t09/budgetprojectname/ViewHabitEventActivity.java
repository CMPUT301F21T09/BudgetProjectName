package com.cmput301f21t09.budgetprojectname;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class ViewHabitEventActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "ViewHabitEventActivity";
    HabitEventController habitEventController = new HabitEventController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit_event);

        // TODO: Get Targeted Habit Event
        Intent intent = getIntent();
        String habitEventID = intent.getStringExtra("HABIT_EVENT_ID");
        String habitID = intent.getStringExtra("HABIT_ID");

        TextView habitTitle = findViewById(R.id.view_habit_event_habit_title);
        TextView habitEventLocation = findViewById(R.id.view_habit_event_habit_event_location);
        TextView habitEventDescription = findViewById(R.id.view_habit_event_habit_event_description);
        TextView habitEventDate = findViewById(R.id.view_habit_event_habit_event_date);
        // TODO: Set Proper Name of Habit, Location of Habit Event to the TextViews
        // habitTitle.setText();
        // habitEventLocation.setText();
        // habitEventDescription.setText();
        // habitEventDate.setText();

        ImageButton editHabitEvent = findViewById(R.id.view_habit_event_habit_event_edit_button);
        editHabitEvent.setOnClickListener(v -> {
            // TODO: Pass Targeted Habit Event Info to the Intent
            startActivity(new Intent(this, DefineHabitEventActivity.class));
        });

        ImageView imageHabitEvent = findViewById(R.id.image);
        // TODO: Set Proper Image of Habit Event to the ImageView
        // imageHabitEvent.setImageBitmap();


        Button deleteHabitEventBtn = findViewById(R.id.view_habit_event_habit_event_delete_button);
        deleteHabitEventBtn.setOnClickListener(v -> {
            // TODO: Get habitEventID from the habit detail screen (passed in Intent)

            System.out.println("habit event to delete " + habitEventID);
            habitEventController.deleteHabitEvent(habitEventID);
            startActivity(new Intent(this, ViewHabitActivity.class));
        });
    }



}
