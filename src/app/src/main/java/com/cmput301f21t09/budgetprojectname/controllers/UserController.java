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
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;

public class UserController {
    private final FirebaseFirestore dbStore = FirebaseFirestore.getInstance();
    private static final String TAG = "UserController";

    public UserController() {
    }

    public interface UserCallback {
        void onCallback(UserModel user);
    }

//    public interface HabitEventIDCallback {
//        void onCallback(String habitEventID);
//    }

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
                        String id = (String) doc.getId();
                        String username = (String) doc.getData().get("username");
                        String firstname = (String) doc.getData().get("firstname");
                        String lastname = (String) doc.getData().get("lastname");
                        HashMap<String, Integer> social = (HashMap<String, Integer>) doc.getData().get("social");
                        System.out.println("****isFollowing: "+ social);
                        // Set up the UserModel
                        UserModel retrievedUserModel = new UserModel();
                        retrievedUserModel.setUsername(username);
                        retrievedUserModel.setUID(id);
                        retrievedUserModel.setFirstName(firstname);
                        retrievedUserModel.setLastName(lastname);
                        retrievedUserModel.setSocial(social);

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
     * Updates user document in the users collection
     *
     * @param userID       ID of user to be updated
     * @param follow       isFollow to be updated
     */
    public void updateUser(String userID, HashMap<String, Integer> follow) {
        DocumentReference habitEventRef = dbStore.collection("users")
                .document(userID);
        habitEventRef.update("social", follow)

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
}
