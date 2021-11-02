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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class DefineHabitEventActivity extends AppCompatActivity {

    private TextView screenTitle;
    private TextView habitEventName;
    private EditText location;
    private EditText comment;
    private ImageView image;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "DefineHabitEventActivity";

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
        screenTitle = (TextView) findViewById(R.id.CreateEditHabitEventTitle);
        screenTitle.setText(modeStr);

        habitEventName = (TextView) findViewById(R.id.habiteventName);
        location = (EditText) findViewById(R.id.location);
        comment = (EditText) findViewById(R.id.comment);
        image = (ImageView) findViewById(R.id.image);

        final Button checkBtn = findViewById(R.id.checkBtn);
        checkBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String locationStr = location.getText().toString();
                String commentStr = comment.getText().toString();
                // error checking/handling for adding optional comment of up to 20 chars
                if(commentStr.length() > 20 ){
                    comment.setError("Comment field can't be more than 20 chars.");
                    comment.requestFocus();
                    Toast.makeText(getApplicationContext(), "ERROR: could not save habit event",
                            Toast.LENGTH_SHORT).show();
                } else{
                    HabitEventModel habitEvent = new HabitEventModel(locationStr, new Date(),
                            commentStr);
                    if (isNewHabitEvent) {
                        storeNewHabitEvent(habitEvent);
                    } else {
                        storeEditedHabitEvent(habitEventID, habitEvent);
                        setHabitEventFields(habitEventID);
                    }
                }
            }
        });
    }

    /**
     * Stores newly created habitEvent document in the habit_events collection
     *
     * @param habitEvent habitEvent to be added
     */
    private void storeNewHabitEvent(HabitEventModel habitEvent) {
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
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    /**
     * Stores modified habitEvent document in the habit_events collection
     *
     * @param habitEventID       ID of habitEvent to be updated
     * @param modifiedHabitEvent habitEvent to be updated
     */
    private void storeEditedHabitEvent(String habitEventID, HabitEventModel modifiedHabitEvent) {
        DocumentReference habitEventRef = db.collection("habit_events")
                .document(habitEventID);
        habitEventRef.update("comment", modifiedHabitEvent.getComment(),
                "location", modifiedHabitEvent.getLocation(),
                "image", modifiedHabitEvent.getImage())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
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
                        // TODO: Possible refactoring where document retrieval is
                        //  separate from setting fields

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