package com.cmput301f21t09.budgetprojectname;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

/**
 * Activity that makes the user to add/edit a habit event
 */
public class DefineHabitEventActivity extends AppCompatActivity {

    private TextView toolbarTitle;
    private TextView habitEventName;
    private EditText location;
    private EditText comment;
    private ImageView image;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "DefineHabitEventActivity";
    private HabitEventController habitEventController = new HabitEventController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_habit_event);

        Intent intent = getIntent();
        String habitEventID = intent.getStringExtra("HABIT_EVENT_ID");
        String habitID = intent.getStringExtra("HABIT_ID");
        System.out.println("*****habitID " + habitID);
        System.out.println("****HE id" + habitEventID);
        boolean isNewHabitEvent = (habitEventID == null);
        String modeStr;

        if (isNewHabitEvent) {
            modeStr = getString(R.string.createHabitEventMode);
        } else {
            modeStr = getString(R.string.editHabitEventMode);
            // sets existing habitEvent fields
            setHabitEventFields(habitEventID);
        }

        // update title according to mode selected: "add" or "edit"
        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(modeStr);

        habitEventName = (TextView) findViewById(R.id.habitName);

        location = (EditText) findViewById(R.id.location);
        comment = (EditText) findViewById(R.id.comment);
        image = (ImageView) findViewById(R.id.image);
        ImageButton doneBtn = findViewById(R.id.done);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String locationStr = location.getText().toString();
                String commentStr = comment.getText().toString();
                // error checking/handling for adding optional comment of up to 20 chars
                if (commentStr.length() > 20) {
                    comment.setError(getString(R.string.errorHabitEventComment));
                    comment.requestFocus();
                    Toast.makeText(getApplicationContext(), "ERROR: could not save habit event",
                            Toast.LENGTH_SHORT).show();
                } else {

                    String descriptionStr = comment.getText().toString();

                    HabitEventModel habitEvent = new HabitEventModel(null, locationStr, new Date(),
                            descriptionStr, null, habitID);

                    if (isNewHabitEvent) {
                        habitEventController.createHabitEvent(habitEvent, new HabitEventController.HabitEventIDCallback() {
                            @Override
                            public void onCallback(String habitEventID) {
                                // TODO: figure out what to add here
                                System.out.println("habitevent id " + habitEventID);
                                // return back to main habit list
                                Intent returnIntent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(returnIntent);

                            }
                        });
                    } else {
                        habitEventController.updateHabitEvent(habitEventID, habitEvent);
                        // return back to habit event detail page
                        Intent returnIntent = new Intent(getApplicationContext(), ViewHabitEventActivity.class);
                        final String HABIT_EVENT_ID = "HABIT_EVENT_ID";
                        returnIntent.putExtra(HABIT_EVENT_ID, habitEventID);
                        startActivity(returnIntent);
                    }
                }
            }
        });

        // Let User Add/Change their habit event image as click ImageView area
        image.setOnClickListener(v -> {
            // TODO: Let User Choose Image from Gallery or Take a Photo
        });
    }


    /**
     * Sets the fields with existing values from Firestore
     *
     * @param habitEventID ID of habitEvent to be retrieved
     */
    private void setHabitEventFields(String habitEventID) {
        DocumentReference docRef = db.collection("habit_events").document(habitEventID);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        // TODO: Move to HabitEventController once async request handling is solved

                        // set fields in view
                        location.setText(document.getString("location"));
                        comment.setText(document.getString("comment"));

                        // TODO: set image

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

}