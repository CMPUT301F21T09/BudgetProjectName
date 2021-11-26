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

    private Window window;

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

        window = getWindow();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            int prevItemId = bottomNavigationView.getSelectedItemId();
            if (itemId != R.id.add) {
                if (prevItemId != R.id.search && itemId == R.id.search) {
                    updateStatusBarColor();
                } else if (prevItemId == R.id.search && itemId != R.id.search) {
                    updateStatusBarColor();
                }
            }
            switch (itemId) {
                case R.id.daily_habit:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, dailyHabitFragment).commit();
                    return true;
                case R.id.search:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, searchFragment).commit();
                    return true;
                case R.id.add:
                    Intent intent = new Intent(this, DefineHabitActivity.class);
                    startActivity(intent);
                    // using break instead of return to keep previous fragment selection
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


    /**
     * Update the color of the status bar same as the top colour of screen
     */
    private void updateStatusBarColor() {
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
