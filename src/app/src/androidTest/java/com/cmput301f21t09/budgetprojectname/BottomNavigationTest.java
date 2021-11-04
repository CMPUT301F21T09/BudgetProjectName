package com.cmput301f21t09.budgetprojectname;

import org.junit.Rule;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import org.junit.*;
import static org.junit.Assert.*;

import androidx.fragment.app.Fragment;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.robotium.solo.Solo;


/**
 * Test class for the bottomNavigation which currently resides in MainActivity.
 */
public class BottomNavigationTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }

    /**
     * Closes the activity after each test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

    /**
     * Gets the Activity
     * @throws Exception
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    /**
     * Test for navigating to profile
     */
    @Test
    public void navigateToProfile(){
        assertTrue(solo.waitForView(R.id.today_date));
        solo.clickOnView(solo.getView(R.id.profile));
        assertTrue(solo.waitForView(R.id.current_user_habit_listview));
    }

    /**
     * Test for navigating to add new habit
     */
    @Test
    public void addPressed(){
        assertTrue(solo.waitForView(R.id.today_date));
        solo.clickOnView(solo.getView(R.id.add));
        assertTrue(solo.waitForText("Create Habit"));
    }
}
