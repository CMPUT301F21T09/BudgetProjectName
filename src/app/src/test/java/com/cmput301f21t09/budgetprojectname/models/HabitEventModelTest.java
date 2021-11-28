package com.cmput301f21t09.budgetprojectname.models;

import static org.junit.Assert.assertEquals;

import android.media.Image;

import com.cmput301f21t09.budgetprojectname.models.HabitEventModel;
import com.cmput301f21t09.budgetprojectname.models.LatLngModel;

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
        testHE = new HabitEventModel("habiteventid", new LatLngModel(0.0, 0.0),
                new Date(1, 1, 1), "comment", null, "habitid");
    }

    @Test
    public void testHabitEventModelConstructor() {
        String id = "habiteventid";
        Double latitude = 0.0;
        Double longitude = 0.0;
        Date date = new Date(1, 1, 1);
        String comment = "comment";
        String image = null;
        String habitID = "habitid";

        assertEquals(id, testHE.getID());
        assertEquals(latitude, testHE.getLocation().getLatitude());
        assertEquals(longitude, testHE.getLocation().getLongitude());
        assertEquals(date, testHE.getDate());
        assertEquals(comment, testHE.getComment());
        assertEquals(image, testHE.getImage());
        assertEquals(habitID, testHE.getHabitID());
    }

    @Test
    public void testHabitEventModelSetters() {
        String id = "newhabiteventid";
        LatLngModel location = new LatLngModel(1.0, 1.0);
        Date date = new Date(2, 2, 2);
        String comment = "newcomment";
        String image = null;
        String habitID = "newhabitid";

        testHE.setID(id);
        testHE.setLocation(location);
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
