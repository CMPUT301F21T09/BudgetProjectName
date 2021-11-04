package com.cmput301f21t09.budgetprojectname;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Date;

public class HabitEventControllerTest {
    // private FirebaseFirestore db = FirebaseFirestore.getInstance();
    // private HabitEventController;
    // private FirebaseFirestore db;
    private static final String TAG = "HabitEventStoreTEST";
    HabitEventModel testHabitEvent;

    @Test
    public void testCreateHabitEvent(){
        HabitEventController habitEventController = new HabitEventController();
        HabitEventModel habitEvent =
                new HabitEventModel(null, "YEGtest", new Date(),
                        "yeg test comment", null,"");

        habitEventController.createHabitEvent(habitEvent, new HabitEventController.HabitEventIDCallback() {
            @Override
            public void onCallback(String habitEventID) {
                // TODO: store the habiteventid
                System.out.println("habitevent id " + habitEventID);
            }
        });
        // TODO: make another call to habitEventController.getHabitEvent(habiteventid, callback)
        //      to check if the habit event was successfully added
    }

    /**
     * Old test which did not work because async handling was not working yet
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
                        // TODO: Move to HabitEventController once async request handling is solved
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
