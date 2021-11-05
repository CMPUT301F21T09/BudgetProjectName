package com.cmput301f21t09.budgetprojectname;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.cmput301f21t09.budgetprojectname.models.HabitModel;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

/**
 * Tests for database operations on habits mocked using arraylists
 */
public class HabitControllerTest {
    /**
     * Tests creation of a habit
     */
    @Test
    public void testCreateHabit() {
        ArrayList<HabitModel> habitList = new ArrayList<>();
        HabitModel habit = HabitModel.getNewInstance().getResult();
        habit.setId("abcdef213");
        habit.setTitle("Test title");
        habit.setReason("Test Reason ");
        habit.setStartDate(new Date());
        habit.setStreak(0);
        habitList.add(habit);

        assertEquals(1, habitList.size());
        assertTrue(habitList.contains(habit));
    }

    /**
     * Tests update of a habit
     */
    @Test
    public void testUpdateHabit() {
        ArrayList<HabitModel> habitList = new ArrayList<>();
        HabitModel habit = HabitModel.getNewInstance().getResult();
        habit.setId("abcdef213");
        habit.setTitle("Test title");
        habit.setReason("Test Reason ");
        habit.setStartDate(new Date());
        habit.setStreak(0);
        habitList.add(habit);
        habitList.remove(0); // the actual DB will not remove the document
        habit.setReason("updated reason");
        habitList.add(habit);

        assertEquals(1, habitList.size());
        assertEquals("updated reason", habitList.get(0).getReason());
    }

    /**
     * Tests deletion of habit
     */
    @Test
    public void testDeleteHabit() {
        ArrayList<HabitModel> habitList = new ArrayList<>();
        HabitModel habit = HabitModel.getNewInstance().getResult();
        habit.setId("abcdef213");
        habit.setTitle("Test title");
        habit.setReason("Test Reason ");
        habit.setStartDate(new Date());
        habit.setStreak(0);
        habitList.add(habit);
        habitList.remove(0);

        assertEquals(0, habitList.size());
    }

    /**
     * Tests reading of habit
     */
    @Test
    public void testReadHabit() {
        ArrayList<HabitModel> habitList = new ArrayList<>();
        HabitModel habit = HabitModel.getNewInstance().getResult();
        habit.setId("abcdef213");
        habit.setTitle("Title test");
        habit.setReason("Reason test");
        habit.setStartDate(new Date());
        habit.setStreak(0);
        habitList.add(habit);

        HabitModel readHabit = habitList.get(0);
        assertEquals("Title test", readHabit.getTitle());
        assertEquals("Reason test", readHabit.getReason());
    }
}
