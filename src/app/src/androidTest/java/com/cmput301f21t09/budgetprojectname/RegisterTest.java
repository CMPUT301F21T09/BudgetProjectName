package com.cmput301f21t09.budgetprojectname;

import static org.junit.Assert.assertFalse;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class RegisterTest {

    /**
     * Valid email address to run the test. This address need to be updated after run this test"
     */
    private final String validEmail = "test@test.test";

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
     * Test for attempting valid register
     */
    @Test
    public void testValidRegister() {
        solo.assertCurrentActivity("Wrong Activity", UserRegisterActivity.class);
        solo.enterText((EditText) solo.getView(R.id.first_name_edittext), "FirstName");
        solo.enterText((EditText) solo.getView(R.id.last_name_edittext), "LastName");
        solo.enterText((EditText) solo.getView(R.id.email_edittext), validEmail);
        solo.enterText((EditText) solo.getView(R.id.username_edittext), "testtest");
        solo.enterText((EditText) solo.getView(R.id.password_edittext), "12345678");
        solo.clickOnButton("Create");
        solo.waitForText("Account creation successful");
    }

    /**
     * Test for attempting register with existed email
     */
    @Test
    public void testExistedEmailRegister() {
        solo.assertCurrentActivity("Wrong Activity", UserRegisterActivity.class);
        solo.enterText((EditText) solo.getView(R.id.first_name_edittext), "FirstName");
        solo.enterText((EditText) solo.getView(R.id.last_name_edittext), "LastName");
        solo.enterText((EditText) solo.getView(R.id.email_edittext), validEmail);
        solo.enterText((EditText) solo.getView(R.id.username_edittext), "testtest");
        solo.enterText((EditText) solo.getView(R.id.password_edittext), "12345678");
        solo.clickOnButton("Create");
        solo.waitForText("Account creation failed: The email address is already in use by another account.");
    }

    /**
     * Test for attempting register with invalid email
     */
    @Test
    public void testInvalidEmailRegister() {
        solo.assertCurrentActivity("Wrong Activity", UserRegisterActivity.class);
        solo.enterText((EditText) solo.getView(R.id.first_name_edittext), "FirstName");
        solo.enterText((EditText) solo.getView(R.id.last_name_edittext), "LastName");
        solo.enterText((EditText) solo.getView(R.id.email_edittext), "invalid");
        solo.enterText((EditText) solo.getView(R.id.username_edittext), "testtest");
        solo.enterText((EditText) solo.getView(R.id.password_edittext), "12345678");
        solo.clickOnButton("Create");
        assertFalse(solo.waitForText("Account creation successful"));
    }

    /**
     * Test for attempting register with invalid password
     */
    @Test
    public void testInvalidPasswordRegister() {
        solo.assertCurrentActivity("Wrong Activity", UserRegisterActivity.class);
        solo.enterText((EditText) solo.getView(R.id.first_name_edittext), "FirstName");
        solo.enterText((EditText) solo.getView(R.id.last_name_edittext), "LastName");
        solo.enterText((EditText) solo.getView(R.id.email_edittext), validEmail);
        solo.enterText((EditText) solo.getView(R.id.username_edittext), "testtest");
        solo.enterText((EditText) solo.getView(R.id.password_edittext), "1");
        solo.clickOnButton("Create");
        assertFalse(solo.waitForText("Account creation successful"));
    }

    /**
     * Test for empty fields
     */
    @Test
    public void testEmptyFieldsLogin() {
        solo.assertCurrentActivity("Wrong Activity", UserRegisterActivity.class);
        solo.clickOnButton("Create");
        assertFalse(solo.waitForText("Account creation successful"));
    }
}
