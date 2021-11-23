package com.cmput301f21t09.budgetprojectname.models;

import static org.junit.Assert.assertEquals;

<<<<<<< HEAD:src/app/src/test/java/com/cmput301f21t09/budgetprojectname/HabitEventModelTest.java
=======
import android.media.Image;

import com.cmput301f21t09.budgetprojectname.models.HabitEventModel;
import com.cmput301f21t09.budgetprojectname.models.LatLngModel;

>>>>>>> location:src/app/src/test/java/com/cmput301f21t09/budgetprojectname/models/HabitEventModelTest.java
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Test class for the HabitEventModel.
 * Verifies that data is set and get correctly.
 */
public class HabitEventModelTest {
    private HabitEventModel testHE;

    @Before
    public void init() {
<<<<<<< HEAD:src/app/src/test/java/com/cmput301f21t09/budgetprojectname/HabitEventModelTest.java
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, 1, 1);

        testHE = new HabitEventModel("habiteventid", "YEG",
                calendar.getTime(), "comment", "ImageData", "habitid");
=======
        testHE = new HabitEventModel("habiteventid", new LatLngModel(0.0, 0.0),
                new Date(1, 1, 1), "comment", null, "habitid");
>>>>>>> location:src/app/src/test/java/com/cmput301f21t09/budgetprojectname/models/HabitEventModelTest.java
    }

    @Test
    public void testHabitEventModelConstructor() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String id = "habiteventid";
<<<<<<< HEAD:src/app/src/test/java/com/cmput301f21t09/budgetprojectname/HabitEventModelTest.java
        String location = "YEG";

        Calendar calendar = Calendar.getInstance();
        calendar.set(1, 1, 1);
        Date date = calendar.getTime();

=======
        LatLngModel location = new LatLngModel(0.0, 0.0);
        Date date = new Date(1, 1, 1);
>>>>>>> location:src/app/src/test/java/com/cmput301f21t09/budgetprojectname/models/HabitEventModelTest.java
        String comment = "comment";
        String image = "ImageData";
        String habitID = "habitid";

        assertEquals(id, testHE.getID());
        assertEquals(location, testHE.getLocation());
        assertEquals(dateFormat.format(date), dateFormat.format(testHE.getDate()));
        assertEquals(comment, testHE.getComment());
        assertEquals(image, testHE.getImage());
        assertEquals(habitID, testHE.getHabitID());
    }

    @Test
    public void testHabitEventModelSetters() {
        String id = "newhabiteventid";
<<<<<<< HEAD:src/app/src/test/java/com/cmput301f21t09/budgetprojectname/HabitEventModelTest.java
        String location = "YYC";

        Calendar calendar = Calendar.getInstance();
        calendar.set(1, 1, 1);
        Date date = calendar.getTime();

=======
        LatLngModel location = new LatLngModel(1.0, 1.0);
        Date date = new Date(2, 2, 2);
>>>>>>> location:src/app/src/test/java/com/cmput301f21t09/budgetprojectname/models/HabitEventModelTest.java
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
