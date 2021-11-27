package com.cmput301f21t09.budgetprojectname;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.util.Log;
import android.widget.ListView;

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
     * Creates an incoming follow request to be accepted or denied.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        UserController userController = new UserController();
        String currentUserId = "XPG70micV5XLyvhytqghBOLnK8U2";
        String anotherUserID = "BtVfJ5exBBgpYXchbR4wsN8f0cp1";

        // get current user model (test user)
        userController.readUser(currentUserId, currentUser -> {
            HashMap<String, Integer> incomingFollowRequests = currentUser.getSocial();

            // add a follow request into current user's social map
            incomingFollowRequests.put(anotherUserID, 0);
            userController.updateUserSocialMap(currentUserId, incomingFollowRequests);

            // click accept on this request
            solo.waitForText("Test", 1, 3000);
            // solo.clickOnView(solo.getView(R.id.accept_button));

        });

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
        solo.waitForText("Follow Requests", 1, 3000);
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

    /**
     * Test for accepting a follow request
     */
    @Test
    public void testAcceptRequest(){
        // go to follow request page
        navigateToFollowFragment();

        solo.clickOnView(solo.getView(R.id.requests_button));
        solo.waitForText("Follow Requests", 1, 3000);
        ListView listView = (ListView) solo.getView(R.id.follow_request_listview);

        // check that request shows up
        assertEquals(1, listView.getCount());

        // click accept on request
        solo.clickOnView(solo.getView(R.id.accept_button));

        // wait for a reload of page
        solo.waitForText("Follow Requests", 1, 3000);

        // check that request is removed
        listView = (ListView) solo.getView(R.id.follow_request_listview);
        assertEquals(0, listView.getCount());
    }

    /**
     * Test for denying a follow request
     */
    @Test
    public void testDenyRequest(){
        // go to follow request page
        navigateToFollowFragment();

        solo.clickOnView(solo.getView(R.id.requests_button));
        solo.waitForText("Follow Requests", 1, 3000);
        ListView listView = (ListView) solo.getView(R.id.follow_request_listview);

        // check that request shows up
        assertEquals(1, listView.getCount());

        // click deny on request
        solo.clickOnView(solo.getView(R.id.decline_button));

        // wait for a reload of page
        solo.waitForText("Follow Requests", 1, 3000);

        // check that request is removed
        listView = (ListView) solo.getView(R.id.follow_request_listview);
        assertEquals(0, listView.getCount());
    }

}
