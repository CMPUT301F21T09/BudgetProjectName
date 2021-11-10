package com.cmput301f21t09.budgetprojectname;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.graphics.Bitmap;
import android.media.Image;
import android.text.format.DateUtils;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Test class for the HabitEventModel.
 * Verifies that data is set and get correctly.
 */
public class HabitEventModelTest {
    private HabitEventModel testHE;

    @Before
    public void init() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, 1, 1);

        testHE = new HabitEventModel("habiteventid", "YEG",
                calendar.getTime(), "comment", "ImageData", "habitid");
    }

    @Test
    public void testHabitEventModelConstructor() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String id = "habiteventid";
        String location = "YEG";

        Calendar calendar = Calendar.getInstance();
        calendar.set(1, 1, 1);
        Date date = calendar.getTime();

        String comment = "comment";
        String image = "ImageData";
        String habitID = "habitid";

        assertEquals(id, testHE.getID());
        assertEquals(location, testHE.getLocation());
        assertEquals(dateFormat.format(date) , dateFormat.format(testHE.getDate()));
        assertEquals(comment, testHE.getComment());
        assertEquals(image, testHE.getImage());
        assertEquals(habitID, testHE.getHabitID());
    }

    @Test
    public void testHabitEventModelSetters() {
        String id = "newhabiteventid";
        String location = "YYC";

        Calendar calendar = Calendar.getInstance();
        calendar.set(1, 1, 1);
        Date date = calendar.getTime();

        String comment = "newcomment";
        String image = "newImageData";
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
