package com.cmput301f21t09.budgetprojectname.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cmput301f21t09.budgetprojectname.R;
import com.cmput301f21t09.budgetprojectname.controllers.UserController;
import com.cmput301f21t09.budgetprojectname.models.HabitModel;
import com.cmput301f21t09.budgetprojectname.models.UserModel;
import com.cmput301f21t09.budgetprojectname.services.AuthorizationService;
import com.cmput301f21t09.budgetprojectname.views.activities.FollowRequestActivity;
import com.cmput301f21t09.budgetprojectname.views.lists.UserHabitCustomList;

import java.util.ArrayList;

/**
 * Fragment that shows the user's following user list and makes accepting/rejecting the
 * follower request from other user
 */
public class FollowingFragment extends Fragment {

    /**
     * Controller for fetching user details
     */
    private UserController userController;

    /**
     * TODO: REMOVE
     * List of users requesting to follow current user
     * Used in this activity to set the text in the request button
     */
    private ArrayList<UserModel> followRequestUsers = new ArrayList<>();

    /**
     * Check on the follow requests sent to you
     * by transitioning to the follow request screen
     */
    private void seeRequests(String userID) {
        // TODO: send userid in intent
        Intent intent = new Intent(getActivity(), FollowRequestActivity.class);
        intent.putExtra("uid", userID);
        startActivity(intent);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_following, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button requests_btn = (Button) view.findViewById(R.id.requests_button);
        // set the number of follow requests
        // set up controller
        userController = new UserController();
        String currentUserId = AuthorizationService.getInstance().getCurrentUserId();
        System.out.println("curr user id " + currentUserId);
        // TODO: store list of follow requests

        // System.out.println("size of follow req" + followRequestUsers.size());
        requests_btn.setOnClickListener(v -> seeRequests(currentUserId));

        // TODO: replace with users following instead of habit
        // ListView setup
        ListView habitList = view.findViewById(R.id.following_list);

        // Set up the list and send an empty list to the view
        ArrayList<HabitModel> habitDataList = new ArrayList<>();
        ArrayAdapter<HabitModel> habitAdapter = new UserHabitCustomList(getContext(),
                habitDataList);
        habitList.setAdapter(habitAdapter);

        HabitModel.getAllForCurrentUser().addTaskCompleteListener(task -> {
            habitDataList.clear();
            if (task.isSuccessful()) {
                habitDataList.addAll(task.getResult());
            }
            habitAdapter.notifyDataSetChanged();
        });

        habitList.setOnItemClickListener((parent, view1, position, id) -> {
            // TODO: Pass targeted Habit to ViewHabitActivity
        });

        // TODO: add back button
    }
}
