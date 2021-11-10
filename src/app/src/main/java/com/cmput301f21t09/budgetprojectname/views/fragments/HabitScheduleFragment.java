package com.cmput301f21t09.budgetprojectname.views.fragments;

import androidx.fragment.app.Fragment;

import com.cmput301f21t09.budgetprojectname.models.IHabitScheduleModel;

/**
 * Base class for all HabitScheduleFragments
 */
public abstract class HabitScheduleFragment extends Fragment {

    /**
     * Get the schedule as represented by the view
     *
     * @return schedule model
     */
    public abstract IHabitScheduleModel getSchedule();
}
