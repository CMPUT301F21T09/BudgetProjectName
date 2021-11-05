package com.cmput301f21t09.budgetprojectname;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.cmput301f21t09.budgetprojectname.controllers.HabitController;
import com.cmput301f21t09.budgetprojectname.models.HabitModel;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

public class ViewHabitTest {
    private Solo solo;
    private HabitController controller;
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
        // NOTE: assume there is at least one habit in the list
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
     * Test for navigating to edit page
     */
    @Test
    public void checkEditNavigation() {
        solo.waitForText("Today", 1, 3000);
        ListView list = (ListView) solo.getView(R.id.habit_listview);
        View v = list.getChildAt(0);
        // click on first element in list
        solo.clickOnView(v);

        // check that activity changed
        solo.waitForText("Frequency", 1, 3000);
        solo.assertCurrentActivity("Wrong Activity", ViewHabitActivity.class);
        String title = ((TextView) solo.getView(R.id.habitTitle)).getText().toString();
        String reason = ((TextView) solo.getView(R.id.habitDescription)).getText().toString();
        // click edit button
        solo.clickOnView(solo.getView(R.id.editHabitButton));

        // check that activity changed
        solo.waitForText("Edit", 1, 3000);
        solo.assertCurrentActivity("Wrong Activity", DefineHabitActivity.class);
        String editTitle = ((EditText) solo.getView(R.id.adh_editHabitTitle)).getText().toString();
        String editReason = ((EditText) solo.getView(R.id.adh_editHabitReason)).getText().toString();

        assertEquals(title, editTitle);
        assertEquals(reason, editReason);
    }

    /**
     * Test for navigating to past habit events
     */
    @Test
    public void checkPastHabitEventsNavigation() {
        solo.waitForText("Today", 1, 3000);
        ListView list = (ListView) solo.getView(R.id.habit_listview);
        View v = list.getChildAt(0);
        // click on first element in list
        solo.clickOnView(v);

        // check that activity changed
        solo.waitForText("Frequency", 1, 3000);
        solo.assertCurrentActivity("Wrong Activity", ViewHabitActivity.class);
        ListView heList = (ListView) solo.getView(R.id.past_habit_event_list);

        // check that there are past habits in list
        if (heList.getCount() > 0) {
            View heView = heList.getChildAt(0);
            solo.clickOnView(heView);
            // check that activity changed
            solo.waitForText("Delete", 1, 3000);
            solo.assertCurrentActivity("Wrong Activity", ViewHabitEventActivity.class);
        }
    }
}
