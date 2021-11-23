package com.cmput301f21t09.budgetprojectname;

import org.junit.Rule;
import android.app.Activity;
import android.widget.EditText;

import org.junit.*;
import static org.junit.Assert.*;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.cmput301f21t09.budgetprojectname.views.activities.UserRegisterActivity;
import com.google.firebase.auth.FirebaseUser;
import com.robotium.solo.Solo;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Test class for UserRegisterActivity.
 */
public class UserRegisterTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<UserRegisterActivity> rule =
            new ActivityTestRule<>(UserRegisterActivity.class, true, true);

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
     * Test for creating account with empty fields
     */
    @Test
    public void testEmptyFieldsCreate() {
        solo.assertCurrentActivity("Wrong Activity", UserRegisterActivity.class);
        solo.clickOnView(solo.getView(R.id.create_button));
        assertFalse(solo.waitForView(R.id.today_date));
    }

    /**
     * Test for creating an account with a used email
     */
    @Test
    public void testUseTakenEmail() {
        solo.assertCurrentActivity("Wrong Activity", UserRegisterActivity.class);
        solo.enterText((EditText) solo.getView(R.id.first_name_edittext), "abc");
        solo.enterText((EditText) solo.getView(R.id.last_name_edittext), "defgh");
        solo.enterText((EditText) solo.getView(R.id.email_edittext), "bkong@ualberta.ca");
        solo.enterText((EditText) solo.getView(R.id.username_edittext), "bkong");
        solo.enterText((EditText) solo.getView(R.id.password_edittext), "12345678");
        solo.clickOnView(solo.getView(R.id.create_button));
        assertFalse(solo.waitForView(R.id.today_date));
    }

    /**
     * Test for creating a new account (and deleting it after)
     */
    @Test
    public void testMakeValidAccount() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        solo.assertCurrentActivity("Wrong Activity", UserRegisterActivity.class);
        solo.enterText((EditText) solo.getView(R.id.first_name_edittext), "abc");
        solo.enterText((EditText) solo.getView(R.id.last_name_edittext), "defgh");
        solo.enterText((EditText) solo.getView(R.id.email_edittext), "hello@ualberta.ca");
        solo.enterText((EditText) solo.getView(R.id.username_edittext), "hello");
        solo.enterText((EditText) solo.getView(R.id.password_edittext), "12345678");
        solo.clickOnView(solo.getView(R.id.create_button));
        assertTrue(solo.waitForView(R.id.today_date));
        FirebaseUser user = firebaseAuth.getCurrentUser();
        user.delete();
    }
}