package com.cmput301f21t09.budgetprojectname;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cmput301f21t09.budgetprojectname.models.UserModel;

import java.util.ArrayList;

/**
 * CustomList for CurrentUserProfileFragment and OtherUserProfileActivity
 * Show the user's name, username and profile photo
 */
public class UserCustomList extends ArrayAdapter<UserModel> {
    private ArrayList<UserModel> users;
    private Context context;


    /**
     * Constructor for UserHabitCustomList
     *
     * @param context a current context of application
     * @param users   a arrayList of users
     */
    public UserCustomList(Context context, ArrayList<UserModel> users) {
        super(context, 0, users);
        this.users = users;
        this.context = context;
    }

    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.user_custom_list, parent, false);
        }
        UserModel user = users.get(position);

        ImageView profilePic = view.findViewById(R.id.profile_pic);
        TextView name = view.findViewById(R.id.name);
        TextView userName = view.findViewById(R.id.username);

        Button follow = view.findViewById(R.id.following);
        // if user following this user
        // set follow button to gone or unfollow
        follow.setOnClickListener(v -> {
            // TODO: Request follow selected user
        });

        return view;
    }
}
