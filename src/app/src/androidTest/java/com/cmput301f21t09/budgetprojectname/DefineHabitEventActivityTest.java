package com.cmput301f21t09.budgetprojectname;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.cmput301f21t09.budgetprojectname.views.activities.DefineHabitEventActivity;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test class for testing the define habit event activity when a user completes a habit for the day
 * ASSUMPTION: assume user has at minimum 1 habit in their daily habit list
 */
public class DefineHabitEventActivityTest {
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
     * Gets the Activity
     * @throws Exception
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    @Test
    public void checkInvalidComment(){
        solo.waitForText("Today", 1, 3000);
        solo.clickOnView(solo.getView(R.id.done_button));
        solo.assertCurrentActivity("Wrong Activity", DefineHabitEventActivity.class);
        solo.enterText((EditText) solo.getView(R.id.comment),
                "thisisasuperlongcommentexceeds20chars");

        solo.clickOnView(solo.getView(R.id.habit_event_confirm));
        boolean isError = solo.waitForText(solo.getString(R.string.errorHabitEventComment),
                1,
                2000);
        assertTrue(isError);
    }

    @Test
    public void checkValidComment(){
        solo.waitForText("Today", 1, 3000);
        solo.clickOnView(solo.getView(R.id.done_button));
        solo.assertCurrentActivity("Wrong Activity", DefineHabitEventActivity.class);
        solo.enterText((EditText) solo.getView(R.id.comment),
                "thisisavalidcomment");

        solo.clickOnView(solo.getView(R.id.habit_event_confirm));
        boolean isError = solo.waitForText(solo.getString(R.string.errorHabitEventComment),
                1,
                2000);
        assertFalse(isError);
    }

}
