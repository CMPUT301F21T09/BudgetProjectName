package com.cmput301f21t09.budgetprojectname.services;

/**
 * Listener for getting data from the database backend
 * @param <T> data type
 */
public interface ServiceTaskListener<T> {
    /**
     * Method invoked on a response from the task
     * @param result of task, contains data if any
     */
    void onResponse(ServiceTask<T> result);
}
