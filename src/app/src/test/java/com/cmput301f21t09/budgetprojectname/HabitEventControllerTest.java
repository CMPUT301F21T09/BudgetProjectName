package com.cmput301f21t09.budgetprojectname;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.cmput301f21t09.budgetprojectname.models.HabitModel;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

/**
 * Tests for database operations on habit events mocked using arraylists
 */
public class HabitEventControllerTest {

    /**
     * Tests creation of a habit event
     */
    @Test
    public void testCreateHabitEvent() {
        ArrayList<HabitEventModel> habitEventList = new ArrayList<>();
        HabitEventModel habitEvent =
                new HabitEventModel(null, null, new Date(),
                        "yeg test comment", null, "");
        habitEventList.add(habitEvent);

        assertEquals(1, habitEventList.size());
        assertTrue(habitEventList.contains(habitEvent));
    }

    /**
     * Tests update of a habit event
     */
    @Test
    public void testUpdateHabitEvent() {
        ArrayList<HabitEventModel> habitEventList = new ArrayList<>();
        HabitEventModel habitEvent =
                new HabitEventModel(null, null, new Date(),
                        "yeg test comment", null, "");
        habitEventList.add(habitEvent);
        HabitEventModel toUpdateHE = habitEventList.get(0);
        habitEventList.remove(0); // the actual DB will not remove the document
        toUpdateHE.setComment("updated comment");
        habitEventList.add(toUpdateHE);

        assertEquals(1, habitEventList.size());
        assertEquals("updated comment", habitEventList.get(0).getComment());
    }

    /**
     * Tests deletion of habit event
     */
    @Test
    public void testDeleteHabitEvent() {
        ArrayList<HabitEventModel> habitEventList = new ArrayList<>();
        HabitEventModel habitEvent =
                new HabitEventModel(null, null, new Date(),
                        "yeg test comment", null, "");
        habitEventList.add(habitEvent);
        habitEventList.remove(0);

        assertEquals(0, habitEventList.size());
    }

    /**
     * Tests reading of habit event
     */
    @Test
    public void testReadHabitEvent() {
        ArrayList<HabitEventModel> habitEventList = new ArrayList<>();

        HabitEventModel habitEvent =
                new HabitEventModel(null, null, new Date(),
                        "yeg test comment", null, "");
        habitEventList.add(habitEvent);
        HabitEventModel readHE = habitEventList.get(0);
        assertEquals("YEGtest", readHE.getLocation());
        assertEquals("yeg test comment", readHE.getComment());
    }


}
