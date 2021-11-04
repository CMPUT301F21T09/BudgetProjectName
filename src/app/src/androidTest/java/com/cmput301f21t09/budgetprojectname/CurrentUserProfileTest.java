package com.cmput301f21t09.budgetprojectname;

import org.junit.Rule;

import android.app.Activity;
import android.widget.EditText;

import org.junit.*;

import static org.junit.Assert.*;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;
import com.robotium.solo.Solo;


/**
 * Test class for currentUserProfileFragment
 */
public class CurrentUserProfileTest {
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

    void navigateToPersonalProfile() {
        solo.clickOnView(solo.getView(R.id.profile));
        assertTrue(solo.waitForView(R.id.current_user_habit_listview));
    }

    /**
     * Test for logging out
     */
    @Test
    public void testSignOut() {
        navigateToPersonalProfile();
        solo.clickOnView(solo.getView(R.id.logout_button));
        assertTrue(solo.waitForView(R.id.login_label));
    }

    //TODO: Add intent tests for profile information being pulled correctly
}
