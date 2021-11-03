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
 * Represents a Habit Event Controller that interfaces with FirestoreDB to
 * create, read, update, and delete HabitEvents.
 *
 */
public class HabitEventController {
    private FirebaseFirestore dbStore = FirebaseFirestore.getInstance();
    private static final String TAG = "HabitEventController";
    public HabitEventModel retrievedHabitEventModel;


//    // Apply Singleton Design Pattern
//    private static HabitEventController habitEventStore = new HabitEventController();
//
//    public static HabitEventController getInstance(){
//        return habitEventStore;
//    }

    public HabitEventController(){}

    public interface HabitEventCallback{
        void onCallback(HabitEventModel habitEvent);
    }

    public interface HabitEventIDCallback{
        void onCallback(String habitEventID);
    }

    /**
     * Creates habitEvent document in the habit_events collection
     *
     * @param habitEvent habitEvent to be added
     */
    public void createHabitEvent(HabitEventModel habitEvent, HabitEventIDCallback idCallback) {
        // db = FirebaseFirestore.getInstance();
        System.out.println("store new habit");
        dbStore.collection("habit_events")
                .add(habitEvent)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String docID = documentReference.getId();
                        Log.d(TAG, "DocumentSnapshot added with ID: " + docID);
                        System.out.println(docID);
                        idCallback.onCallback(docID);
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
        DocumentReference habitEventRef = dbStore.collection("habit_events")
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
    // updateLoc()
    /**
     * new HEModel(oldHE stuff, new loc)
     * cachedModel = heModel
     * notifyListeners()
     * commit()
     */
    // TODO: add read scenario
    // TODO: add delete scenario
}
