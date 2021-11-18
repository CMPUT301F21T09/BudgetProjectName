package com.cmput301f21t09.budgetprojectname;

import static org.junit.Assert.assertEquals;

import android.media.Image;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

/**
 * Test class for the HabitEventModel.
 * Verifies that data is set and get correctly.
 */
public class HabitEventModelTest {
    private HabitEventModel testHE;

    @Before
    public void init() {
        testHE = new HabitEventModel("habiteventid", null,
                new Date(1, 1, 1), "comment", null, "habitid");
    }

    @Test
    public void testHabitEventModelConstructor() {
        String id = "habiteventid";
        String location = "YEG";
        Date date = new Date(1, 1, 1);
        String comment = "comment";
        Image image = null;
        String habitID = "habitid";

        assertEquals(id, testHE.getID());
        //assertEquals(location, testHE.getLocation());
        assertEquals(date, testHE.getDate());
        assertEquals(comment, testHE.getComment());
        assertEquals(image, testHE.getImage());
        assertEquals(habitID, testHE.getHabitID());
    }

    @Test
    public void testHabitEventModelSetters() {
        String id = "newhabiteventid";
        String location = "YYC";
        Date date = new Date(2, 2, 2);
        String comment = "newcomment";
        Image image = null;
        String habitID = "newhabitid";

        testHE.setID(id);
        //testHE.setLocation(location);
        testHE.setDate(date);
        testHE.setComment(comment);
        testHE.setImage(image);
        testHE.setHabitID(habitID);

        assertEquals(id, testHE.getID());
        assertEquals(location, testHE.getLocation());
        assertEquals(date, testHE.getDate());
        assertEquals(comment, testHE.getComment());
        assertEquals(image, testHE.getImage());
        assertEquals(habitID, testHE.getHabitID());
    }
}
