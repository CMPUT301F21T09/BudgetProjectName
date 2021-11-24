package com.cmput301f21t09.budgetprojectname.views.activities;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cmput301f21t09.budgetprojectname.R;
import com.cmput301f21t09.budgetprojectname.controllers.HabitEventController;
import com.cmput301f21t09.budgetprojectname.models.HabitEventModel;
import com.cmput301f21t09.budgetprojectname.models.HabitModel;
import com.cmput301f21t09.budgetprojectname.models.UserModel;
import com.cmput301f21t09.budgetprojectname.views.lists.FollowRequestCustomList;
import com.cmput301f21t09.budgetprojectname.views.lists.HabitEventCustomList;
import com.cmput301f21t09.budgetprojectname.views.lists.UserHabitCustomList;

import java.util.ArrayList;

/**
 * Class which handles accepting or declining an incoming follow request from another user
 */
public class FollowRequestActivity extends AppCompatActivity {

    ArrayList<UserModel> followRequestDataList;
    ArrayAdapter<UserModel> followRequestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_request);

        // ListView setup
        ListView followRequestList = findViewById(R.id.follow_request_listview);

        // Set up the list and send an empty list to the view
        followRequestDataList = new ArrayList<>();

        // add a fake user to arrayList
        UserModel fakeUser = new UserModel();
        fakeUser.setFirstName("Fake");
        fakeUser.setLastName("McFakerson");
        fakeUser.setUsername("fk123");
        followRequestDataList.add(fakeUser);

        followRequestAdapter = new FollowRequestCustomList(this, followRequestDataList);
        followRequestList.setAdapter(followRequestAdapter);

      }
}