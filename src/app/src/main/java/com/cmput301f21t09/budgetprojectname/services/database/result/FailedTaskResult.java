package com.cmput301f21t09.budgetprojectname.services.database.result;

import androidx.annotation.NonNull;

import com.cmput301f21t09.budgetprojectname.services.database.TaskError;

public class FailedTaskResult<T> implements TaskResult<T> {
    private final TaskError error;

    FailedTaskResult(TaskError error) {
        this.error = error;
    }

    public boolean isSuccessful() {
        return false;
    }

    @NonNull
    public T getResult() {
        throw new IllegalStateException("Cannot call get result on task that did not complete successfully");
    }

    @NonNull
    public TaskError getError() {
        return error;
    }
}
