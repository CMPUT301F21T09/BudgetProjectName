package com.cmput301f21t09.budgetprojectname.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cmput301f21t09.budgetprojectname.R;
import com.cmput301f21t09.budgetprojectname.controllers.UserController;
import com.cmput301f21t09.budgetprojectname.models.HabitModel;
import com.cmput301f21t09.budgetprojectname.models.UserModel;
import com.cmput301f21t09.budgetprojectname.services.AuthorizationService;
import com.cmput301f21t09.budgetprojectname.views.lists.UserHabitCustomList;

import java.util.ArrayList;
import java.util.HashMap;

public class AnotherUserProfileActivity extends AppCompatActivity {

    /* Controllers */

    /**
     * Controller for fetching user details
     */
    private UserController userController;

    /* Views */

    /**
     * Text view for the name of the user
     */
    private TextView name;

    /**
     * Text view for the name of the user
     */
    private TextView username;

    /**
     * Button view to follow this user or to show a follow request has been sent
     */
    private Button followBtn;

    /**
     * Text view to show that the logged in user has already followed this user
     */
    private TextView followingLabel;

    /**
     * Text view to show that the logged in user needs to follow this user to see their habits
     */
    private TextView followThisAccountLabel;

    /**
     * Text view for the All habits label
     */
    private TextView allHabitsLabel;

    /**
     * List View for public habits
     */
    private ListView habitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_user_profile);

        // Set up the back button
        ImageButton back = findViewById(R.id.another_user_profile_back_button);
        back.setOnClickListener(v -> {
            finish();
        });

        // Retrieve the logged in user's userid
        String currentUserId = AuthorizationService.getInstance().getCurrentUserId();

        // Get the another user's ID from the previous activity
        Intent intent = getIntent();
        String anotherUserID = intent.getStringExtra("USER_ID");

        // Retrieve the specific views
        name = (TextView) findViewById(R.id.name);
        username = (TextView) findViewById(R.id.username);
        followBtn = (Button) findViewById(R.id.follow_button);
        followingLabel = (TextView) findViewById(R.id.following_label);
        followThisAccountLabel = (TextView) findViewById(R.id.follow_this_account_label);
        allHabitsLabel = (TextView) findViewById(R.id.all_habits_label);
        habitList = (ListView) findViewById(R.id.current_user_habit_listview);


        // Set up the controller
        userController = new UserController();

        System.out.println("******Insert data into the screen");

        // Insert the name and the username
        userController.readUser(anotherUserID, retrievedAnotherUser -> {
                name.setText(retrievedAnotherUser.getFirstName() + " " + retrievedAnotherUser.getLastName());
                username.setText("@" + retrievedAnotherUser.getUsername());
        });

        // We first check if the logged in user is following this user on screen
        userController.readUser(currentUserId, retrievedCurrentUser -> {
            if (retrievedCurrentUser.getSocial().containsKey(anotherUserID)) {
                System.out.println("************ Executed");

                // Retrieve the value of another user in the logged in user's Social map
                int currentUserSocialMapValueOfAnotherUser = ((Number) retrievedCurrentUser.getSocial().get(anotherUserID)).intValue();

                if (currentUserSocialMapValueOfAnotherUser == 1 || currentUserSocialMapValueOfAnotherUser == 2) {
                    // Case: The logged in user is following this user
                    // Set to the "Following" scenario with a list of this user's public habits
                    followBtn.setVisibility(View.INVISIBLE);
                    followingLabel.setVisibility(View.VISIBLE);
                    followThisAccountLabel.setVisibility(View.INVISIBLE);
                    allHabitsLabel.setVisibility(View.VISIBLE);
                    habitList.setVisibility(View.VISIBLE);

                    // Set up the list and send an empty list to the view
                    ArrayList<HabitModel> habitDataList = new ArrayList<>();
                    ArrayAdapter<HabitModel> habitAdapter = new UserHabitCustomList(this, habitDataList);
                    habitList.setAdapter(habitAdapter);

                    // Display all the habits of another user
                    HabitModel.getAllForCurrentUser().addTaskCompleteListener(task -> {
                        habitDataList.clear();
                        if (task.isSuccessful()) {
                            habitDataList.addAll(task.getResult());
                        }
                        habitAdapter.notifyDataSetChanged();
                    });
                } else {
                    // The logged in user is not currently following the user on screen.
                    // Let's check if the logged in user has sent a follow request to the user on screen.
                    userController.readUser(anotherUserID, retrievedAnotherUser -> {
                        if (retrievedAnotherUser.getSocial().containsKey(currentUserId)) {

                            // Retrieve the value of the logged in user in another user's Social map
                            int anotherUserSocialMapValueOfCurrentUser = ((Number) retrievedAnotherUser.getSocial().get(currentUserId)).intValue();

                            if (anotherUserSocialMapValueOfCurrentUser == 0 || anotherUserSocialMapValueOfCurrentUser == 2) {
                                // Case: The logged in user has sent a follow request to this user
                                followBtn.setVisibility(View.VISIBLE);
                                followBtn.setText("Requested");
                                followBtn.setBackgroundTintList(getResources().getColorStateList(R.color.requested_button_background_tint));
                                followingLabel.setVisibility(View.INVISIBLE);
                                followThisAccountLabel.setVisibility(View.VISIBLE);
                                allHabitsLabel.setVisibility(View.INVISIBLE);
                                habitList.setVisibility(View.INVISIBLE);
                            } else if (anotherUserSocialMapValueOfCurrentUser == 1) {
                                // Another user is following the logged in user
                                followBtn.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        HashMap<String, Integer> incomingFollowRequest = new HashMap<String, Integer>();
                                        incomingFollowRequest.put(currentUserId, 2);
                                        userController.updateUser(anotherUserID, incomingFollowRequest);

                                        followBtn.setVisibility(View.VISIBLE);
                                        followBtn.setText("Requested");
                                        followBtn.setBackgroundTintList(getResources().getColorStateList(R.color.requested_button_background_tint));
                                        followingLabel.setVisibility(View.INVISIBLE);
                                        followThisAccountLabel.setVisibility(View.VISIBLE);
                                        allHabitsLabel.setVisibility(View.INVISIBLE);
                                        habitList.setVisibility(View.INVISIBLE);
                                    }
                                });
                            }
                        } else {
                            // The logged in user is not following the user on screen and has not sent the
                            // user on screen a follow request.
                            followBtn.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    HashMap<String, Integer> incomingFollowRequest = new HashMap<String, Integer>();
                                    incomingFollowRequest.put(currentUserId, 0);
                                    userController.updateUser(anotherUserID, incomingFollowRequest);

                                    followBtn.setVisibility(View.VISIBLE);
                                    followBtn.setText("Requested");
                                    followBtn.setBackgroundTintList(getResources().getColorStateList(R.color.requested_button_background_tint));
                                    followingLabel.setVisibility(View.INVISIBLE);
                                    followThisAccountLabel.setVisibility(View.VISIBLE);
                                    allHabitsLabel.setVisibility(View.INVISIBLE);
                                    habitList.setVisibility(View.INVISIBLE);
                                }
                            });

                        }
                    });
                }
            } else {
                // Since the logged in user is not following the user on screen, we check if the logged
                // in user has sent a follow request to the user on screen.
                userController.readUser(anotherUserID, retrievedAnotherUser -> {
                    if (retrievedAnotherUser.getSocial().containsKey(currentUserId)) {

                        // Retrieve the value of the logged in user in another user's Social map
                        int anotherUserSocialMapValueOfCurrentUser = ((Number) retrievedAnotherUser.getSocial().get(currentUserId)).intValue();

                        if (anotherUserSocialMapValueOfCurrentUser == 0 || anotherUserSocialMapValueOfCurrentUser == 2) {
                            // Case: The logged in user has sent a follow request to this user
                            followBtn.setVisibility(View.VISIBLE);
                            followBtn.setText("Requested");
                            followBtn.setBackgroundTintList(getResources().getColorStateList(R.color.requested_button_background_tint));
                            followingLabel.setVisibility(View.INVISIBLE);
                            followThisAccountLabel.setVisibility(View.VISIBLE);
                            allHabitsLabel.setVisibility(View.INVISIBLE);
                            habitList.setVisibility(View.INVISIBLE);
                        } else if (anotherUserSocialMapValueOfCurrentUser == 1) {
                            // Another user is following the logged in user

                        }
                    } else {
                        // The logged in user is not following the user on screen and has not sent the
                        // user on screen a follow request.
                        followBtn.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                HashMap<String, Integer> incomingFollowRequest = new HashMap<String, Integer>();
                                incomingFollowRequest.put(currentUserId, 0);
                                userController.updateUser(anotherUserID, incomingFollowRequest);

                                followBtn.setVisibility(View.VISIBLE);
                                followBtn.setText("Requested");
                                followBtn.setBackgroundTintList(getResources().getColorStateList(R.color.requested_button_background_tint));
                                followingLabel.setVisibility(View.INVISIBLE);
                                followThisAccountLabel.setVisibility(View.VISIBLE);
                                allHabitsLabel.setVisibility(View.INVISIBLE);
                                habitList.setVisibility(View.INVISIBLE);
                            }
                        });

                    }
                });
            }
        });
    }
}