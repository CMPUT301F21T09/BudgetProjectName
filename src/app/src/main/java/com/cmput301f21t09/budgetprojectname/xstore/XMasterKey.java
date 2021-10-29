package com.cmput301f21t09.budgetprojectname.xstore;

import java.util.Objects;

/**
 * Key for tracking the state of an immutable object
 * @param <T> Data Type - Should be immutable
 */
public class XMasterKey<T> extends XKey<T>{

    /**
     * Internal state
     */
    private T state;

    @Override
    protected T getState() {
        return state;
    }

    @Override
    protected boolean onUpdate(T newState) {
        // Update state if the new state is different from the old one
        if (Objects.equals(state, newState)) return false;

        state = newState;
        return true;
    }
}
