package com.cmput301f21t09.budgetprojectname.models;

import java.util.Date;

/**
 * Interface class for user habits
 */
public interface IHabitModel {

    /**
     * Habit maximum title length
     */
    int MAX_TITLE_LENGTH = 20;
    /**
     * Habit maximum reason length
     */
    int MAX_REASON_LENGTH = 30;

    /**
     * Get the id of the habit
     * @return id
     */
    String getId();

    /**
     * Get the title of the habit
     * @return habit title
     */
    String getTitle();

    /**
     * Get the reason of the habit
     * @return habit reason
     */
    String getReason();

    /**
     * Get the current streak score
     * @return streak score
     */
    long getStreak();

    /**
     * Get the start date of the habit
     * @return habit start date
     */
    Date getStartDate();

    /**
     * Get the date of the last habit event created for the habit
     * @return get the date that the habit was last completed
     */
    Date getLastCompleted();

}
