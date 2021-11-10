package com.cmput301f21t09.budgetprojectname.controllers;

import com.cmput301f21t09.budgetprojectname.models.IWeeklyHabitScheduleModel;
import com.cmput301f21t09.budgetprojectname.models.WeeklyHabitScheduleModel;

/**
 * Controller for weekly habits
 */
public class WeeklyHabitScheduleController extends BaseController {

    /**
     * Model for controller
     */
    private final WeeklyHabitScheduleModel model;

    /**
     * Private constructor to create controller with model
     *
     * @param model to provide controller
     */
    private WeeklyHabitScheduleController(WeeklyHabitScheduleModel model) {
        this.model = model;
    }

    /**
     * Get an instance of the controller using a boolean array representation of each day of the week
     *
     * @param days array of days per week
     * @return controller initialized with data from the provided array
     */
    public static WeeklyHabitScheduleController getInstanceFromArray(boolean[] days) {
        if (days.length != IWeeklyHabitScheduleModel.DAYS_IN_WEEK)
            throw new IllegalArgumentException("Array length should match DAYS_IN_WEEK");

        WeeklyHabitScheduleModel model = new WeeklyHabitScheduleModel();

        for (int i = 0; i < days.length; i++) {
            model.setDay(i, days[i]);
        }

        return new WeeklyHabitScheduleController(model);
    }

    /**
     * Toggle the state for a day
     *
     * @param day to toggle
     */
    public void toggleDay(int day) {
        model.setDay(day, !model.getDay(day));
        notifyListener();
    }

    /**
     * Get the model this controller handles
     *
     * @return model
     */
    public IWeeklyHabitScheduleModel getModel() {
        return model;
    }
}
