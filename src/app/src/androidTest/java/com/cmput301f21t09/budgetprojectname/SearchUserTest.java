package com.cmput301f21t09.budgetprojectname;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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


/**
 * Test cases for checking search user functionality
 */
public class SearchUserTest {
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
     * Test to check can navigate to screen
     */
    @Test
    public void testNavigateToSearch() {
        assertTrue(solo.waitForView(R.id.today_date));
        solo.clickOnView(solo.getView(R.id.search));
        assertTrue(solo.waitForView(R.id.toolbar_title));
    }

    /**
     * Test to check can navigate to screen
     */
    @Test
    public void testEmptyListShowsBackground() {
        assertTrue(solo.waitForView(R.id.today_date));
        solo.clickOnView(solo.getView(R.id.search));
        assertTrue(solo.waitForView(R.id.toolbar_title));
        assertTrue(solo.waitForView(R.id.search_background));
    }

    /**
     * Test to check can navigate to screen
     */
    @Test
    public void testFindUsers() {
        assertTrue(solo.waitForView(R.id.today_date));
        solo.clickOnView(solo.getView(R.id.search));
        assertTrue(solo.waitForView(R.id.toolbar_title));
        solo.typeText((EditText) solo.getView(R.id.search_friend_edittext), "tey");
        assertTrue(solo.waitForText("Chee Tey"));
    }

    /**
     * Test to check can navigate to screen
     */
    @Test
    public void testClearText() {
        assertTrue(solo.waitForView(R.id.today_date));
        solo.clickOnView(solo.getView(R.id.search));
        assertTrue(solo.waitForView(R.id.toolbar_title));

        solo.typeText((EditText) solo.getView(R.id.search_friend_edittext), "tey");
        assertTrue(solo.waitForText("Chee Tey"));

        solo.clickOnView(solo.getView(R.id.clear_text));
        solo.sleep(500);

        View background = solo.getView(R.id.search_background);
        assertEquals(View.VISIBLE, background.getVisibility());

        ListView list = (ListView) solo.getView(R.id.user_listview);
        assertEquals(View.GONE, list.getVisibility());

    }


}
