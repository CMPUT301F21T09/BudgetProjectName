package com.cmput301f21t09.budgetprojectname.views.lists;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.cmput301f21t09.budgetprojectname.R;
import com.cmput301f21t09.budgetprojectname.models.UserModel;
import com.cmput301f21t09.budgetprojectname.views.activities.AnotherUserProfileActivity;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

/**
 * CustomList for CurrentUserProfileFragment and OtherUserProfileActivity
 * Show the user's name, username and profile photo
 */
public class UserCustomList extends ArrayAdapter<UserModel> {
    /**
     * List of users
     */
    private final ArrayList<UserModel> users;

    /**
     * Information about app environment
     */
    private final Context context;

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

    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.user_custom_list, parent, false);
        }
        UserModel user = users.get(position);

        ImageView profilePic = view.findViewById(R.id.profile_pic);
        TextView name = view.findViewById(R.id.name);
        TextView userName = view.findViewById(R.id.username);

        name.setText(user.getFirstName() + " " + user.getLastName());
        userName.setText(user.getUsername());

        // Brings the user to the profile screen
        ShapeableImageView habitBackground = view.findViewById(R.id.user_lists_background);
        habitBackground.setOnClickListener(v -> {
            // Pass the UserID through intent
            Intent intent = new Intent(context, AnotherUserProfileActivity.class);
            intent.putExtra("USER_ID", user.getUID());
            context.startActivity(intent);
        });

        return view;
    }
}