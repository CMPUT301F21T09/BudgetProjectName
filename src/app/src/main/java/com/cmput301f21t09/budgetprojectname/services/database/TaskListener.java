package com.cmput301f21t09.budgetprojectname.services.database;

import com.cmput301f21t09.budgetprojectname.services.database.result.TaskResult;

/**
 * Listener for getting data from the database backend
 * @param <T> data type
 */
public interface TaskListener<T> {
    /**
     * Method invoked on a response from the task
     * @param result of task, contains data if any
     */
    void onResponse(TaskResult<T> result);
}
