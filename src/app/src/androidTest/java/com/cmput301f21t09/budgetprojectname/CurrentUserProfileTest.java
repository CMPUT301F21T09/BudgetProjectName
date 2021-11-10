package com.cmput301f21t09.budgetprojectname;

import org.junit.Rule;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import org.junit.*;

import static org.junit.Assert.*;

import androidx.annotation.NonNull;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.robotium.solo.Solo;


/**
 * Test class for currentUserProfileFragment
 */
public class CurrentUserProfileTest {
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
     * Navigates to personal profile fragment
     */
    private void navigateToPersonalProfile() {
        solo.clickOnView(solo.getView(R.id.profile));
        assertTrue(solo.waitForView(R.id.current_user_habit_listview));
    }

    /**
     * Test for logging out
     */
    @Test
    public void testSignOut() {
        navigateToPersonalProfile();
        solo.clickOnView(solo.getView(R.id.logout_button));
        assertTrue(solo.waitForView(R.id.login_label));
    }

    /**
     * Test for checking profile list
     */
    @Test
    public void testAllHabitList() {
        navigateToPersonalProfile();
        ListView listView = (ListView)solo.getView(R.id.current_user_habit_listview);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("habits");
        collectionReference
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        int listViewCount = listView.getCount();
                        if (task.isSuccessful()) {
                            assertEquals(task.getResult().size(), listViewCount);
                        }
                    }
                });
    }

    //TODO: Add intent tests for user name being pulled correctly
}
