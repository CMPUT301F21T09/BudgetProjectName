package com.cmput301f21t09.budgetprojectname.models;

import java.util.Date;
import java.util.List;

/**
 * Model interface for a habit's schedule
 */
public interface IHabitScheduleModel {

    /**
     * Check if the habit is meant to be completed on the given date
     *
     * @param date to check
     * @return true if the habit should be completed on the given date
     */
    boolean isToBeCompletedOn(Date date);

    /**
     * Check if the are missing days if the habit was last completed on the given date
     *
     * @param date to check
     * @return true if there are missing days in the schedule
     */
    boolean wasSkippedIfLastCompletedOn(Date date);

    /**
     * Serialize the model to a map
     *
     * @return serialized version of model
     */
    List<String> toList();
}
