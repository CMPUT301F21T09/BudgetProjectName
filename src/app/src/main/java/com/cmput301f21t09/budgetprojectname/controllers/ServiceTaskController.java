package com.cmput301f21t09.budgetprojectname.controllers;

import com.cmput301f21t09.budgetprojectname.services.ServiceTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class ServiceTaskController<U> extends BaseController {

    private final Map<U, ServiceTask<?>> tasks = new HashMap<>();

    // Meant to be overridden by subclasses if necessary
    protected void onTaskComplete(U key) {}

    protected final void registerTask(U key, ServiceTask<?> task) {
        this.tasks.put(key, task);
        task.addTaskCompleteListener(taskPrime -> {
            this.onTaskUpdate(key, taskPrime);
        });
        notifyListener();
    }

    protected final boolean hasTask(U key) {
        return tasks.containsKey(key);
    }

    protected final Object getResult(U key) {
        return Objects.requireNonNull(tasks.get(key)).getResult();
    }

    protected final Exception getException(U key) {
        return Objects.requireNonNull(tasks.get(key)).getException();
    }

    public final boolean isTaskComplete(U key) {
        return Objects.requireNonNull(tasks.get(key)).isComplete();
    }

    public final boolean isTaskSuccessful(U key) {
        return Objects.requireNonNull(tasks.get(key)).isSuccessful();
    }

    public final boolean isTaskFailure(U key) {
        return Objects.requireNonNull(tasks.get(key)).isFailure();
    }

    private void onTaskUpdate(U key, ServiceTask<?> task) {
        // Check that we are for sure still watching the given task
        if (this.tasks.get(key) == task) {
            onTaskComplete(key);
            notifyListener();
        }
    }

}

