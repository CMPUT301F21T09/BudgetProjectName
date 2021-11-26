package com.cmput301f21t09.budgetprojectname.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.cmput301f21t09.budgetprojectname.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Controller which handles the interaction with the users collection in firestore
 */
public class UserController {
    private final FirebaseFirestore dbStore = FirebaseFirestore.getInstance();
    private static final String TAG = "UserController";

    public UserController() {
    }

    public interface UserCallback {
        void onCallback(UserModel user);
    }

    public interface UsersCallback {
        void onCallback(ArrayList<String> userIDs);
    }

    /**
     * Gets existing user from Firestore Db
     *
     * @param userID ID of habitEvent to be retrieved
     */
    public void readUser(String userID, UserController.UserCallback userCallback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(userID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + doc.getData());
                        UserModel retrievedUserModel = doc.toObject(UserModel.class);

                        userCallback.onCallback(retrievedUserModel);
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
     * Gets existing user from Firestore Db
     *
     * @param username of user to retrieve
     */
    public void readUserByUserName(String username, UserController.UserCallback userCallback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query query = db.collection("users").orderBy("username").startAt(username).endAt(username + '\uf8ff');
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<DocumentSnapshot> docs = task.getResult().getDocuments();
                if (docs.size() > 0) {
                    for (DocumentSnapshot doc : docs) {
                        Log.d(TAG, "DocumentSnapshot data: " + doc.getData());
                        UserModel retrievedUserModel = doc.toObject(UserModel.class);

                        userCallback.onCallback(retrievedUserModel);
                    }
                } else {
                    Log.d(TAG, "No such document");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });
    }

    /**
     * Updates user document's Social map in the users collection
     *
     * @param userID ID of user to be updated
     * @param social Social hashmap whose value is to be updated
     */
    public void updateUserSocialMap(String userID, HashMap<String, Integer> social) {
        DocumentReference habitEventRef = dbStore.collection("users")
                .document(userID);
        habitEventRef.update("social", social)
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
     * Gets follow requests for existing user from Firestore Db
     *
     * @param userID ID of user whose follow requests we are retrieving
     */
    // TODO: generalize this to get users that the current user is following (user:1)
    public void readUserFollowRequests(String userID, UserController.UsersCallback usersCallback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(userID);
        // arrayList of userids of users who are requesting to follow us
        ArrayList<String> followRequests = new ArrayList<>();
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + doc.getData());
                        HashMap<String, Integer> social =
                                (HashMap<String, Integer>) doc.getData().get("social");

                        // find all the users with "userid" : 0 meaning they
                        // are requesting to follow us or  "userid" : 2 meaning both of us are
                        // requesting to follow one another
                        for (String userid : social.keySet()) {
                            int value = Integer.parseInt(String.valueOf(social.get(userid)));
                            System.out.println("userid " + userid + "value " + value);
                            if (value == 0 || value == 2) {
                                System.out.println("This is a follow request!");
                                followRequests.add(userid);
                            }
                        }
                        // return back the list of user ids
                        usersCallback.onCallback(followRequests);
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
