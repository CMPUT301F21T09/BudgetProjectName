package com.cmput301f21t09.budgetprojectname.models;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HabitScheduleModelFactory {

    /**
     * Public Constructor for creating a new instance of the HabitScheduleFactory
     */
    public HabitScheduleModelFactory() {
    }

    /**
     * Returns an instance of the model from the provided data
     *
     * @param data to parse
     * @return instance of model based on provided data
     */
    IHabitScheduleModel getModelInstanceFromData(List<String> data) {
        return WeeklyHabitScheduleModel.parseList(data);
    }

    /**
     * Returns an empty (default) instance of the weekly habit schedule
     *
     * @return weekly habit schedule
     */
    IHabitScheduleModel getNewModelInstance() {
        return new WeeklyHabitScheduleModel();
    }

    List<String> getQueryForToday() {
        WeeklyHabitScheduleModel weeklyModel = new WeeklyHabitScheduleModel();
        Calendar today = Calendar.getInstance();
        if (today.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) weeklyModel.setDay(IWeeklyHabitScheduleModel.MONDAY, true);
        else if (today.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) weeklyModel.setDay(IWeeklyHabitScheduleModel.TUESDAY, true);
        else if (today.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) weeklyModel.setDay(IWeeklyHabitScheduleModel.WEDNESDAY, true);
        else if (today.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) weeklyModel.setDay(IWeeklyHabitScheduleModel.THURSDAY, true);
        else if (today.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) weeklyModel.setDay(IWeeklyHabitScheduleModel.FRIDAY, true);
        else if (today.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) weeklyModel.setDay(IWeeklyHabitScheduleModel.SATURDAY, true);
        else if (today.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) weeklyModel.setDay(IWeeklyHabitScheduleModel.SUNDAY, true);
        return weeklyModel.toList();
    }
}
