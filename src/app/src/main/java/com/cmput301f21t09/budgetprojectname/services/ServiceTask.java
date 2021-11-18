package com.cmput301f21t09.budgetprojectname.services;

import java.util.HashSet;
import java.util.Set;

/**
 * Task listener for a given task
 *
 * @param <T>
 */
public class ServiceTask<T> {

    /**
     * Current state of the task
     */
    private ServiceTaskState state = ServiceTaskState.RUNNING;
    /**
     * Set of all the provided listeners
     */
    private final Set<ServiceTaskListener<T>> listeners = new HashSet<>();
    /**
     * Result of the task
     */
    private T result;
    /**
     * Exception of failure of task
     */
    private Exception e;

    /**
     * Complete the task with the given result
     * <p>
     * Called by the task's manager
     *
     * @param result of the task
     */
    void completeAsSuccess(T result) {
        this.result = result;
        this.state = ServiceTaskState.SUCCESS;
        notifyListeners();
    }

    /**
     * Complete the task with the given exception
     * <p>
     * Called by the task's manager
     *
     * @param e exception of the task
     */
    void completeAsFailure(Exception e) {
        this.e = e;
        this.state = ServiceTaskState.FAILED;
        notifyListeners();
    }

    /**
     * Return the result of the task
     * <p>
     * Throws if the task is not marked as successful
     *
     * @return task's result
     */
    public T getResult() {
        if (!isSuccessful())
            throw new IllegalStateException("Cannot get result on unsuccessful task");
        return result;
    }

    /**
     * Return the exception that describes why the task failed
     * <p>
     * Throws if the task is not marked as failed
     *
     * @return task's exception
     */
    public Exception getException() {
        if (!isFailure())
            throw new IllegalStateException("Cannot get exception of non-failed task");
        return e;
    }

    /**
     * Whether or not the task has been completed
     *
     * @return true if complete
     */
    public boolean isComplete() {
        return state != ServiceTaskState.RUNNING;
    }

    /**
     * Whether or not the task has been completed successfully
     *
     * @return true if successful
     */
    public boolean isSuccessful() {
        return state == ServiceTaskState.SUCCESS;
    }

    /**
     * Whether or not the task has been completed but did fail
     *
     * @return true if failed
     */
    public boolean isFailure() {
        return state == ServiceTaskState.FAILED;
    }

    /**
     * Add listener to run when task is complete
     *
     * @param listener to run when task is complete
     */
    public void addTaskCompleteListener(ServiceTaskListener<T> listener) {
        if (isComplete()) listener.onResponse(this);
        listeners.add(listener);
    }

    /**
     * Remove listener from the given task
     *
     * @param listener to remove
     */
    public void removeTaskCompleteListener(ServiceTaskListener<T> listener) {
        listeners.remove(listener);
    }

    /**
     * Notifies all the given listeners
     */
    private void notifyListeners() {
        listeners.forEach(listener -> listener.onResponse(this));
    }

}

/**
 * State enums for a service task
 */
enum ServiceTaskState {
    /**
     * Task is currently running
     */
    RUNNING,
    /**
     * Task has completed successfully
     */
    SUCCESS,
    /**
     * Task has completed with exception
     */
    FAILED
}
