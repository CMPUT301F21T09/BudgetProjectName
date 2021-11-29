package com.cmput301f21t09.budgetprojectname.views.lists;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cmput301f21t09.budgetprojectname.R;
import com.cmput301f21t09.budgetprojectname.controllers.UserController;
import com.cmput301f21t09.budgetprojectname.models.UserModel;
import com.cmput301f21t09.budgetprojectname.services.AuthorizationService;
import com.cmput301f21t09.budgetprojectname.views.activities.AnotherUserProfileActivity;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Helper class to create a custom list for user follow request
 */
public class FollowCustomList extends ArrayAdapter<UserModel> {
    /**
     * List of users who have requested to follow current user
     */
    private ArrayList<UserModel> users;

    /**
     * Information about app environment
     */
    private final Context context;

    /**
     * Checks if the activity using it is a follow request, the accept/decline buttons
     * should be visible
     */
    private final boolean isFollowRequest;

    /**
     * User ID of user currently signed in
     */
    private final String currentUserID = AuthorizationService.getInstance().getCurrentUserId();

    /**
     * Constructor for FollowCustomList
     *
     * @param context a current context of application
     * @param users   a arrayList of users who wish to follow a given user
     */
    public FollowCustomList(Context context, ArrayList<UserModel> users,
                            boolean isFollowRequest) {
        super(context, 0, users);
        this.users = users;
        this.context = context;
        this.isFollowRequest = isFollowRequest;
    }

    /**
     * Gets a view for incoming user follow requests which displays name and username of each
     * user
     *
     * @param position    specific user element in adapter's list
     * @param convertView view type being used
     * @param parent      the parent that this view will eventually be attached to
     * @return view for user follow request
     */
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.follow_custom_list,
                    parent, false);
        }

        ImageButton acceptBtn = view.findViewById(R.id.accept_button);
        ImageButton denyBtn = view.findViewById(R.id.decline_button);

        // if using this for the following list then there is no option to accept or decline
        // just show their name and username
        if (!isFollowRequest) {
            acceptBtn.setVisibility(View.INVISIBLE);
            denyBtn.setVisibility(View.INVISIBLE);
        }

        // Get the specific user being interacted with
        UserModel user = users.get(position);

        // Get the views
        TextView followRequestName = view.findViewById(R.id.follow_request_name);
        TextView followRequestUsername = view.findViewById(R.id.follow_request_username);

        // Set the views accordingly
        followRequestName.setText(user.getFirstName());
        followRequestUsername.setText("@" + user.getUsername());

        // Brings the user to the following another user's details screen
        ShapeableImageView habitBackground = view.findViewById(R.id.follow_list_background);
        habitBackground.setOnClickListener(v -> {
            // pass habit id to view the habit details for targeted habit
            Intent intent = new Intent(context, AnotherUserProfileActivity.class);
            intent.putExtra("USER_ID", user.getUID());
            context.startActivity(intent);
        });

        // User clicks accept request button
        acceptBtn.setOnClickListener(v -> {
            System.out.println("user that got accepted " + user.getUID() + "name " + user.getUsername());
            acceptFollowRequest(user);
        });

        // User clicks deny request button
        denyBtn.setOnClickListener(v -> {
            System.out.println("user that got denied " + user.getUID() + "name " + user.getUsername());
            denyFollowRequest(user);
        });

        return view;
    }

    /**
     * Accepts follow request of another user and updates the database accordingly.
     * Refreshes the activity to ensure updated db data is used.
     *
     * @param anotherUser user whose request we want to accept
     */
    private void acceptFollowRequest(UserModel anotherUser) {

        UserController userController = new UserController();

        // modify current user's status inside other user's social map
        HashMap<String, Integer> updatedOtherSocialMap = anotherUser.getSocial();
        String anotherUserID = anotherUser.getUID();

        // set a placeholder value that is not one of the expected values 0,1,2
        // TODO: if time, refactor to use enums to avoid errors
        int currentUserInOtherSocialMap = -1;
        if (updatedOtherSocialMap.get(currentUserID) != null) {
            currentUserInOtherSocialMap =
                    Integer.parseInt(String.valueOf(updatedOtherSocialMap.get(currentUserID)));
        }

        // Two cases need to be considered when a request is accepted
        // (from perspective of other user)
        if (currentUserInOtherSocialMap == 0) {
            // change value from 0 --> 2 to show that there is an incoming follow
            // request from current user and current user is followed by anotherUser
            updatedOtherSocialMap.put(currentUserID, 2);
        } else {
            updatedOtherSocialMap.put(currentUserID, 1); // current user is followed by anotherUser
        }

        // update db with new relationships
        userController.updateUserSocialMap(anotherUserID, updatedOtherSocialMap, new UserController.UserUpdateCallback() {
            @Override
            public void onSuccess() {
                // modify other user's status inside current user's social map
                userController.readUser(currentUserID, currentUser -> {
                    HashMap<String, Integer> updatedCurrentSocialMap = currentUser.getSocial();
                    int anotherUserInCurrentSocialMap =
                            updatedCurrentSocialMap.get(anotherUserID);

                    if (anotherUserInCurrentSocialMap == 0) {
                        // 0 means they have sent us a request which we have accepted now we must
                        // remove the request from current user's social map
                        updatedCurrentSocialMap.remove(anotherUserID);
                    } else if (anotherUserInCurrentSocialMap == 2) {
                        // 2 means we are already following this friend and they've sent a request to us
                        // we want to change value to 1 to show that we are still following them
                        updatedCurrentSocialMap.put(anotherUserID, 1);
                    }
                    // update db with new relationships
                    userController.updateUserSocialMap(currentUserID, updatedCurrentSocialMap, new UserController.UserUpdateCallback() {
                        @Override
                        public void onSuccess() {
                            users.remove(anotherUser);
                            ArrayList<UserModel> updatedUsers = new ArrayList<>();
                            updateData(updatedUsers);
                        }

                        @Override
                        public void onFailure() {

                        }
                    });
                });
            }

            @Override
            public void onFailure() {


            }
        });
    }

    /**
     * Denies follow request of another user and updates the database accordingly.
     * Refreshes the activity to ensure updated db data is used.
     *
     * @param anotherUser user whose request we want to deny
     */
    private void denyFollowRequest(UserModel anotherUser) {
        String anotherUserID = anotherUser.getUID();

        UserController userController = new UserController();

        // current user's status inside other user's social map is unchanged

        // alternatively we could move this code block to another function because it is similar
        // to the accept request case, but I have specific comments for each case
        // modify other user's status inside current user's social map
        userController.readUser(currentUserID, currentUser -> {
            System.out.println("**username " + currentUser.getUsername());
            HashMap<String, Integer> updatedCurrentSocialMap = currentUser.getSocial();
            int anotherUserInCurrentSocialMap =
                    Integer.parseInt(String.valueOf(updatedCurrentSocialMap.get(anotherUserID)));

            if (anotherUserInCurrentSocialMap == 0) {
                // 0 means they have sent us a request which we have rejected now we must
                // remove the request from current user's social map
                updatedCurrentSocialMap.remove(anotherUserID);
            } else if (anotherUserInCurrentSocialMap == 2) {
                // 2 means we are already following this friend and they've sent a request to us
                // we want to change value to 1 to show that we are still following them
                updatedCurrentSocialMap.put(anotherUserID, 1);
            }
            // update db with new relationships
            userController.updateUserSocialMap(currentUserID, updatedCurrentSocialMap, new UserController.UserUpdateCallback() {
                @Override
                public void onSuccess() {
                    users.remove(anotherUser);
                    ArrayList<UserModel> updatedUsers = new ArrayList<>();
                    updateData(updatedUsers);
                }

                @Override
                public void onFailure() {

                }
            });
        });
    }

    /**
     * Replace ArrayList(UserModel Data) with updated data
     *
     * @param updatedData Updated ArrayList of UserModel
     */
    private void updateData(ArrayList<UserModel> updatedData) {
        users = updatedData;
        this.notifyDataSetChanged();
    }

}
