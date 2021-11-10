package com.cmput301f21t09.budgetprojectname;

import android.media.Image;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a Habit Event Controller that interfaces with FirestoreDB to
 * create, read, update, and delete HabitEvents.
 */
public class HabitEventController {
    private final FirebaseFirestore dbStore = FirebaseFirestore.getInstance();
    private static final String TAG = "HabitEventController";
//    // Apply Singleton Design Pattern
//    private static HabitEventController habitEventStore = new HabitEventController();
//
//    public static HabitEventController getInstance(){
//        return habitEventStore;
//    }

    public HabitEventController() {
    }

    public interface HabitEventCallback {
        void onCallback(HabitEventModel habitEvent);
    }

    public interface HabitEventIDCallback {
        void onCallback(String habitEventID);
    }


    /**
     * HabitEventListCallback interface
     */
    public interface HabitEventListCallback {
        void onCallback(ArrayList<HabitEventModel> habitEventList);
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
        System.out.println("updated habit id" + modifiedHabitEvent.getHabitID());
        DocumentReference habitEventRef = dbStore.collection("habit_events")
                .document(habitEventID);
        habitEventRef.update("comment", modifiedHabitEvent.getComment(),
                "location", modifiedHabitEvent.getLocation(),
                "image", modifiedHabitEvent.getImage(),
                "habitID", modifiedHabitEvent.getHabitID())

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
     * Deletes habit event from Firestore DB
     *
     * @param habitEventID id of habit event to be deleted
     */
    public void deleteHabitEvent(String habitEventID) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("habit_events").document(habitEventID)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }

    /**
     * Gets existing habitevent from Firestore Db
     *
     * @param habitEventID ID of habitEvent to be retrieved
     */
    public void readHabitEvent(String habitEventID, HabitEventCallback habitEventCallback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("habit_events").document(habitEventID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + doc.getData());
                        String id = (String) doc.getId();
                        Date date = ((Timestamp) doc.getData().get("date")).toDate();
                        String location = (String) doc.getData().get("location");
                        String comment = (String) doc.getData().get("comment");
                        Image image = (Image) doc.getData().get("image");
                        String habitID = (String) doc.getData().get("habitID");
                        HabitEventModel retrievedHabitEventModel =
                                new HabitEventModel(id, location, date, comment, image, habitID);
                        habitEventCallback.onCallback(retrievedHabitEventModel);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    /**
     * @param habitID          habitID is used to query all the corresponding habit events
     * @param hbEvtLstCallback Callback function
     */
    public void readHabitEvents(String habitID, HabitEventListCallback hbEvtLstCallback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ArrayList<HabitEventModel> habitEventDataList = new ArrayList<>();
        CollectionReference collectionReference = db.collection("habit_events");
        collectionReference
                .whereEqualTo("habitID", habitID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Append every document into habitEventDataList
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                habitEventDataList.add(doc.toObject(HabitEventModel.class));
                            }
                            hbEvtLstCallback.onCallback(habitEventDataList);
                        } else {
                            Log.d(TAG, "Error: ", task.getException());
                        }
                    }
                });
    }

}



