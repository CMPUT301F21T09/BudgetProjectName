package com.cmput301f21t09.budgetprojectname.xstore;

import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.HashSet;
import java.util.Set;

/**
 * Base class for key types
 *
 * Implements base features for encapsulating model data and issueing and updating callback handlers
 * @param <T> Data Model Type
 */
public abstract class XKey<T> {

    /**
     * Data request objects that are currently open
     */
    private final Set<XRequest<T>> openRequests = new HashSet<>();

    /**
     * Get the current key state
     * @return current state
     */
    abstract protected T getState();

    /**
     * To be run when a state update is requested.
     *
     * During this the key should check if the new state is different from the old one
     * and perform any changes necessary to keep track of the new state information
     *
     * If the state was in fact changed the function should return true otherwise open
     * requests will not be provided the new state
     * @param newState The new state provided
     * @return True if state was updated
     */
    abstract protected boolean onUpdate(T newState);

    /**
     * To be run when a request is made
     * @return True if the request should be allowed
     */
    protected boolean onRequest() { return true; };

    /**
     * To be run when a request is closed
     * @return True if the request should be allowed
     */
    protected boolean onRequestClose() { return true; }

    /**
     * Update the state provided by the key
     *
     * @param newState the proposed change in state
     * @return True if the update was successful
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public final boolean update(T newState) {
        if (onUpdate(newState)) {
            openRequests.forEach(XRequest::fulfill);
            return true;
        }
        return false;
    }

    /**
     * Request access to the state
     *
     * @return request object registered to the given key
     */
    @Nullable
    public final XRequest<T> request() {
        if (onRequest()) {
            XRequest<T> newRequest = new XRequest<T>(this);
            openRequests.add(newRequest);
            return newRequest;
        }
        return null;
    }

    /**
     * Close a request that is registered to the key
     *
     * @param request to close
     */
    final void closeRequest(XRequest<T> request) {
        if (openRequests.remove(request)) throw new IllegalArgumentException("Cannot close unregistered request");
    }
}
