package com.cmput301f21t09.budgetprojectname;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 4 main fragments for bottom navigation UI
        DailyHabitFragment dailyHabitFragment = new DailyHabitFragment();
        SearchFragment searchFragment = new SearchFragment();
        FollowingFragment followingFragment = new FollowingFragment();
        CurrentUserProfileFragment currentUserProfileFragment = new CurrentUserProfileFragment();

        // Fragment switching
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.getMenu().findItem(R.id.add).setCheckable(false);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.daily_habit:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, dailyHabitFragment).commit();
                    return true;
                case R.id.search:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, searchFragment).commit();
                    return true;
                case R.id.add:
                    Intent intent = new Intent(this, DefineHabitActivity.class);
                    startActivity(intent);
                    // break instead of return to keep previous fragment selection
                    break;
                case R.id.following:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, followingFragment).commit();
                    return true;
                case R.id.profile:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, currentUserProfileFragment).commit();
                    return true;
            }
            return false;
        });

        bottomNavigationView.setSelectedItemId(R.id.daily_habit);

    }
}
