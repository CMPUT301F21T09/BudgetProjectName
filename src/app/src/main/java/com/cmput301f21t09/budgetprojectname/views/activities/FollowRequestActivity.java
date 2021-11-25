package com.cmput301f21t09.budgetprojectname.views.activities;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cmput301f21t09.budgetprojectname.R;
import com.cmput301f21t09.budgetprojectname.controllers.HabitEventController;
import com.cmput301f21t09.budgetprojectname.controllers.UserController;
import com.cmput301f21t09.budgetprojectname.models.HabitEventModel;
import com.cmput301f21t09.budgetprojectname.models.HabitModel;
import com.cmput301f21t09.budgetprojectname.models.UserModel;
import com.cmput301f21t09.budgetprojectname.views.lists.FollowRequestCustomList;
import com.cmput301f21t09.budgetprojectname.views.lists.HabitEventCustomList;
import com.cmput301f21t09.budgetprojectname.views.lists.UserHabitCustomList;

import java.util.ArrayList;

/**
 * Class which handles accepting or declining an incoming follow request from another user
 */
public class FollowRequestActivity extends AppCompatActivity {

    // TODO: comment
    ArrayList<UserModel> followRequestDataList;
    ArrayAdapter<UserModel> followRequestAdapter;

    /**
     * Controller for fetching user details
     */
    private UserController userController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_request);

        // Get the Intent that started this activity and extract the user id string
        Intent intent = getIntent();
        String currentUserId = intent.getStringExtra("uid");
        System.out.println("uid " + currentUserId);

        // ListView setup
        ListView followRequestList = findViewById(R.id.follow_request_listview);

        // Set up the list and send an empty list to the view
        followRequestDataList = new ArrayList<>();
        followRequestAdapter = new FollowRequestCustomList(this, followRequestDataList);
        followRequestList.setAdapter(followRequestAdapter);
        userController = new UserController();
        userController.readUserFollowRequests(currentUserId, followRequests -> {
            for(String userID: followRequests){
                // get back the model using userID
                userController.readUser(userID, followRequestUser -> {
//                    System.out.println("user who wants to follow you " +
//                            followRequestUser.getFirstName() + " " + followRequestUser.getUID());
                    followRequestDataList.add(followRequestUser);
                    // update adapter that new users have been added to list
                    followRequestAdapter.notifyDataSetChanged();
                });
                // followRequestList.addAll(hbEvtLst);

            }
        });
       followRequestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                System.out.println("cliecked");
                followRequestAdapter.notifyDataSetChanged();
            }
        });


        // TODO: add back button
      }
}