package com.cmput301f21t09.budgetprojectname;

import static org.junit.Assert.assertTrue;

import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.cmput301f21t09.budgetprojectname.controllers.UserController;
import com.cmput301f21t09.budgetprojectname.services.AuthorizationService;
import com.cmput301f21t09.budgetprojectname.views.activities.AnotherUserProfileActivity;
import com.cmput301f21t09.budgetprojectname.views.activities.DefineHabitActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;

/**
 * Intent testing for Another User's Profile activity
 */
public class AnotherUserProfileTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    // User Controller
    UserController userController = new UserController();

    // UserIDs of the currently logged-in user and another user (Test user)
    String currentUserID = AuthorizationService.getInstance().getCurrentUserId();
    String anotherUserID = "BtVfJ5exBBgpYXchbR4wsN8f0cp1";

    /**
     * Runs before all tests and creates solo instance.
     * Creates an incoming follow request to be accepted or denied.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());

        // Remove any instance of currentUserID in anotherUser's social (if it exists)
        userController.readUser(anotherUserID, anotherUser -> {
            HashMap<String, Integer> pendingFollowRequests = anotherUser.getSocial();
            if (pendingFollowRequests.containsKey(currentUserID)) {
                pendingFollowRequests.remove(currentUserID);
            }
            // Update another user's social map
            userController.updateUserSocialMap(anotherUserID, pendingFollowRequests);
        });
    }

    /**
     * Closes the activity after each test
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        // Remove the follow request that has been sent
        userController.readUser(anotherUserID, anotherUser -> {
            HashMap<String, Integer> pendingFollowRequests = anotherUser.getSocial();
            if (pendingFollowRequests.containsKey(currentUserID)) {
                pendingFollowRequests.remove(currentUserID);
            }
            // Update another user's social map
            userController.updateUserSocialMap(anotherUserID, pendingFollowRequests);
        });
        solo.finishOpenedActivities();
    }

    /**
     * Navigates to another user's profile
     */
    public void navigateToUserProfile() {
        assertTrue(solo.waitForView(R.id.today_date));
        solo.clickOnView(solo.getView(R.id.search));
        assertTrue(solo.waitForView(R.id.toolbar_title));

        assertTrue(solo.waitForView(R.id.toolbar_title));
        solo.typeText((EditText) solo.getView(R.id.search_friend_edittext), "testUser2");
        assertTrue(solo.waitForText("Test2 User"));

        // Click on testUser2's profile
        ListView list = (ListView) solo.getView(R.id.user_listview);
        View v = list.getChildAt(0);
        solo.clickOnView(v);
    }

    /**
     * Test for requesting to follow
     */
    @Test
    public void testRequestToFollow() {
        // Go to the user profile page
        navigateToUserProfile();

        // Check that the user profile screen is rendered
        solo.assertCurrentActivity("Wrong Activity", AnotherUserProfileActivity.class);
        assertTrue(solo.waitForText("Test2 User"));
        assertTrue(solo.waitForText("Follow"));

        // Click on the follow button
        solo.clickOnView(solo.getView(R.id.follow_button));

        // Check if a request has been sent
        assertTrue(solo.waitForText("Requested"));
    }
}
