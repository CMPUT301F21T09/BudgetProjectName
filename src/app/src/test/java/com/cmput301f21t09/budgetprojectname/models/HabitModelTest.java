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

    /**
     * Test for setting and getting ID from habitModel
     */
    @Test
    public void testIdGetterAndSetter() {
        String id = "123";
        newHabitModel.setId(id);
        assertEquals(id, newHabitModel.getId());
    }

    /**
     * Test for setting and getting title from habitModel
     */
    @Test
    public void testTitleGetterAndSetter() {
        String title = "Dishes";
        newHabitModel.setTitle(title);
        assertEquals(title, newHabitModel.getTitle());
    }

    /**
     * Test for setting and getting reason from habitModel
     */
    @Test
    public void testReasonGetterAndSetter() {
        String reason = "this is a good reason";
        newHabitModel.setReason(reason);
        assertEquals(reason, newHabitModel.getReason());
    }

    /**
     * Test for incrementing and getting streak from habitModel
     */
    @Test
    public void testGetStreak() {
        //TODO: increment streak
        assertEquals(newHabitModel.getStreak(), 0);
    }

    /**
     * Test for setting and getting startDate from habitModel
     */
    @Test
    public void testStartDateGetterAndSetter() {
        Date date = new Date(2021, 1, 1);
        newHabitModel.setStartDate(date);
        assertEquals(date, newHabitModel.getStartDate());
    }

    /**
     * Test for setting and getting privacy from habitModel
     */
    @Test
    public void testPrivacyGetterAndSetter() {
        newHabitModel.setIsPrivate(true);
        assertEquals(true, newHabitModel.getIsPrivate());
        newHabitModel.setIsPrivate(false);
        assertEquals(false, newHabitModel.getIsPrivate());
    }

    /**
     * Test for incrementing and getting lastCompleted from habitModel
     */
    @Test
    //TODO: set lastCompleted
    public void testGetLastCompleted() {
    }
}
