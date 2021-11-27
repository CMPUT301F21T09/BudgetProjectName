package com.cmput301f21t09.budgetprojectname.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.cmput301f21t09.budgetprojectname.services.ServiceTask;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * Tests the HabitScheduleFactorModel
 */

public class HabitScheduleModelFactoryTest {
    private HabitScheduleModelFactory scheduleFactory;
    private IHabitScheduleModel newModel;
    private WeeklyHabitScheduleModel weeklyModel;

    @Before
    public void init() {
        scheduleFactory = new HabitScheduleModelFactory();
        weeklyModel =  new WeeklyHabitScheduleModel();
    }

    /**
     * Tests that an empty (default) instance of the weekly habit schedule is returned
     */
    @Test
    public void testNewModelInstance(){
        /** // TODO: uncomment once tests have been updated
        newModel = scheduleFactory.getNewModelInstance();
        Map<String, Object> map = newModel.toMap();
        Object innerMap = map.get("weekly");
        assertNotNull(innerMap);
         **/
    }

    /**
     * Tests weekly habit schedule from a habit model
     */
    @Test
    public void testGetModelInstanceFromData(){
        /** // TODO: uncomment once tests have been updated
        HabitModel newHabit = HabitModel.getNewInstance().getResult();

        boolean[] days = {true, false, true, false, false, false, false};
        for (int i = 0; i < days.length; i++) {
            weeklyModel.setDay(i, days[i]);
        }
        newHabit.setSchedule(weeklyModel);

        newModel = scheduleFactory.getModelInstanceFromData(newHabit.getSchedule().toMap());
        Map<String, Object> map = newModel.toMap();
        Object innerMap = map.get("weekly");
        assertNotNull(innerMap);
         **/
    }

}
