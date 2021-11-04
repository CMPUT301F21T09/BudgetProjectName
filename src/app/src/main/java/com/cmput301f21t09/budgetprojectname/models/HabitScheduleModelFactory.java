package com.cmput301f21t09.budgetprojectname.models;

import static com.cmput301f21t09.budgetprojectname.models.WeeklyHabitScheduleModel.WEEKLY_HABIT_SCHEDULE_IDENTIFIER;

import java.util.Map;

public class HabitScheduleModelFactory {

    /**
     * Public Constructor for creating a new instance of the HabitScheduleFactory
     */
    public HabitScheduleModelFactory() {}

    /**
     * Returns an instance of the model from the provided data
     * @param data to parse
     * @return instance of model based on provided data
     */
    IHabitScheduleModel getModelInstanceFromData(Map<String, Object> data) {
        if (data.containsKey(WEEKLY_HABIT_SCHEDULE_IDENTIFIER)) {
            return WeeklyHabitScheduleModel.parseMap(((Map<String, Object>) data.get(WEEKLY_HABIT_SCHEDULE_IDENTIFIER)));
        } else {
            throw new IllegalArgumentException("Unknown identifier for Habit Schedule data");
        }

    }

    /**
     * Returns an empty (default) instance of the weekly habit schedule
     * @return weekly habit schedule
     */
    IHabitScheduleModel getNewModelInstance() {
        return new WeeklyHabitScheduleModel();
    }
}
