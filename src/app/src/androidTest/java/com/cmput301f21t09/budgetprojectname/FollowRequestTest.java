package com.cmput301f21t09.budgetprojectname;

import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.cmput301f21t09.budgetprojectname.controllers.UserController;
import com.cmput301f21t09.budgetprojectname.views.activities.FollowRequestActivity;
import com.cmput301f21t09.budgetprojectname.views.activities.ViewHabitActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;

/**
 * Intent testing for follow request activity
 */
public class FollowRequestTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    /**
     * Closes the activity after each test
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    /**
     * Gets the Activity
     *
     * @throws Exception
     */
    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    /**
     * Navigates to personal profile fragment
     */
    private void navigateToFollowFragment() {
        solo.clickOnView(solo.getView(R.id.following));
        assertTrue(solo.waitForView(R.id.following_list));

    }

    /**
     * Test for navigating to follow request page
     */
    @Test
    public void testNavigateToFollowRequests() {
        navigateToFollowFragment();
        solo.clickOnView(solo.getView(R.id.requests_button));
        solo.waitForText("Follow Requests", 1, 3000);
        solo.assertCurrentActivity("Wrong Activity", FollowRequestActivity.class);
    }

    @Test
    public void testAcceptRequest(){
        // go to follow request page
        navigateToFollowFragment();
        solo.clickOnView(solo.getView(R.id.requests_button));
        solo.waitForText("Follow Requests", 1, 3000);

        String currentUserId = "XPG70micV5XLyvhytqghBOLnK8U2";
        UserController userController = new UserController();
        // get current user model (test user)
        userController.readUser(currentUserId, retrievedAnotherUser -> {
            Log.d("",retrievedAnotherUser.getFirstName() + " " + retrievedAnotherUser.getLastName());
            // add a follow request into current user's social map
            HashMap<String, Integer> incomingFollowRequest = new HashMap<String, Integer>();
            incomingFollowRequest.put(currentUserId, newValue);
            userController.updateUserSocialMap(anotherUserID, incomingFollowRequest);
        });

    }

}
