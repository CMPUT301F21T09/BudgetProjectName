package com.cmput301f21t09.budgetprojectname.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model implementation for the per day of week representation of a habit schedule
 */
public class WeeklyHabitScheduleModel implements IWeeklyHabitScheduleModel {
    /**
     * Identifier as found in database for a weekly habit schedule
     */
    public static String WEEKLY_HABIT_SCHEDULE_IDENTIFIER = "doWeekly";

    /**
     * Table for holding the "to complete on" state for each day
     */
    private final boolean[] days = new boolean[IWeeklyHabitScheduleModel.DAYS_IN_WEEK];

    /**
     * Names of each weekday as found in the database
     */
    private static final String[] WEEKDAY_IDENTIFIERS = {
            "doWeeklySunday",
            "doWeeklyMonday",
            "doWeeklyTuesday",
            "doWeeklyWednesday",
            "doWeeklyThursday",
            "doWeeklyFriday",
            "doWeeklySaturday",
    };

    /**
     * Empty constructor for default model instances
     * <p>
     * All days are default set to not be completed
     */
    public WeeklyHabitScheduleModel() {
    }

    /**
     * Return an instance of a WeeklyHabitScheduleModel with data from a list
     *
     * @param list to parse
     * @return model with data set from the given map
     */
    public static WeeklyHabitScheduleModel parseList(List<String> list) {
        WeeklyHabitScheduleModel model = new WeeklyHabitScheduleModel();
        for (String identifier : list) {
            for (int i = 0; i < WEEKDAY_IDENTIFIERS.length; i++) {
                if (identifier.equals(WEEKDAY_IDENTIFIERS[i])) {
                    model.setDay(i, true);
                    break;
                }
            }
        }
        return model;
    }

    @Override
    public boolean isToBeCompletedOn(Date date) {
        Date today = new Date();
        int testedDateOfWeek = date.getDay();
        if (date.after(today)) {
            return days[testedDateOfWeek];
        }
        return false;
    }

    @Override
    public boolean wasSkippedIfLastCompletedOn(Date date) {
        int lastCompletedDateOfWeek = date.getDay();
        int lastCompletedDay = date.getDate();
        int i;
        int current = lastCompletedDay + 1;
        Date today = new Date();
        int todayDayOfWeek = today.getDay();

        if (lastCompletedDateOfWeek == 6) {
            i = 0;
        } else {
            i = lastCompletedDateOfWeek + 1;
        }
        if (lastCompletedDay == today.getDate()) {
            return false;
        }
        while (true) {
            if (days[i]) {
                if (todayDayOfWeek == current) {
                    return false;
                } else {
                    return true;
                }
            }
            if (i == 6) {
                i = 0;
            } else {
                i++;
            }
            current++;
        }
    }

    @Override
    public List<String> toList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < WEEKDAY_IDENTIFIERS.length; i++) {
            if (getDay(i)) list.add(WEEKDAY_IDENTIFIERS[i]);
        }
        return list;
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
