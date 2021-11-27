package com.cmput301f21t09.budgetprojectname.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Tests the HabitScheduleFactorModel
 */
public class HabitScheduleModelFactoryTest {
    private HabitScheduleModelFactory scheduleFactory;
    private IHabitScheduleModel newModel;

    @Before
    public void init() {
        scheduleFactory = new HabitScheduleModelFactory();
    }

    /**
     * Tests that an empty (default) instance of the weekly habit schedule is returned
     */
    @Test
    public void testNewModelInstance(){
        newModel = scheduleFactory.getNewModelInstance();
        assertNotNull(newModel);
    }

    /**
     * Tests weekly habit schedule from a habit model
     */
    @Test
    public void testGetModelInstanceFromData(){
        List<String> data = Arrays.asList("doWeeklyTuesday", "doWeeklyFriday");

        newModel = scheduleFactory.getModelInstanceFromData(data);

        Calendar testDate = Calendar.getInstance();

        boolean[] isToBeCompleted = {false, false, true, false, false, true, false};
        final int initialDate = 21;

        for (int date = initialDate; date - initialDate < isToBeCompleted.length; date++) {
            testDate.set(2021, 11, date);
            assertEquals(isToBeCompleted[date - initialDate], newModel.isToBeCompletedOn(testDate.getTime()));
        }

    }

}
