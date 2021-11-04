package com.cmput301f21t09.budgetprojectname.services;

import java.util.function.Function;

/**
 * Manager for a service task
 * @param <T> service task result type
 */
public class ServiceTaskManager<T> {

    /**
     * The task the manager runs
     */
    private final ServiceTask<T> task = new ServiceTask<>();
    /**
     * Whether or not the task has been marked complete
     */
    private boolean isComplete = false;

    /**
     * Create a new service task from an already created task
     *
     * Creates a service task that completes with the same status as the provided task.
     * Calls provided functions and the new task gets the result of the appropriate function call
     *
     * This allows for a task to be forwarded back to another listener while adjusting the data given to the next listener
     * @param task to attach to
     * @param onSuccess function to run if the task is a success, return value is used for result in new task
     * @param onFailure function to run if the task fails, return value is used for exception in new task
     * @param <S> result type of new task
     * @param <T> result type of original task
     * @return new task that complets on old task
     */
    public static <S, T> ServiceTask<S> fromTask(ServiceTask<T> task, Function<T, S> onSuccess, Function<Exception, Exception> onFailure) {
        ServiceTaskManager<S> taskman = new ServiceTaskManager<>();
        task.addTaskCompleteListener(taskPrime -> {
            if (taskPrime.isSuccessful()) {
                taskman.setSuccess(onSuccess.apply(taskPrime.getResult()));
            } else {
                taskman.setFailure(onFailure.apply(taskPrime.getException()));
            }
        });
        return taskman.getTask();
    }

    /**
     * Get the task the manager controls
     * @return controlled task
     */
    public ServiceTask<T> getTask() {
        return task;
    }

    /**
     * Set the success value of the task, completing it
     *
     * Throws if the task is already complete
     * @param result to provide through task
     */
    public void setSuccess(T result) {
        updateCompletionState();
        task.completeAsSuccess(result);
    }

    /**
     * Set the failure value fo the task, completing it
     *
     * Throws if the task is already complete
     * @param e exception to provide through task
     */
    public void setFailure(Exception e) {
        updateCompletionState();
        task.completeAsFailure(e);
    }

    /**
     * Update the completion status of the given task
     * Throws if the task is already complete
     */
    private void updateCompletionState() {
        if (isComplete) throw new IllegalStateException("Unable to change state of task if already complete");
        isComplete = true;
    }
}
