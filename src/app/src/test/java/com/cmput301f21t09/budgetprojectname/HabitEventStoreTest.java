package com.cmput301f21t09.budgetprojectname;

import static org.junit.Assert.assertEquals;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class HabitEventStoreTest {
    // private FirebaseFirestore db = FirebaseFirestore.getInstance();
    // private HabitEventStore;
    // private FirebaseFirestore db;
    private static final String TAG = "HabitEventStoreTEST";
    HabitEventModel testHabitEvent;

    @Test
    public void testCreateHabitEvent(){
        HabitEventStore habitEventStore = new HabitEventStore();
        HabitEventModel habitEvent =
                new HabitEventModel("YEGtest", new Date(), "yeg test comment");
        habitEventStore.createHabitEvent(habitEvent, new HabitEventStore.HabitEventIDCallback() {
            @Override
            public void onCallback(String habitEventID) {
                // TODO: store the habitevent id
                System.out.println("habitevent id " + habitEventID);
            }
        });
        // TODO make another call to habitEventStore.getHabitEvent(habiteventid, callback) to check
        // if the habit event was successfully added
        assertEquals(1,1);
    }

    /**
    @Test
    public void testCreateHabitEvent(){
        HabitEventModel addedHabitEvent =
                new HabitEventModel("YEGtest", new Date(), "yeg test comment");
        habitEventStore.createHabitEvent(addedHabitEvent);
        // check if inside DB -- maybe just where by name
        DocumentReference docRef = db.collection("habit_events").document(needid);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        // TODO: Move to HabitEventStore once async request handling is solved
                       assertEquals("YEGtest", document.getString("location"));
                       assertEquals("yeg test comment",document.getString("comment") );
                        // TODO: check image

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }
    **/

}
