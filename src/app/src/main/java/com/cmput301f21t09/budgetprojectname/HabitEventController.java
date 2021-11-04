package com.cmput301f21t09.budgetprojectname;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

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
//    public void updateHabitEvent(String habitEventID, HabitEventModel modifiedHabitEvent) {
//        DocumentReference habitEventRef = dbStore.collection("habit_events")
//                .document(habitEventID);
//        habitEventRef.update("comment", modifiedHabitEvent.getComment(),
//                "location", modifiedHabitEvent.getLocation(),
//                "image", modifiedHabitEvent.getImage())
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d(TAG, "DocumentSnapshot successfully updated!");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error updating document", e);
//                    }
//                });
//    }
    // updateLoc()
    /**
     * new HEModel(oldHE stuff, new loc)
     * cachedModel = heModel
     * notifyListeners()
     * commit()
     */

    /**
     * Read habitEvent document in the habit_events collection
     *
     * @param habitID habitID is used to query all the corresponding habit events
     */
//    public void readHabitEvent(String habitID, HabitEventListCallback hbEvtLstCallback) {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        ArrayList<HabitEventModel> habitEventDataList = new ArrayList<>();
//        CollectionReference collectionReference = db.collection("habit_events");
//        collectionReference
//                .whereEqualTo("habitID", habitID)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot doc : task.getResult()) {
////                                System.out.println(doc.getId());
//                                // Todo: Fix image type
//                                if (nonNull(doc.getData().get("location")) && nonNull(doc.getData().get("comment")) && nonNull(doc.getData().get("image"))) {
//                                    // This particular habit event has a location, a comment, and an image
//
//                                    String id = (String) doc.getId();
//                                    Date date  = ((Timestamp) doc.getData().get("date")).toDate();
//                                    String location = (String) doc.getData().get("location");
//                                    String comment = (String) doc.getData().get("comment");
//                                    Image image = (Image) doc.getData().get("image");
//
//                                    habitEventDataList.add(new HabitEventModel(id, location, date, comment, image, habitID));
//                                } else if (nonNull(doc.getData().get("location")) && nonNull(doc.getData().get("comment")) && isNull(doc.getData().get("image"))) {
//                                    // This particular habit event has a location and a comment. (No image)
//                                    String id = (String) doc.getId();
//                                    Date date  = ((Timestamp) doc.getData().get("date")).toDate();
//                                    String location = (String) doc.getData().get("location");
//                                    System.out.println(location);
//                                    String comment = (String) doc.getData().get("comment");
//
//                                    habitEventDataList.add(new HabitEventModel(id, location, date, comment, null, habitID));
//                                } else if (nonNull(doc.getData().get("location")) && nonNull(doc.getData().get("image")) && isNull(doc.getData().get("comment"))) {
//                                    // This particular habit event has a location and an image. (No comment)
//
//                                    String id = (String) doc.getId();
//                                    Date date  = ((Timestamp) doc.getData().get("date")).toDate();
//                                    String location = (String) doc.getData().get("location");
//                                    Image image = (Image) doc.getData().get("image");
//
//                                    habitEventDataList.add(new HabitEventModel(id, location, date, null, image, habitID));
//                                } else if (nonNull(doc.getData().get("comment")) && nonNull(doc.getData().get("image")) && isNull(doc.getData().get("location"))) {
//                                    // This particular habit event has a comment and an image. (No location)
//
//                                    String id = String.valueOf(doc.getId());
//                                    Date date  = ((Timestamp) doc.getData().get("date")).toDate();
//                                    String location = (String) doc.getData().get("location");
//                                    String comment = (String) doc.getData().get("comment");
//                                    Image image = (Image) doc.getData().get("image");
//
//                                    habitEventDataList.add(new HabitEventModel(id, null, date, comment, image, habitID));
//
//                                } else if (nonNull(doc.getData().get("location")) && isNull(doc.getData().get("comment")) && isNull(doc.getData().get("image"))) {
//                                    // This particular habit event has a location. (No image and no comment)
//
//                                    String id = (String) doc.getId();
//                                    String location = (String) doc.getData().get("location");
//                                    Date date  = ((Timestamp) doc.getData().get("date")).toDate();
//
//                                    habitEventDataList.add(new HabitEventModel(id, location, date, habitID));
//                                } else if (nonNull(doc.getData().get("comment")) && isNull(doc.getData().get("location")) && isNull(doc.getData().get("image"))) {
//                                    // This particular habit event has a comment. (No location and no image)
//
//                                    String id = (String) doc.getId();
//                                    String location = (String) doc.getData().get("location");
//                                    String comment = (String) doc.getData().get("comment");
//                                    Date date  = ((Timestamp) doc.getData().get("date")).toDate();
//
//                                    habitEventDataList.add(new HabitEventModel(id, null, date, comment, null, habitID));
//                                } else if (nonNull(doc.getData().get("image")) && isNull(doc.getData().get("location")) && isNull(doc.getData().get("comment"))) {
//                                    // This particular habit event has an image. (No location and no comment)
//
//                                    String id = (String) doc.getId();
//                                    String location = (String) doc.getData().get("location");
//                                    Date date  = ((Timestamp) doc.getData().get("date")).toDate();
//                                    Image image = (Image) doc.getData().get("image");
//
//                                    habitEventDataList.add(new HabitEventModel(id, null, date, null, image, habitID));
//                                } else if (nonNull(doc.getData().get("location")) && nonNull(doc.getData().get("comment")) && isNull(doc.getData().get("image"))) {
//                                    // This particular habit event has no location, no comment, and no image
//
//                                    String id = (String) doc.getId();
//                                    Date date  = ((Timestamp) doc.getData().get("date")).toDate();
//                                    habitEventDataList.add(new HabitEventModel(id, date, habitID));
//                                } else {
//                                    Log.d(TAG, "Error with icons");
//                                }
//                            }
//                            hbEvtLstCallback.onCallback(habitEventDataList);
//                        } else {
//                            Log.d(TAG, "Error: ", task.getException());
//                        }
//                    }
//                });
//    }

    public void readHabitEvent(String habitID, HabitEventListCallback hbEvtLstCallback) {
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
                            for (QueryDocumentSnapshot doc : task.getResult()) {
//                                System.out.println(doc.getId());
                                // Todo: Fix image type
                                if (nonNull(doc.getData().get("location")) && nonNull(doc.getData().get("comment")) && nonNull(doc.getData().get("image"))) {
                                    // This particular habit event has a location, a comment, and an image

                                    String id = (String) doc.getId();
                                    Date date  = ((Timestamp) doc.getData().get("date")).toDate();
                                    String location = (String) doc.getData().get("location");
                                    String comment = (String) doc.getData().get("comment");
                                    Image image = (Image) doc.getData().get("image");

                                    habitEventDataList.add(new HabitEventModel(location, date, comment, image, habitID));
                                } else if (nonNull(doc.getData().get("location")) && nonNull(doc.getData().get("comment")) && isNull(doc.getData().get("image"))) {
                                    // This particular habit event has a location and a comment. (No image)
                                    String id = (String) doc.getId();
                                    Date date  = ((Timestamp) doc.getData().get("date")).toDate();
                                    String location = (String) doc.getData().get("location");
                                    System.out.println(location);
                                    String comment = (String) doc.getData().get("comment");

                                    habitEventDataList.add(new HabitEventModel(location, date, comment, habitID));
                                } else if (nonNull(doc.getData().get("location")) && nonNull(doc.getData().get("image")) && isNull(doc.getData().get("comment"))) {
                                    // This particular habit event has a location and an image. (No comment)

                                    String id = (String) doc.getId();
                                    Date date  = ((Timestamp) doc.getData().get("date")).toDate();
                                    String location = (String) doc.getData().get("location");
                                    Image image = (Image) doc.getData().get("image");

                                    habitEventDataList.add(new HabitEventModel(location, date, image, habitID));
                                } else if (nonNull(doc.getData().get("comment")) && nonNull(doc.getData().get("image")) && isNull(doc.getData().get("location"))) {
                                    // This particular habit event has a comment and an image. (No location)

                                    String id = String.valueOf(doc.getId());
                                    Date date  = ((Timestamp) doc.getData().get("date")).toDate();
                                    String location = (String) doc.getData().get("location");
                                    String comment = (String) doc.getData().get("comment");
                                    Image image = (Image) doc.getData().get("image");

                                    habitEventDataList.add(new HabitEventModel(null, date, comment, image, habitID));

                                } else if (nonNull(doc.getData().get("location")) && isNull(doc.getData().get("comment")) && isNull(doc.getData().get("image"))) {
                                    // This particular habit event has a location. (No image and no comment)

                                    String id = (String) doc.getId();
                                    String location = (String) doc.getData().get("location");
                                    Date date  = ((Timestamp) doc.getData().get("date")).toDate();

                                    habitEventDataList.add(new HabitEventModel(location, date, null, null, habitID));
                                } else if (nonNull(doc.getData().get("comment")) && isNull(doc.getData().get("location")) && isNull(doc.getData().get("image"))) {
                                    // This particular habit event has a comment. (No location and no image)

                                    String id = (String) doc.getId();
                                    String location = (String) doc.getData().get("location");
                                    String comment = (String) doc.getData().get("comment");
                                    Date date  = ((Timestamp) doc.getData().get("date")).toDate();

                                    habitEventDataList.add(new HabitEventModel(null, date, comment, null, habitID));
                                } else if (nonNull(doc.getData().get("image")) && isNull(doc.getData().get("location")) && isNull(doc.getData().get("comment"))) {
                                    // This particular habit event has an image. (No location and no comment)

                                    String id = (String) doc.getId();
                                    String location = (String) doc.getData().get("location");
                                    Date date  = ((Timestamp) doc.getData().get("date")).toDate();
                                    Image image = (Image) doc.getData().get("image");

                                    habitEventDataList.add(new HabitEventModel(null, date, image, habitID));
                                } else if (nonNull(doc.getData().get("location")) && nonNull(doc.getData().get("comment")) && isNull(doc.getData().get("image"))) {
                                    // This particular habit event has no location, no comment, and no image

                                    String id = (String) doc.getId();
                                    Date date  = ((Timestamp) doc.getData().get("date")).toDate();
                                    habitEventDataList.add(new HabitEventModel(date, habitID));
                                } else {
                                    Log.d(TAG, "Error with icons");
                                }
                            }
                            hbEvtLstCallback.onCallback(habitEventDataList);
                        } else {
                            Log.d(TAG, "Error: ", task.getException());
                        }
                    }
                });
    }

    // TODO: add delete scenario
}