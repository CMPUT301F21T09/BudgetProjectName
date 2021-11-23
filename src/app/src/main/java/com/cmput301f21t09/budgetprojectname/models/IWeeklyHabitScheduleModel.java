package com.cmput301f21t09.budgetprojectname.models;

/**
 * Model interface for the per day of week representation of a habit schedule
 */
public interface IWeeklyHabitScheduleModel extends IHabitScheduleModel {

    /* Identifiers for checking getting data for each day */

    /**
     * Specifier for Monday
     */
    int MONDAY = 0;
    /**
     * Specifier for Tuesday
     */
    int TUESDAY = 1;
    /**
     * Specifier for Wednesday
     */
    int WEDNESDAY = 2;
    /**
     * Specifier for Thursday
     */
    int THURSDAY = 3;
    /**
     * Specifier for Friday
     */
    int FRIDAY = 4;
    /**
     * Specifier for Saturday
     */
    int SATURDAY = 5;
    /**
     * Specifier for Sunday
     */
    int SUNDAY = 6;

    /**
     * Number of days in a week
     */
    int DAYS_IN_WEEK = 7;

    /**
     * Get if the habit is scheduled for that day of the week
     *
     * @param day to check
     * @return true if habit is scheduled for completion on that day
     */
    boolean getDay(int day);

    /**
     * Return all days as an array indexable using the same values as getDay
     *
     * @return array representing all days of the week
     */
    boolean[] getAllDays();
}
