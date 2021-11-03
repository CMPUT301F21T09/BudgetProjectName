package com.cmput301f21t09.budgetprojectname;


import android.annotation.SuppressLint;
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
        MainFragment habitFragment = new MainFragment();
        SearchFragment searchFragment = new SearchFragment();
        AddFragment addFragment = new AddFragment();
        FollowingFragment followingFragment = new FollowingFragment();
        ProfileFragment profileFragment = new ProfileFragment();

        // Fragment switch
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.main:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, habitFragment).commit();
                    return true;
                case R.id.search:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, searchFragment).commit();
                    return true;
                case R.id.add:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, addFragment).commit();
                    return true;
                case R.id.following:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, followingFragment).commit();
                    return true;
                case R.id.profile:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, profileFragment).commit();
                    return true;
            }
            return false;
        });
        bottomNavigationView.setSelectedItemId(R.id.main);

    }
}
