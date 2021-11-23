package com.cmput301f21t09.budgetprojectname.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * Unit tests for HabitScheduleModel
 */
public class WeeklyHabitScheduleModelTest {
    private WeeklyHabitScheduleModel model;

    @Before
    public void init() {
        model = new WeeklyHabitScheduleModel();
    }

    /**
     * Test for setting and getting day in model
     */
    @Test
    public void testDaySetterAndGetter() {
        model.setDay(0, true);
        assertTrue(model.getDay(0));
    }

    /**
     * Test for getting all days from model
     */
    @Test
    public void testGetAllDays() {
        boolean[] days = {true, false, true, false, false, false, false};
        for (int i = 0; i < days.length; i++) {
            model.setDay(i, days[i]);
        }
        boolean[] daysList = model.getAllDays();
        for (int i = 0; i < days.length; i++) {
            assertEquals(daysList[i], days[i]);
        }
    }

//    /**
//     * Test for toMap method
//     */
//    @Test
//    public void testToMap() {
//        boolean[] days = {true, false, true, false, false, false, false};
//        for (int i = 0; i < days.length; i++) {
//            model.setDay(i, days[i]);
//        }
//        Map<String, Object> map = model.toMap();
//        Object innerMap = map.get("weekly");
//        assertNotNull(innerMap);
//    }
    //TODO: Test isToBeCompletedOn and wasSkippedIfLastCompletedOn once implemented

}
