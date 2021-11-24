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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Helper class to create a custom list for user follow request
 */
public class FollowRequestCustomList extends ArrayAdapter<UserModel> {
    private final ArrayList<UserModel> users;
    private final Context context;

    /**
     * Constructor for FollowRequestCustomList
     *
     * @param context a current context of application
     * @param users   a arrayList of users who wish to follow a given user
     */
    public FollowRequestCustomList(Context context, ArrayList<UserModel> users) {
        super(context, 0, users);
        this.users = users;
        this.context = context;
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
        // Get the specific user being interacted with
        UserModel user = users.get(position);

        // Get the views
        TextView followRequestName = view.findViewById(R.id.follow_request_name);
        TextView followRequestUsername = view.findViewById(R.id.follow_request_username);

        // Set the views accordingly
        followRequestName.setText(user.getFirstName());
        followRequestUsername.setText("@"+ user.getUsername());

        // Brings the user to the create habit event screen
        ImageButton acceptBtn = view.findViewById(R.id.accept_button);
        acceptBtn.setOnClickListener(v -> {
            // pass habit id to create habit event for targeted habit
            // Intent intent = new Intent(context, DefineHabitEventActivity.class);
            // intent.putExtra("HABIT_ID", habit.getId());
            // context.startActivity(intent);
            System.out.println("user that got accepted " + user.getUID() + "name " + user.getUsername());
            acceptFollowRequest(user);
        });

        return view;
    }

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
            // remove the request from current user's social map
            updatedCurrentSocialMap.remove(anotherUserID);
            userController.updateUserSocialMap(currentUserID, updatedCurrentSocialMap);
        });
    }
}
