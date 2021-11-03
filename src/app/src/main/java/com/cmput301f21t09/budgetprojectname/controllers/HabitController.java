package com.cmput301f21t09.budgetprojectname.controllers;

import android.util.Log;

import com.cmput301f21t09.budgetprojectname.models.HabitModel;
import com.cmput301f21t09.budgetprojectname.models.IHabitModel;


public class HabitController extends ServiceTaskController<String> {

    public static final String HABIT_MODEL_LOAD = "HABIT_MODEL_LOAD";
    public static final String HABIT_MODEL_SAVE = "HABIT_MODEL_SAVE";

    private HabitModel model;

    private HabitController() {}

    public static HabitController getEditHabitController(String id) {
        HabitController controller =  new HabitController();
        controller.registerTask(HABIT_MODEL_LOAD, HabitModel.getInstanceById(id));
        return controller;
    }

    public static HabitController getCreateHabitController() {
        HabitController controller =  new HabitController();
        controller.registerTask(HABIT_MODEL_LOAD, HabitModel.getNewInstance());
        return controller;
    }

    public void updateModel(String title, String reason) {
        model.setTitle(title);
        model.setReason(reason);
        registerTask(HABIT_MODEL_LOAD, model.commit());
        Log.d("HabitController", "Habit Model Save Command issued");
        notifyListener();
    }

    @Override
    protected void onTaskComplete(String key) {
        if (key.equals(HABIT_MODEL_LOAD)) {
            model = (HabitModel) getResult(HABIT_MODEL_LOAD);
        }
    }

    public IHabitModel getModel() {
        return isTaskSuccessful(HABIT_MODEL_LOAD) ? model : null;
    }

    public boolean isSaving() {
        return hasTask(HABIT_MODEL_SAVE) && !isTaskComplete(HABIT_MODEL_SAVE);
    }
}
