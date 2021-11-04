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
    public void testId() {
        String id = "123";
        newHabitModel.setId(id);

        assertEquals(id, newHabitModel.getId());
    }
}
