package com.cmput301f21t09.budgetprojectname;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Date;

/**
 * Fragment that shows user's profile with their name, username, and habits
 */
public class CurrentUserProfileFragment extends Fragment {
    /**
     * Sign out.
     * Tells authorization service to sign out and
     * takes user to login screen.
     */
    private void signOut() {
        AuthorizationService.getInstance().signOut();
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

        // ListView setup
        ListView habitList = view.findViewById(R.id.current_user_habit_listview);

        ArrayList<HabitModel> habitDataList = new ArrayList<>();

        // This is mock data
        for (int i = 0; i < 10; i++) {
            habitDataList.add(new HabitModel("TestID", "TestString" + 1, "TestReason", new Date(), new Date(), i));
        }

        ArrayAdapter<HabitModel> habitAdapter = new UserHabitCustomList(getContext(), habitDataList);
        habitList.setAdapter(habitAdapter);

        habitList.setOnItemClickListener((parent, view1, position, id) -> {
            // TODO: Pass targeted Habit to ViewHabitActivity
        });

        // Sign out button pressed: sign out
        ImageButton signOut = view.findViewById(R.id.logout_button);
        signOut.setOnClickListener(v -> signOut());
    }
}
