package com.cmput301f21t09.budgetprojectname.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

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

    /**
     * Test for toMap method
     */
    @Test
    public void testToList() {
        boolean[] days = {true, false, true, false, false, false, false};
        for (int i = 0; i < days.length; i++) {
            model.setDay(i, days[i]);
        }

        List<String> list = model.toList();
        System.out.println(list.toString());
        assertEquals(2, list.size());
        assertTrue(list.contains("doWeeklySunday"));
        assertTrue(list.contains("doWeeklyTuesday"));
    }
    //TODO: Test isToBeCompletedOn and wasSkippedIfLastCompletedOn once implemented

}
