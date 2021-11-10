package com.cmput301f21t09.budgetprojectname.controllers;

public abstract class BaseController {
    private Runnable r;

    public void attachListener(Runnable r) {
        this.r = r;
    }

    protected void notifyListener() {
        if (r != null) r.run();
    }
}
