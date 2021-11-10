package com.cmput301f21t09.budgetprojectname.models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Model implementation for the per day of week representation of a habit schedule
 */
public class WeeklyHabitScheduleModel implements IWeeklyHabitScheduleModel {
    /**
     * Identifier as found in database for a weekly habit schedule
     */
    public static String WEEKLY_HABIT_SCHEDULE_IDENTIFIER = "weekly";

    /**
     * Table for holding the "to complete on" state for each day
     */
    private final boolean[] days = new boolean[IWeeklyHabitScheduleModel.DAYS_IN_WEEK];

    /**
     * Empty constructor for default model instances
     * <p>
     * All days are default set to not be completed
     */
    public WeeklyHabitScheduleModel() {
    }

    /**
     * Return an instance of a WeeklyHabitScheduleModel with data from a map
     *
     * @param map to parse
     * @return model with data set from the given map
     */
    public static WeeklyHabitScheduleModel parseMap(Map<String, Object> map) {
        WeeklyHabitScheduleModel model = new WeeklyHabitScheduleModel();
        for (int i = 0; i < IWeeklyHabitScheduleModel.DAYS_IN_WEEK; i++) {
            model.setDay(i, map.get(IWeeklyHabitScheduleModel.NAMES_OF_WEEKDAYS[i]) != null && (Boolean) map.get(IWeeklyHabitScheduleModel.NAMES_OF_WEEKDAYS[i]));
        }
        return model;
    }

    @Override
    public boolean isToBeCompletedOn(Date date) {
        return false; // TODO
    }

    @Override
    public boolean wasSkippedIfLastCompletedOn(Date date) {
        return false; // TODO
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < IWeeklyHabitScheduleModel.DAYS_IN_WEEK; i++) {
            map.put(IWeeklyHabitScheduleModel.NAMES_OF_WEEKDAYS[i], getDay(i));
        }
        Map<String, Object> outerMap = new HashMap<>();
        outerMap.put(WEEKLY_HABIT_SCHEDULE_IDENTIFIER, map);
        return outerMap;
    }

    /**
     * Set if the habit is scheduled for that day of the week
     *
     * @param day      to set
     * @param complete true if habit is to be completed that day
     */
    public void setDay(int day, boolean complete) {
        if (day < 0 || day > IWeeklyHabitScheduleModel.DAYS_IN_WEEK)
            throw new IllegalArgumentException("Argument 'day' must be one of the weekday constants");

        days[day] = complete;
    }

    @Override
    public boolean getDay(int day) {
        if (day < 0 || day > IWeeklyHabitScheduleModel.DAYS_IN_WEEK)
            throw new IllegalArgumentException("Argument 'day' must be one of the weekday constants");

        return days[day];
    }

    @Override
    public boolean[] getAllDays() {
        return days.clone();
    }

}
