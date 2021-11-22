package com.cmput301f21t09.budgetprojectname.views.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cmput301f21t09.budgetprojectname.R;
import com.cmput301f21t09.budgetprojectname.models.HabitModel;
import com.cmput301f21t09.budgetprojectname.views.lists.UserHabitCustomList;

import java.util.ArrayList;

public class FollowerRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_request);

        // TODO: replace with users following instead of habit
        // ListView setup
        ListView followersList = findViewById(R.id.follow_request_listview);

//        // Set up the list and send an empty list to the view
//        ArrayList<HabitModel> habitDataList = new ArrayList<>();
//        ArrayAdapter<HabitModel> habitAdapter = new UserHabitCustomList(this, habitDataList);
//        followersList.setAdapter(habitAdapter);
//
//        HabitModel.getAllForCurrentUser().addTaskCompleteListener(task -> {
//            habitDataList.clear();
//            if (task.isSuccessful()) {
//                habitDataList.addAll(task.getResult());
//            }
//            habitAdapter.notifyDataSetChanged();
//        });
//
//        followersList.setOnItemClickListener((parent, view1, position, id) -> {
//            // TODO: Pass targeted Habit to ViewHabitActivity
//        });
      }
}