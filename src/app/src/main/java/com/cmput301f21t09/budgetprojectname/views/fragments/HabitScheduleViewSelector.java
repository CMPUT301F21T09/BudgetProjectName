package com.cmput301f21t09.budgetprojectname.views.fragments;

import com.cmput301f21t09.budgetprojectname.models.IHabitScheduleModel;
import com.cmput301f21t09.budgetprojectname.models.IWeeklyHabitScheduleModel;
import com.cmput301f21t09.budgetprojectname.models.WeeklyHabitScheduleModel;

/**
 * View factory for habit schedule models
 */
public class HabitScheduleViewSelector {

    /**
     * Get an instance of a fragment that shows the provided type of view model
     * @param model to represent
     * @param editable if model is editable
     * @return fragment that represents the data in the given model
     */
    public static HabitScheduleFragment getFragmentForModel(IHabitScheduleModel model, boolean editable) {
        if (model instanceof WeeklyHabitScheduleModel)
            return WeeklyHabitScheduleFragment.newInstance((IWeeklyHabitScheduleModel) model, editable);
        else
            throw new IllegalArgumentException("Provided model is not supported");
    }

}
