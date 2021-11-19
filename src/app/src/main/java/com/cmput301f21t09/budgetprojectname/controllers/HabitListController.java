package com.cmput301f21t09.budgetprojectname.controllers;

import android.util.Log;

import com.cmput301f21t09.budgetprojectname.models.HabitModel;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

/**
 * Represents a Habit Event Controller that interfaces with FirestoreDB to
 * create, read, update, and delete HabitEvents.
 */
public class HabitListController {
    /**
     * TAG for HabitListController
     */
    private static final String TAG = "HabitListController";

    /**
     * Constructor for HabitListController
     */
    public HabitListController() {
    }

    /**
     * HabitEventListCallback interface
     */
    public interface HabitListCallback {
        void onCallback(ArrayList<HabitModel> habitEventList);
    }

    /**
     * Read all the habit documents in the habits collection
     *
     * @param hbLstCallback
     */
    public void readHabitList(HabitListCallback hbLstCallback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ArrayList<HabitModel> habitDataList = new ArrayList<>();
        CollectionReference collectionReference = db.collection("habits");
        collectionReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Append every document into habitEventDataList
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    HabitModel newHabitModel = HabitModel.getNewInstance().getResult();
                    newHabitModel.setId((String) doc.getId());
                    newHabitModel.setTitle((String) doc.getData().get("title"));
                    newHabitModel.setReason((String) doc.getData().get("reason"));
                    newHabitModel.setStartDate(((Timestamp) doc.getData().get("start_date")).toDate());
                    newHabitModel.setStreak((Long) doc.getData().get("streak"));
                    habitDataList.add(newHabitModel);
                }
                hbLstCallback.onCallback(habitDataList);
            } else {
                Log.d(TAG, "Error: ", task.getException());
            }
        });
    }
}
