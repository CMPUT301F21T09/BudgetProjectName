package com.cmput301f21t09.budgetprojectname;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class DefineHabitEventActivityTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<DefineHabitEventActivity> rule =
            new ActivityTestRule<>(DefineHabitEventActivity.class, true, true);
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
        solo.assertCurrentActivity("Wrong Activity", DefineHabitEventActivity.class);
        solo.enterText((EditText) solo.getView(R.id.comment),
                "thisisasuperlongcommentexceeds20chars");
        solo.clickOnView(solo.getView(R.id.menu_commit_changes));
        boolean isError = solo.waitForText(solo.getString(R.string.errorHabitEventComment),
                1,
                2000);
        assertTrue(isError);
    }

    @Test
    public void checkValidComment(){
        solo.assertCurrentActivity("Wrong Activity", DefineHabitEventActivity.class);
        solo.enterText((EditText) solo.getView(R.id.comment),
                "thisisavalidcomment");
        solo.clickOnView(solo.getView(R.id.menu_commit_changes));
        boolean isError = solo.waitForText(solo.getString(R.string.errorHabitEventComment),
                1,
                2000);
        assertFalse(isError);
    }

}
