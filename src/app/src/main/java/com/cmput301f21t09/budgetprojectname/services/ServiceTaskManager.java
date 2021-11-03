package com.cmput301f21t09.budgetprojectname.services;

import java.util.function.Function;

public class ServiceTaskManager<T> {

    private final ServiceTask<T> task = new ServiceTask<>();
    private boolean isComplete = false;

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

    public ServiceTask<T> getTask() {
        return task;
    }

    public void setSuccess(T result) {
        updateCompletionState();
        task.completeAsSuccess(result);
    }

    public void setFailure(Exception e) {
        updateCompletionState();
        task.completeAsFailure(e);
    }

    private void updateCompletionState() {
        if (isComplete) throw new IllegalStateException("Unable to change state of task if already complete");
        isComplete = true;
    }
}
