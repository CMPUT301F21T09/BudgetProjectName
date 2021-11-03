package com.cmput301f21t09.budgetprojectname;

import static com.google.android.gms.common.util.CollectionUtils.mapOf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class DefineHabitEventActivity extends AppCompatActivity {

    private TextView toolbarTitle;
    private TextView habitEventName;
    private EditText location;
    private EditText description;
    private ImageView image;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "DefineHabitEventActivity";
    private HabitEventStore habitEventStore = new HabitEventStore();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_habit_event);

        Intent intent = getIntent();
        String habitEventID = intent.getStringExtra("HABIT_EVENT_ID");
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
        description = (EditText) findViewById(R.id.description);
        image = (ImageView) findViewById(R.id.image);

        ImageButton doneBtn = findViewById(R.id.done);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String locationStr = location.getText().toString();
                String descriptionStr = description.getText().toString();
                HabitEventModel habitEvent = new HabitEventModel(locationStr, new Date(), descriptionStr);
                // setters

                if (isNewHabitEvent) {
                    habitEventStore.createHabitEvent(habitEvent, new HabitEventStore.HabitEventIDCallback() {
                        @Override
                        public void onCallback(String habitEventID) {
                            // TODO: figure out what to add here
                            System.out.println("habitevent id " + habitEventID);
                            location.getText().clear();
                            description.getText().clear();
                        }
                    });
                    // habiteventcontroller.updateLoc(edittext)
                } else {
                    habitEventStore.updateHabitEvent(habitEventID, habitEvent);
                }

            }
        });


        //Let User Add/Change their habit event image as click ImageView area
        image.setOnClickListener(v -> {
            // TODO: Let User Choose Image from Gallery or Take a Photo
        });
    }


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
                        // TODO: Move to HabitEventStore once async request handling is solved

                        // set fields in view
                        location.setText(document.getString("location"));
                        description.setText(document.getString("comment"));
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