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
 *
 */
public class HabitListController {
    private FirebaseFirestore dbStore = FirebaseFirestore.getInstance();
    private static final String TAG = "HabitListController";
    public HabitEventModel retrievedHabitEventModel;

    public HabitListController(){}

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
        collectionReference
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Append every document into habitEventDataList
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                System.out.println("******");
                                System.out.println(doc.getId());
                                System.out.println(doc.getData().get("title"));

                                String ID = (String) doc.getId();
                                String title = (String) doc.getData().get("title");
                                String reason = (String) doc.getData().get("reason");
                                Date startDate  = ((Timestamp) doc.getData().get("start_date")).toDate();
//                                Date lastCompleted  = ((Timestamp) doc.getData().get("lastCompleted")).toDate();
                                int streak = ((Long) doc.getData().get("streak")).intValue();

                                habitDataList.add(new HabitModel(ID, title, reason, startDate, null, streak));
//                                String location = (String) doc.getData().get("location");
//                                String comment = (String) doc.getData().get("comment");
//                                Image image = (Image) doc.getData().get("image");
//                                habitEventDataList.add(new HabitEventModel(id, location, date, comment, image, habitID));

//                                habitDataList.add(doc.toObject(HabitModel.class));
                            }
                            hbLstCallback.onCallback(habitDataList);
                        } else {
                            Log.d(TAG, "Error: ", task.getException());
                        }
                    }
                });
    }
}
