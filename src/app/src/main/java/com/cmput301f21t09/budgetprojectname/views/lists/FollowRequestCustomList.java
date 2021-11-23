package com.cmput301f21t09.budgetprojectname.views.lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cmput301f21t09.budgetprojectname.R;
import com.cmput301f21t09.budgetprojectname.models.UserModel;

import java.util.ArrayList;

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

        return view;
    }
}
