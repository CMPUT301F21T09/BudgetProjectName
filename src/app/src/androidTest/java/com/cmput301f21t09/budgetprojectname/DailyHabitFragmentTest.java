package com.cmput301f21t09.budgetprojectname;

import static org.junit.Assert.assertTrue;
import android.app.Activity;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Test class for dailyHabitFragment class
 */
public class DailyHabitFragmentTest {
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

    /**
     * Test to check correct date
     */
    @Test
    public void testCorrectDate() {
        String dateToday = new SimpleDateFormat("EEEE, MMMM d").format(new Date());
        TextView dateText = (TextView) solo.getView(R.id.today_date);
        String text = String.valueOf(dateText.getText());
        assertTrue(text.equals(dateToday));
    }

    //TODO: Test daily habit list
}