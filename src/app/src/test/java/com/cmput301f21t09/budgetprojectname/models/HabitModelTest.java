package com.cmput301f21t09.budgetprojectname.models;

import static org.junit.Assert.assertEquals;

import com.cmput301f21t09.budgetprojectname.services.ServiceTask;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

/**
 * Test class for the HabitModel.
 * Verifies that data is set and get correctly.
 */
public class HabitModelTest {
    private HabitModel newHabitModel;

    @Before
    public void init() {
        ServiceTask<HabitModel> newServiceTaskHabitModel = HabitModel.getNewInstance();
        newHabitModel = newServiceTaskHabitModel.getResult();
    }

    @Test
    public void testIdGetterAndSetter() {
        String id = "123";
        newHabitModel.setId(id);
        assertEquals(id, newHabitModel.getId());
    }

    @Test
    public void testTitleGetterAndSetter() {
        String title = "Dishes";
        newHabitModel.setTitle(title);
        assertEquals(title, newHabitModel.getTitle());
    }

    @Test
    public void testReasonGetterAndSetter() {
        String reason = "this is a good reason";
        newHabitModel.setReason(reason);
        assertEquals(reason, newHabitModel.getReason());
    }

    @Test
    public void testGetStreak() {
        //TODO: increment streak
        assertEquals(newHabitModel.getStreak(), 0);
    }

    @Test
    public void testStartDateGetterAndSetter() {
        Date date = new Date(2021, 1, 1);
        newHabitModel.setStartDate(date);
        assertEquals(date, newHabitModel.getStartDate());
    }

    @Test
    //TODO: set lastCompleted
    public void testGetLastCompleted() {
    }
}
