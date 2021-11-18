package com.cmput301f21t09.budgetprojectname;

import org.junit.Rule;

import android.app.Activity;
import android.widget.EditText;

import org.junit.*;

import static org.junit.Assert.*;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;


/**
 * Test class for user login action
 */
public class LoginTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<UserLoginActivity> rule =
            new ActivityTestRule<>(UserLoginActivity.class, true, true);

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

    void setToLoginScreen() {
        boolean isOnMainScreen = solo.waitForView(R.id.today_date);
        if (isOnMainScreen) {
            solo.goBack();
        }
        assertFalse(solo.waitForView(R.id.today_date));
    }

    /**
     * Test for attempting valid login
     */
    @Test
    public void testValidLogIn() {
        setToLoginScreen();
        solo.assertCurrentActivity("Wrong Activity", UserLoginActivity.class);
        solo.enterText((EditText) solo.getView(R.id.email_edittext), "bkong@ualberta.ca");
        solo.enterText((EditText) solo.getView(R.id.password_edittext), "12345678");
        solo.clickOnButton("Sign in");
        assertTrue(solo.waitForView(R.id.today_date));
    }

    /**
     * Test for attempting invalid email login
     */
    @Test
    public void testInvalidLogin() {
        setToLoginScreen();
        solo.assertCurrentActivity("Wrong Activity", UserLoginActivity.class);
        solo.enterText((EditText) solo.getView(R.id.email_edittext), "abc");
        solo.enterText((EditText) solo.getView(R.id.password_edittext), "12345678");
        solo.clickOnButton("Sign in");
        assertFalse(solo.waitForView(R.id.today_date));
    }

    /**
     * Test for attempting valid email but invalid password login
     */
    @Test
    public void testInvalidPasswordLogin() {
        setToLoginScreen();
        solo.assertCurrentActivity("Wrong Activity", UserLoginActivity.class);
        solo.enterText((EditText) solo.getView(R.id.email_edittext), "bkong@ualberta.ca");
        solo.enterText((EditText) solo.getView(R.id.password_edittext), "11111111");
        solo.clickOnButton("Sign in");
        assertFalse(solo.waitForView(R.id.today_date));
    }

    /**
     * Test for empty fields
     */
    @Test
    public void testEmptyFieldsLogin() {
        setToLoginScreen();
        solo.assertCurrentActivity("Wrong Activity", UserLoginActivity.class);
        solo.clickOnButton("Sign in");
        assertFalse(solo.waitForView(R.id.today_date));
    }
}
