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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit_event);

        // TODO: Get Targeted Habit Event

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
            // TODO: Delete Targeted Habit Event
            deleteHabitEvent("AhDMiDRXIxD3hxop997V");

        });
    }

    /**
     * Deletes habit event from Firestore DB
     * @param habitEventID
     */
    public void deleteHabitEvent(String habitEventID){
        // TODO: refactor and move to HabitEventStore class
        db.collection("habit_events").document(habitEventID)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }

}
