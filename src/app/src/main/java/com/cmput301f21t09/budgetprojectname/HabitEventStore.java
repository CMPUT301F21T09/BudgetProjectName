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
     * Stores newly created habitEvent document in the habit_events collection
     *
     * @param habitEvent habitEvent to be added
     */
    public void storeNewHabitEvent(HabitEventModel habitEvent) {
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
    public void storeEditedHabitEvent(String habitEventID, HabitEventModel modifiedHabitEvent) {
        DocumentReference habitEventRef = db.collection("habit_events")
                .document(habitEventID);
        habitEventRef.update("description", modifiedHabitEvent.getDescription(),
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
    public HabitEventModel getHabitEvent(String habitEventID) {
        DocumentReference docRef = db.collection("habit_events").document(habitEventID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        // This onComplete function is async so passing document to this
                        // helper function indicates it has been completed
                        parseSnapshot(document);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        return retrievedHabitEventModel;
    }

    private void parseSnapshot(DocumentSnapshot document){
        // convert document to Plain Old Java Object (POJO)
        retrievedHabitEventModel = document.toObject(HabitEventModel.class);
        System.out.println("here: " + retrievedHabitEventModel.getLocation());
    }

}
