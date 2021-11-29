package com.cmput301f21t09.budgetprojectname.controllers;

import com.cmput301f21t09.budgetprojectname.services.ServiceTask;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller for tracking multiple service tasks
 *
 * @param <U> key type for distinguishing between service tasks
 */
public abstract class ServiceTaskController<U> extends BaseController {

    /**
     * Map of registered service tasks as organized by the keys
     */
    private final Map<U, ServiceTask<?>> tasks = new HashMap<>();

    /**
     * Run when a given task is complete
     * <p>
     * Meant to be overridden by subclasses if necessary
     *
     * @param key of the task that completed
     */
    protected void onTaskComplete(U key) {
    }

    /**
     * Register a given task to a specified key, replacing a given task if one already exists
     * <p>
     * If a replaced task completes it will not call onTaskComplete of the controller,
     * the previous task is effectively forgotten.
     *
     * @param key  to register task under
     * @param task to register
     */
    protected final void registerTask(U key, ServiceTask<?> task) {
        this.tasks.put(key, task);
        task.addTaskCompleteListener(taskPrime -> {
            this.onTaskUpdate(key, taskPrime);
        });
        notifyListener();
    }

    /**
     * Check if the controller has a task registered under the given key
     *
     * @param key to check
     * @return true if there is a task registered under the key
     */
    protected final boolean hasTask(U key) {
        return tasks.containsKey(key);
    }

    /**
     * Get the result of the task registered under the given key
     * <p>
     * If no task is registered will return null
     *
     * @param key of service task
     * @return result of the service
     */
    protected final Object getResult(U key) {
        return tasks.get(key) != null ? tasks.get(key).getResult() : null;
    }

    /**
     * Get the exception of the task registered under the given key
     * <p>
     * If no task is registered will return null
     *
     * @param key of service task
     * @return exception of the service
     */
    protected final Exception getException(U key) {
        return tasks.get(key) != null ? tasks.get(key).getException() : null;
    }

    /**
     * Gets the completion status of a task registered under a given key
     *
     * @param key of service task
     * @return true if task is complete
     */
    public final boolean isTaskComplete(U key) {
        return tasks.get(key) != null && tasks.get(key).isComplete();
    }

    /**
     * Gets the success status of a task registered under a given key
     *
     * @param key of service task
     * @return true if task is successful
     */
    public final boolean isTaskSuccessful(U key) {
        return tasks.get(key) != null && tasks.get(key).isSuccessful();
    }

    /**
     * Gets the failure status of a task registered under a given key
     *
     * @param key of service task
     * @return true if task has failed
     */
    public final boolean isTaskFailure(U key) {
        return tasks.get(key) != null && tasks.get(key).isFailure();
    }

    /**
     * Listener to provide to tasks
     *
     * @param key  known registration of task
     * @param task actual task registered at key
     */
    private void onTaskUpdate(U key, ServiceTask<?> task) {
        // Check that we are for sure still watching the given task
        if (this.tasks.get(key) == task) {
            onTaskComplete(key);
            notifyListener();
        }
    }
}

