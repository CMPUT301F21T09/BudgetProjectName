package com.cmput301f21t09.budgetprojectname.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cmput301f21t09.budgetprojectname.R;
import com.cmput301f21t09.budgetprojectname.controllers.UserController;
import com.cmput301f21t09.budgetprojectname.views.lists.UserHabitCustomList;
import com.cmput301f21t09.budgetprojectname.views.activities.UserLoginActivity;
import com.cmput301f21t09.budgetprojectname.models.HabitModel;
import com.cmput301f21t09.budgetprojectname.services.AuthorizationService;

import java.util.ArrayList;

/**
 * Fragment that shows user's profile with their name, username, and habits
 */
public class CurrentUserProfileFragment extends Fragment {

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
     * Sign out.
     * Tells authorization service to sign out and
     * takes user to login screen.
     */
    private void signOut() {
        AuthorizationService.getInstance().signOut();
        Toast.makeText(getActivity(), "Signed out", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(), UserLoginActivity.class));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_current_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve the logged in user's userid
        String currentUserId = AuthorizationService.getInstance().getCurrentUserId();

        // Retrieve the specific views
        name = view.findViewById(R.id.name);
        username = view.findViewById(R.id.username);

        // Set up the controller
        userController = new UserController();

        // Insert the logged in user's details
        userController.readUser(currentUserId, retrievedAnotherUser -> {
            name.setText(retrievedAnotherUser.getFirstName() + " " + retrievedAnotherUser.getLastName());
            username.setText("@" + retrievedAnotherUser.getUsername());
        });

        // ListView setup
        ListView habitList = view.findViewById(R.id.current_user_habit_listview);

        // Set up the list and send an empty list to the view
        ArrayList<HabitModel> habitDataList = new ArrayList<>();
        ArrayAdapter<HabitModel> habitAdapter = new UserHabitCustomList(getContext(), habitDataList);
        habitList.setAdapter(habitAdapter);

        HabitModel.getAllForCurrentUser().addTaskCompleteListener(task -> {
            habitDataList.clear();
            if (task.isSuccessful()) {
                habitDataList.addAll(task.getResult());
            }
            habitAdapter.notifyDataSetChanged();
        });

        // Sign out button pressed: sign out
        ImageButton signOut = view.findViewById(R.id.logout_button);
        signOut.setOnClickListener(v -> signOut());
    }
}
