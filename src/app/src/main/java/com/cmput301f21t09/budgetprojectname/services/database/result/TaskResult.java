package com.cmput301f21t09.budgetprojectname.services.database.result;

import androidx.annotation.NonNull;

import com.cmput301f21t09.budgetprojectname.services.database.TaskError;

public interface TaskResult<T> {

    static <S> TaskResult<S> getInstance(S result) {
        return new SuccessfulTaskResult<>(result);
    }

    static <S> TaskResult<S> getInstance(TaskError error) {
        return new FailedTaskResult<>(error);
    }

    boolean isSuccessful();

    @NonNull
    T getResult();

    @NonNull
    TaskError getError();
}
