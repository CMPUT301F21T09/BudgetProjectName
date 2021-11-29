package com.cmput301f21t09.budgetprojectname.views.activities;

import static java.util.Objects.isNull;

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

    /**
     * Class which handles viewing and following another user
     */
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
        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        followBtn = findViewById(R.id.follow_button);
        followingLabel = findViewById(R.id.following_label);
        followThisAccountLabel = findViewById(R.id.follow_this_account_label);
        allHabitsLabel = findViewById(R.id.all_habits_label);
        habitList = findViewById(R.id.current_user_habit_listview);


        // Set up the controller
        userController = new UserController();

        // Insert the name and the username
        userController.readUser(anotherUserID, retrievedAnotherUser -> {
            name.setText(retrievedAnotherUser.getFirstName() + " " + retrievedAnotherUser.getLastName());
            username.setText("@" + retrievedAnotherUser.getUsername());
        });

        // Display (Not following and hasn't requested, Requested, or Following)
        // scenarios based on Social Map values of current user and user on screen
        userController.readUser(currentUserId, retrievedCurrentUser -> {
            userController.readUser(anotherUserID, retrievedAnotherUser -> {
                // Get the Social Map values accordingly and set it to null if it doesn't exist
                Integer valueOfAnotherUserInCurrentUserSocialMap = retrievedCurrentUser.getSocial()
                        .get(anotherUserID) == null ? null : ((Number) retrievedCurrentUser.getSocial()
                        .get(anotherUserID)).intValue();

                Integer valueOfCurrentUserInAnotherUserSocialMap = retrievedAnotherUser.getSocial()
                        .get(currentUserId) == null ? null : ((Number) retrievedAnotherUser.getSocial()
                        .get(currentUserId)).intValue();

                // The conditional statements below would render the correct screen accordingly based on
                // the fact that (0==incoming_request, 1==following, 2==incoming_request and following)
                if ((isNull(valueOfAnotherUserInCurrentUserSocialMap)
                        || valueOfAnotherUserInCurrentUserSocialMap == 0)
                        && isNull(valueOfCurrentUserInAnotherUserSocialMap)) {
                    // Not Following and hasn't requested Screen is shown and the Follow button is presented
                    // (Additionally, user on screen is not following the logged-in user)
                    // If the user clicks on the Follow button,
                    // increase the user on screen's Social Map's value of current user from None to 0
                    requestToFollow(currentUserId, anotherUserID, retrievedAnotherUser.getSocial(), 0);
                } else if ((isNull(valueOfAnotherUserInCurrentUserSocialMap)
                        || valueOfAnotherUserInCurrentUserSocialMap == 0)
                        && valueOfCurrentUserInAnotherUserSocialMap == 1) {
                    // Not Following and hasn't requested Screen is shown and the Follow button is presented
                    // (Additionally, user on screen is following the logged-in user)

                    // If the user clicks on the Follow button,
                    // increase the user on screen's Social Map's value of current user from 1 to 2
                    requestToFollow(currentUserId, anotherUserID, retrievedAnotherUser.getSocial(), 2);
                } else if ((isNull(valueOfAnotherUserInCurrentUserSocialMap)
                        || valueOfAnotherUserInCurrentUserSocialMap == 0)
                        && (valueOfCurrentUserInAnotherUserSocialMap == 0
                        || valueOfCurrentUserInAnotherUserSocialMap == 2)) {
                    // Requested Screen is shown
                    changeDisplayToRequestedState();
                } else if ((valueOfAnotherUserInCurrentUserSocialMap == 1
                        || valueOfAnotherUserInCurrentUserSocialMap == 2)
                        && (isNull(valueOfCurrentUserInAnotherUserSocialMap)
                        || valueOfCurrentUserInAnotherUserSocialMap == 0
                        || valueOfCurrentUserInAnotherUserSocialMap == 1
                        || valueOfCurrentUserInAnotherUserSocialMap == 2)) {
                    // Following screen is shown
                    changeDisplayToFollowingState(anotherUserID);
                }
            });
        });
    }

    /**
     * Change the display to the Following state
     */
    private void changeDisplayToFollowingState(String anotherUserID) {
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
        HabitModel.getAllForAnotherUser(anotherUserID).addTaskCompleteListener(task -> {
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
    private void requestToFollow(String currentUserId, String anotherUserID,
                                 HashMap<String, Integer> socialMap, Integer newValue) {
        followBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Update the value of the currentUserId in anotherUserID's document
                socialMap.put(currentUserId, newValue);
                userController.updateUserSocialMap(anotherUserID, socialMap, new UserController.UserUpdateCallback() {
                    @Override
                    public void onSuccess() {
                        // Change the display to Requested State
                        changeDisplayToRequestedState();
                    }

                    @Override
                    public void onFailure() {

                    }
                });
            }
        });
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