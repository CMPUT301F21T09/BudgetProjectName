package com.cmput301f21t09.budgetprojectname.controllers;

/**
 * Base Controller Implementation
 */
public abstract class BaseController {
    /**
     * Callback to run on model state change
     */
    private Runnable r;

    /**
     * Attach a listener to run on model state change
     * @param r callback to run
     */
    public void attachListener(Runnable r) {
        this.r = r;
    }

    /**
     * Notify listener of model state change
     */
    protected void notifyListener() {
        if (r != null) r.run();
    }
}
