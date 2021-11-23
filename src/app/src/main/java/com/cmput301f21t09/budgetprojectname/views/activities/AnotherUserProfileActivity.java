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
     * Text view to show that the logged-in user has already followed this user
     */
    private TextView followingLabel;

    /**
     * Text view to show that the logged-in user needs to follow this user to see their habits
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

        // Retrieve the logged-in user's userid
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

        // Insert the name and the username
        userController.readUser(anotherUserID, retrievedAnotherUser -> {
            name.setText(retrievedAnotherUser.getFirstName() + " " + retrievedAnotherUser.getLastName());
            username.setText("@" + retrievedAnotherUser.getUsername());
        });

        // Set the display according to the relationship between the logged-in user and the user on screen
        userController.readUser(currentUserId, retrievedCurrentUser -> {
            // Check if the logged-in user is following the user on screen
            if (retrievedCurrentUser.getSocial().containsKey(anotherUserID)) {
                // Retrieve the value of user on screen in the logged-in user's Social map
                int valueOfAnotherUserInCurrentUserSocialMap = ((Number) retrievedCurrentUser.getSocial().get(anotherUserID)).intValue();

                if (valueOfAnotherUserInCurrentUserSocialMap == 1 || valueOfAnotherUserInCurrentUserSocialMap == 2) {
                    // Case: The logged-in user is following the user on screen

                    // Set to the "Following" scenario with a list of this user's public habits
                    changeDisplayToFollowingState();
                } else {
                    // Case: The logged-in user is not currently following the user on screen
                    // Check if the logged-in user has sent a follow request to the user on screen
                    userController.readUser(anotherUserID, retrievedAnotherUser -> {
                        if (retrievedAnotherUser.getSocial().containsKey(currentUserId)) {
                            // Retrieve the value of the logged-in user in user on screen's Social map
                            int valueOfCurrentUserInAnotherUserSocialMap = ((Number) retrievedAnotherUser.getSocial().get(currentUserId)).intValue();

                            if (valueOfCurrentUserInAnotherUserSocialMap == 0 || valueOfCurrentUserInAnotherUserSocialMap == 2) {
                                // The logged-in user has sent a follow request to this user
                                changeDisplayToRequestedState();
                            } else if (valueOfCurrentUserInAnotherUserSocialMap == 1) {
                                // User on screen is following the logged-in user but the logged-in
                                // user hasn't sent the user on screen a follow request

                                followBtn.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        requestToFollow(currentUserId, anotherUserID, 2);
                                    }
                                });
                            }
                        } else {
                            // Case: The logged-in user is not following the user on screen and has not sent the
                            // user on screen a follow request. The user on screen is not following the logged-in
                            // user as well.
                            followBtn.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    requestToFollow(currentUserId, anotherUserID, 0);
                                }
                            });
                        }
                    });
                }
            } else {
                // Case: The logged-in user is not currently following the user on screen and the
                // user on screen hasn't sent a follow request to the logged-in user
                // Check if the logged-in user has sent a follow request to the user on screen.
                userController.readUser(anotherUserID, retrievedAnotherUser -> {
                    if (retrievedAnotherUser.getSocial().containsKey(currentUserId)) {
                        // Retrieve the value of the logged-in user in another user's Social map
                        int valueOfCurrentUserInAnotherUserSocialMap = ((Number) retrievedAnotherUser.getSocial().get(currentUserId)).intValue();

                        if (valueOfCurrentUserInAnotherUserSocialMap == 0 || valueOfCurrentUserInAnotherUserSocialMap == 2) {
                            // Case: The logged-in user has sent a follow request to this user
                            changeDisplayToRequestedState();
                        }
                    } else {
                        // Case: The logged-in user is not following the user on screen and has not sent the
                        // user on screen a follow request.
                        followBtn.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                requestToFollow(currentUserId, anotherUserID, 0);
                            }
                        });
                    }
                });
            }
        });
    }

    /**
     * Change the display to the Following state
     */
    private void changeDisplayToFollowingState() {
        // Set the views accordingly
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
    }

    /**
     * Submit a request to follow another user
     *
     * @param currentUserId ID of the current user
     * @param anotherUserID ID of the user that that the current user wants to follow
     * @param newValue      new value to be used
     */
    private void requestToFollow(String currentUserId, String anotherUserID, Integer newValue) {
        // Update the value of the currentUserId in anotherUserID's document
        HashMap<String, Integer> incomingFollowRequest = new HashMap<String, Integer>();
        incomingFollowRequest.put(currentUserId, newValue);
        userController.updateUserSocialMap(anotherUserID, incomingFollowRequest);

        // Change the display to Requested State
        changeDisplayToRequestedState();
    }

    /**
     * Change the display to Requested to Follow state
     */
    private void changeDisplayToRequestedState() {
        // Set the views to Requested to Follow state
        followBtn.setVisibility(View.VISIBLE);
        followBtn.setText("Requested");
        followBtn.setBackgroundTintList(getResources().getColorStateList(R.color.requested_button_background_tint));
        followingLabel.setVisibility(View.INVISIBLE);
        followThisAccountLabel.setVisibility(View.VISIBLE);
        allHabitsLabel.setVisibility(View.INVISIBLE);
        habitList.setVisibility(View.INVISIBLE);
    }
}