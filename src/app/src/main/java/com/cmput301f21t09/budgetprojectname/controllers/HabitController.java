package com.cmput301f21t09.budgetprojectname.controllers;

import android.util.Log;

import com.cmput301f21t09.budgetprojectname.models.HabitModel;
import com.cmput301f21t09.budgetprojectname.models.IHabitModel;
import com.cmput301f21t09.budgetprojectname.models.IHabitScheduleModel;

import java.util.Date;

/**
 * Controller for views that interact with habits
 */
public class HabitController extends ServiceTaskController<String> {

    /**
     * Key for getting information on the model load task
     */
    public static final String HABIT_MODEL_LOAD = "HABIT_MODEL_LOAD";
    /**
     * Key for getting information on the model save task
     */
    public static final String HABIT_MODEL_SAVE = "HABIT_MODEL_SAVE";

    /**
     * The currently loaded model instance
     */
    private HabitModel model;

    /**
     * Private constructor
     */
    private HabitController() {
    }

    /**
     * Get a habit controller for editing the habit with the given id
     *
     * @param id of habit to edit
     * @return controller for editing habit
     */
    public static HabitController getEditHabitController(String id) {
        HabitController controller = new HabitController();
        controller.registerTask(HABIT_MODEL_LOAD, HabitModel.getInstanceById(id));
        return controller;
    }

    /**
     * Get a habit controller for creating a new habit
     *
     * @return controller for creating new habit
     */
    public static HabitController getCreateHabitController() {
        HabitController controller = new HabitController();
        controller.registerTask(HABIT_MODEL_LOAD, HabitModel.getNewInstance());
        return controller;
    }

    /**
     * Whether or not the start date is editable
     *
     * @return true is the start date is editable
     */
    public boolean isStartDateEditable() {
        return getModel().getLastCompleted() == null;
    }

    /**
     * Checks the given title to see if it complies with the model spec
     *
     * @param title to check
     * @return error code to display if not
     */
    public String getTitleError(String title) {
        // TODO: move this check to the model
        if (title.trim().length() == 0) return "Required";
        if (title.length() > IHabitModel.MAX_TITLE_LENGTH)
            return "Must be under " + IHabitModel.MAX_TITLE_LENGTH + "characters";

        return null;
    }

    /**
     * Update the model with the given data and commit the changes to the backend
     *
     * @param title     to update model to
     * @param reason    to update model to
     * @param startDate to update model to
     */
    public void updateModel(String title, String reason, Date startDate, IHabitScheduleModel schedule) {
        Log.d("HabitController", "Habit Model Save Command issued");

        // Set data
        model.setTitle(title);
        model.setReason(reason);
        model.setStartDate(startDate);
        model.setSchedule(schedule);

        // Commit changes
        registerTask(HABIT_MODEL_SAVE, model.commit());
        notifyListener();
    }

    @Override
    protected void onTaskComplete(String key) {
        if (key.equals(HABIT_MODEL_LOAD)) {
            model = (HabitModel) getResult(HABIT_MODEL_LOAD);
        }
    }

    /**
     * Get the habit model this controller holds
     *
     * @return habit model, null if not loaded/doesn't exist
     */
    public IHabitModel getModel() {
        return isTaskSuccessful(HABIT_MODEL_LOAD) ? model : null;
    }

    /**
     * Whether or not the model is currently being saved
     *
     * @return true if the model is currently being saved
     */
    public boolean isSaving() {
        return hasTask(HABIT_MODEL_SAVE) && !isTaskComplete(HABIT_MODEL_SAVE);
    }

    /**
     * Whether or not the model has been saved
     *
     * @return true if the model has been saved
     */
    public boolean isSaved() {
        return isTaskSuccessful(HABIT_MODEL_SAVE);
    }
}
