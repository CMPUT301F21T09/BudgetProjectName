package com.cmput301f21t09.budgetprojectname;

import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


/**
 * Tests for DefineHabitActivity
 */

public class DefineHabitActivityTest {
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
     * Test for navigating to add habit screen
     */
    @Test
    public void testNavigateToAddHabit() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView(solo.getView(R.id.add));
        solo.waitForText("Create Habit");
    }

    /**
     * Test for creating habit without title
     */
    @Test
    public void testNoTitleHabitCreation() {
        testNavigateToAddHabit();
        solo.clickOnView(solo.getView(R.id.habit_confirm));
        solo.waitForText("Create Habit");
    }

    /**
     * Test for creating new habit (and then deleting it from firebase)
     */
    @Test
    public void testCreateNewHabit() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        testNavigateToAddHabit();
        String titleText = "Heyheyheyheyhey";
        String reasonText = "A very good reason";
        solo.enterText((EditText) solo.getView(R.id.adh_editHabitTitle), titleText);
        solo.enterText((EditText) solo.getView(R.id.adh_editHabitReason), reasonText);
        solo.clickOnView(solo.getView(R.id.habit_confirm));

        CollectionReference collectionReference = db.collection("habits");
        collectionReference
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            boolean hasNewHabitInDatabase = false;
                            String documentID = "";
                            // Append every document into habitEventDataList
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                String ID = (String) doc.getId();
                                String title = (String) doc.getData().get("title");
                                String reason = (String) doc.getData().get("reason");
                                if (reasonText.equals(reason) && titleText.equals(title)) {
                                    hasNewHabitInDatabase = true;
                                    documentID = ID;
                                    collectionReference.document(ID).delete();

                                }
                            }
                            assertTrue(hasNewHabitInDatabase);
                        }
                    }
                });
    }

}
