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
import com.cmput301f21t09.budgetprojectname.views.activities.DefineHabitEventActivity;
import com.cmput301f21t09.budgetprojectname.views.activities.FollowRequestActivity;
import com.cmput301f21t09.budgetprojectname.views.activities.ViewHabitActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Helper class to create a custom list for user follow request
 */
public class FollowRequestCustomList extends ArrayAdapter<UserModel> {
    private final ArrayList<UserModel> users;
    private final Context context;
    private final boolean isFollowRequest;

    /**
     * Constructor for FollowRequestCustomList
     *
     * @param context a current context of application
     * @param users   a arrayList of users who wish to follow a given user
     */
    public FollowRequestCustomList(Context context, ArrayList<UserModel> users,
                                   boolean isFollowRequest){
        super(context, 0, users);
        this.users = users;
        this.context = context;
        this.isFollowRequest = isFollowRequest;
    }

    /**
     * Gets a view for incoming user follow requests which displays name and username of each
     * user
     * @param position specific user element in adapter's list
     * @param convertView view type being used
     * @param parent the parent that this view will eventually be attached to
     * @return view for user follow request
     */
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.follow_request_custom_list,
                    parent, false);
        }
        // if isFollowRequest is false, then set the check and decline buttons to invisible
        ImageButton acceptBtn = view.findViewById(R.id.accept_button);
        Button decline_btn =  view.findViewById(R.id.decline_button);
        accept_btn.setVisibility(view.INVISIBLE);
        decline_btn.setVisibility(view.INVISIBLE);

        // Get the specific user being interacted with
        UserModel user = users.get(position);

        // Get the views
        TextView followRequestName = view.findViewById(R.id.follow_request_name);
        TextView followRequestUsername = view.findViewById(R.id.follow_request_username);

        // Set the views accordingly
        followRequestName.setText(user.getFirstName());
        followRequestUsername.setText("@"+ user.getUsername());

        // Brings the user to the create habit event screen
        acceptBtn.setOnClickListener(v -> {
            System.out.println("user that got accepted " + user.getUID() + "name " + user.getUsername());
            acceptFollowRequest(user);
        });

        return view;
    }

    /**
     * Accepts follow request of another user and updates the database accordingly.
     * Refreshes the activity to ensure updated db data is used.
     * @param anotherUser user whose request we want to accept
     */
    private void acceptFollowRequest(UserModel anotherUser){
        String currentUserID = AuthorizationService.getInstance().getCurrentUserId();
        UserController userController = new UserController();

        // modify current user's status with respect to the other user
        HashMap<String, Integer> updatedOtherSocialMap = anotherUser.getSocial();
        String anotherUserID = anotherUser.getUID();
        updatedOtherSocialMap.put(currentUserID, 1); // current user is followed by anotherUser
        userController.updateUserSocialMap(anotherUserID, updatedOtherSocialMap);

        // modify other user's status with respect to current user
        userController.readUser(currentUserID, currentUser -> {
            HashMap<String, Integer> updatedCurrentSocialMap = currentUser.getSocial();
            int anotherUserInCurrentSocialMap =
                    Integer.parseInt(String.valueOf(updatedCurrentSocialMap.get(anotherUserID)));

            if(anotherUserInCurrentSocialMap == 0){
                // 0 means they have sent us a request which we have accepted now we must
                // remove the request from current user's social map
                updatedCurrentSocialMap.remove(anotherUserID);
            } else if(anotherUserInCurrentSocialMap == 2){
                // 2 means we are already following this friend and they've sent a request to us
                // we want to change value to 1 to show that we are still following them
                updatedCurrentSocialMap.put(anotherUserID, 1);
            }
            // update db with new relationships
            userController.updateUserSocialMap(currentUserID, updatedCurrentSocialMap);

            // refresh list on page to account for newly removed entries
            // needs to be done in callback to ensure db has been updated
            Intent intent = new Intent(context, FollowRequestActivity.class);
            intent.putExtra("uid", currentUserID);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });


    }
}
