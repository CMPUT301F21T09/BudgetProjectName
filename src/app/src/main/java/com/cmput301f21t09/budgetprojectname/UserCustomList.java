package com.cmput301f21t09.budgetprojectname;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

/**
 * CustomList for CurrentUserProfileFragment and OtherUserProfileActivity
 * Show the habit's name, reason, and streak
 */
public class UserCustomList extends ArrayAdapter<User> {
    private ArrayList<User> users;
    private Context context;


    /**
     * Constructor for UserHabitCustomList
     *
     * @param context a current context of application
     * @param users   a arrayList of users
     */
    public UserCustomList(Context context, ArrayList<User> users) {
        super(context, 0, users);
        this.users = users;
        this.context = context;
    }

    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.user_custom_list, parent, false);
        }
        User user = users.get(position);

        ImageView profilePic = view.findViewById(R.id.profile_pic);
        TextView name = view.findViewById(R.id.name);
        TextView userName = view.findViewById(R.id.username);

        return view;
    }
}
