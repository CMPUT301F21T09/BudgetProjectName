package com.cmput301f21t09.budgetprojectname.services;

import java.util.HashSet;
import java.util.Set;

public class ServiceTask<T> {

    private ServiceTaskState state = ServiceTaskState.RUNNING;
    private Set<ServiceTaskListener<T>> listeners = new HashSet<>();
    private T result;
    private Exception e;

    void completeAsSuccess(T result) {
        this.result = result;
        this.state = ServiceTaskState.SUCCESS;
        notifyListeners();
    }

    void completeAsFailure(Exception e) {
        this.e = e;
        this.state = ServiceTaskState.FAILED;
        notifyListeners();
    }

    public T getResult() {
        if (state != ServiceTaskState.SUCCESS) throw new IllegalStateException("Cannot get result on unsuccessful task");
        return result;
    }

    public Exception getException() {
        if (state != ServiceTaskState.FAILED) throw new IllegalStateException("Cannot get exception of non-failed task");
        return e;
    }

    public boolean isComplete() {
        return state != ServiceTaskState.RUNNING;
    }

    public boolean isSuccessful() {
        return state == ServiceTaskState.SUCCESS;
    }

    public boolean isFailure() {
        return state == ServiceTaskState.FAILED;
    }

    public void addTaskCompleteListener(ServiceTaskListener<T> listener) {
        if (isComplete()) listener.onResponse(this);
        listeners.add(listener);
    }

    public void removeTaskCompleteListener(ServiceTaskListener<T> listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        listeners.forEach(listener -> listener.onResponse(this));
    }

}

enum ServiceTaskState {
    RUNNING,
    SUCCESS,
    FAILED
}
