package com.cmput301f21t09.budgetprojectname.models;

import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class HabitScheduleModelFactoryTest {
    private HabitScheduleModelFactory scheduleFactory;

    @Before
    public void init() {
        scheduleFactory = new HabitScheduleModelFactory();
    }

    @Test
    public void test(){
//        HabitModel habitModel = HabitModel.getNewInstance();
//        Map<String, Object> map = HabitModel.parseModel();
//
//        scheduleFactory.getModelInstanceFromData((Map<String, Object>) map.get("schedule"))
        IHabitScheduleModel newModel = scheduleFactory.getNewModelInstance();
        Map<String, Object> map = newModel.toMap();
        Object innerMap = map.get("weekly");
        assertNull(innerMap);
    }

}
