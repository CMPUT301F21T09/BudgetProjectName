package com.cmput301f21t09.budgetprojectname;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class DefineHabitEventActivity extends AppCompatActivity {

    private TextView screenTitle;
    private TextView habitEventName;
    private EditText location;
    private EditText description;
    private ImageView image;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "DefineHabitEventActivity";

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

                HabitEventModel habitEvent = new HabitEventModel(locationStr, new Date(), descriptionStr);
                // TODO: save HabitEvent in Firestore DB
                storeNewHabitEvent(habitEvent);

            }
        });
    }

    private void storeNewHabitEvent(HabitEventModel habitEvent){
        System.out.println("store new habit");
        db.collection("habit_events")
                .add(habitEvent)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String docID = documentReference.getId();
                        Log.d(TAG, "DocumentSnapshot added with ID: " + docID);
                        System.out.println(docID);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("error!" + e);
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
    // TODO: create method to update existing HabitEvent using docID
}
