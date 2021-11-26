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
import com.cmput301f21t09.budgetprojectname.views.lists.FollowRequestCustomList;
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

    // TODO: comment
    ArrayList<UserModel> followingDataList;
    ArrayAdapter<UserModel> followingAdapter;

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

        // set up controller
        userController = new UserController();

        String currentUserId = AuthorizationService.getInstance().getCurrentUserId();

        Button requests_btn = (Button) view.findViewById(R.id.requests_button);
        requests_btn.setOnClickListener(v -> seeRequests(currentUserId));

        // ListView setup
        ListView followingList = view.findViewById(R.id.following_list);

        // Set up the list and send an empty list to the view
        followingDataList = new ArrayList<>();
        followingAdapter = new FollowRequestCustomList(getContext(),
                followingDataList, false);
        followingList.setAdapter(followingAdapter);

        // load all users we are following
        userController.readUserFollowRequests(currentUserId, false ,
                followRequests -> {
            for(String userID: followRequests){
                // get back the model using userID
                userController.readUser(userID, followingUser -> {
                    System.out.println("user you are following " +
                            followingUser.getFirstName() + " " + followingUser.getUID());
                    followingDataList.add(followingUser);
                    // update adapter that new users have been added to list
                    followingAdapter.notifyDataSetChanged();
                    // update number of users following
                    // must be done in callback function so it is dynamic
                    // ie. changes when data is fetched
                    requests_btn.setText("You are following " +
                            followingDataList.size() + " users");
                });
            }
        });

        // when a user is selected go to their profile page
    }
}
