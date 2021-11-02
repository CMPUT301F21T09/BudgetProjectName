package com.cmput301f21t09.budgetprojectname;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Represents a habitEventStore that interfaces with FirestoreDB to
 * create, read, update, and delete HabitEvents.
 *
 */
public class HabitEventStore {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "HabitEventStore";
    public HabitEventModel retrievedHabitEventModel;

    // Apply Singleton Design Pattern
    private static HabitEventStore habitEventStore = new HabitEventStore();

    private HabitEventStore(){}

    public static HabitEventStore getInstance(){
        return habitEventStore;
    }

    /**
     * Creates habitEvent document in the habit_events collection
     *
     * @param habitEvent habitEvent to be added
     */
    public void createHabitEvent(HabitEventModel habitEvent) {
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
     * Updates habitEvent document in the habit_events collection
     *
     * @param habitEventID       ID of habitEvent to be updated
     * @param modifiedHabitEvent habitEvent to be updated
     */
    public void updateHabitEvent(String habitEventID, HabitEventModel modifiedHabitEvent) {
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
    // TODO: add read scenario
    // TODO: add delete scenario
}
