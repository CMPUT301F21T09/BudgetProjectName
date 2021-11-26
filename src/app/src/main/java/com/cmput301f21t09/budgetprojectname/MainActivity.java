package com.cmput301f21t09.budgetprojectname;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.cmput301f21t09.budgetprojectname.views.activities.DefineHabitActivity;
import com.cmput301f21t09.budgetprojectname.views.fragments.CurrentUserProfileFragment;
import com.cmput301f21t09.budgetprojectname.views.fragments.DailyHabitFragment;
import com.cmput301f21t09.budgetprojectname.views.fragments.FollowingFragment;
import com.cmput301f21t09.budgetprojectname.views.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Activity that holds 4 main Fragment
 */
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

        Window window = getWindow();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.daily_habit:
                    if (bottomNavigationView.getSelectedItemId() == R.id.search)
                        updateStatusBarColor(window);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, dailyHabitFragment).commit();
                    return true;
                case R.id.search:
                    if(bottomNavigationView.getSelectedItemId() != R.id.search)
                        updateStatusBarColor(window);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, searchFragment).commit();
                    return true;
                case R.id.add:
                    Intent intent = new Intent(this, DefineHabitActivity.class);
                    startActivity(intent);
                    // using break instead of return to keep previous fragment selection
                    break;
                case R.id.following:
                    if (bottomNavigationView.getSelectedItemId() == R.id.search)
                        updateStatusBarColor(window);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, followingFragment).commit();
                    return true;
                case R.id.profile:
                    if (bottomNavigationView.getSelectedItemId() == R.id.search)
                        updateStatusBarColor(window);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, currentUserProfileFragment).commit();
                    return true;
            }
            return false;
        });

        bottomNavigationView.setSelectedItemId(R.id.daily_habit);
    }

    private void updateStatusBarColor(Window window) {
        int flags = window.getDecorView().getSystemUiVisibility();
        if (window.getStatusBarColor() == ContextCompat.getColor(this, R.color.white)) {
            flags ^= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.secondary_dark));
        } else {
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        }
        window.getDecorView().setSystemUiVisibility(flags);
    }
}
