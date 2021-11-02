package com.cmput301f21t09.budgetprojectname.services.database.result;

import androidx.annotation.NonNull;

import com.cmput301f21t09.budgetprojectname.services.database.TaskError;

public class SuccessfulTaskResult<T> implements TaskResult<T> {
    private final T result;

    SuccessfulTaskResult(T result) {
        this.result = result;
    }

    public boolean isSuccessful() {
        return true;
    }

    @NonNull
    public T getResult() {
        return result;
    }

    @NonNull
    public TaskError getError() {
        throw new IllegalStateException("Cannot call get error on task that completed successfully");
    }
}
