package com.cmput301f21t09.budgetprojectname;

import static org.junit.Assert.assertEquals;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

public class HabitEventTest {
    private Solo solo;
    private String habitEventID_DELETE = "";
    @Rule
    public ActivityTestRule<ExampleActivity> rule =
            new ActivityTestRule<>(ExampleActivity.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        // create a habitevent to delete and update
        HabitEventController habitEventController = new HabitEventController();
        HabitEventModel habitEvent = new HabitEventModel(null, "YEG",
                new Date(1,1,1),"comment",
                null, "IdninLJ01WM1PD8e4NzX");
        habitEventController.createHabitEvent(habitEvent, new HabitEventController.HabitEventIDCallback() {
            @Override
            public void onCallback(String habitEventID) {
                System.out.println("habitevent id " + habitEventID);
                habitEventID_DELETE = habitEventID;
            }
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
        System.out.println("printing!");
    }

    /**
     * Tests the deletion of a habit event
     */
    @Test
    public void testDeleteHabitEvent(){
        solo.clickOnView(solo.getView(R.id.viewHabitDetailsBtn));

        // check that list contains newly created habitevent
        solo.waitForActivity("ViewHabitActivity");
        solo.waitForText("February", 1, 3000);
        ListView list = (ListView) solo.getView(R.id.past_habit_event_list);

        // save current number of elements in list
        int oldCount =  list.getCount();
        View v = list.getChildAt(0);
        // click on first element in list
        solo.clickOnView(v);

        // wait for habit event to load
        solo.waitForActivity("ViewHabitEventActivity");
        solo.waitForText("February", 1, 3000);

        // delete the habit event
        solo.clickOnView(solo.getView(R.id.view_habit_event_habit_event_delete_button));

        // check that we go back to the viewhabitactivity
        solo.waitForText("Past", 1, 3000);
        solo.assertCurrentActivity("Wrong Activity", ViewHabitActivity.class);
        ListView modList = (ListView) solo.getView(R.id.past_habit_event_list);
        int newCount =  modList.getCount();

        // check that the list has one less element
        assertEquals(oldCount-1, newCount);
    }

    /**
     * Tests the update of a habit event
     */
    @Test
    public void testUpdateHabitEvent(){
        // TODO: add test
        solo.clickOnView(solo.getView(R.id.viewHabitDetailsBtn));

        // check that list contains newly created habitevent
        solo.waitForActivity("ViewHabitActivity");
        solo.waitForText("February", 1, 3000);
        ListView list = (ListView) solo.getView(R.id.past_habit_event_list);

        View v = list.getChildAt(0);
        // click on first element in list
        solo.clickOnView(v);

        // wait for habit event to load
        solo.waitForActivity("ViewHabitEventActivity");
        solo.waitForText("February", 1, 3000);

        // edit the habit event
        solo.clickOnView(solo.getView(R.id.view_habit_event_habit_event_edit_button));

        // check that we go back to the definehabiteventactivity
        solo.waitForText("comment", 1, 3000);
        solo.assertCurrentActivity("Wrong Activity", DefineHabitEventActivity.class);

        // update fields
        solo.clearEditText((EditText) solo.getView(R.id.location)); // Clear edittext
        solo.clearEditText((EditText) solo.getView(R.id.comment)); // Clear edittext
        solo.enterText((EditText)solo.getView(R.id.location), "Edmonton");
        solo.enterText((EditText)solo.getView(R.id.comment), "updated comment");

        // click checkmark to confirm changes
        solo.clickOnView(solo.getView(R.id.done));

        // wait for habit event to load
        solo.waitForActivity("ViewHabitEventActivity");
        solo.waitForText("February", 1, 3000);

        // check that location is updated
        String modLocation = ((EditText) solo.getView(R.id.location)).getText().toString();
        assertEquals("Edmonton", modLocation);

        // check that comment is updated
        String modComment = ((EditText) solo.getView(R.id.comment)).getText().toString();
        assertEquals("updated comment", modComment);


    }

}
