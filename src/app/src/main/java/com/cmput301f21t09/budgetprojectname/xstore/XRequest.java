package com.cmput301f21t09.budgetprojectname.xstore;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.function.Consumer;

/**
 * Request handler for the state of a key
 *
 * Each object that depends on some data should issue its own request to the given key
 * A request is essentially "an ask get the current state and be notified on any state changes"
 *
 * A request is "fulfilled" when it is notified of any state changes,
 * a request can be "fulfilled" multiple times before it is closed.
 *
 * Closing a request prevents it from listening to new state changes
 *
 * @param <T> Type of the State of the Key this is registered to
 */
public class XRequest<T> {
    /**
     * Key the request is registered to
     */
    private final XKey<T> key;
    /**
     * Callback to issue on fulfillment of request
     */
    private Consumer<T> response;

    /**
     * Constructor for request
     * @param key request is registered to
     */
    XRequest(@NonNull XKey<T> key) {
        this.key = key;
    }

    /**
     * Set the response to perform when the request is fulfilled (state has changed)
     * @param response callback on fulfillment
     */
    public void onFulfill(Consumer<T> response) {
        if (this.response != null) throw new IllegalStateException(this.toString() +
                ": Cannot accept new fulfillment response as one already exists\n" +
                "To properly change response behavior close request and open a new one");
        this.response = response;
    }

    /**
     * Get current state that this request listens to
     * @return the current state
     */
    public T getState() {
        return key.getState();
    }

    /**
     * Cancel the request
     *
     * Cancelling ensures that if there is a registered callback
     * the request object will not get notified.
     *
     * NOTE: The request object can still be used to get the state synchronously, though this may change
     */
    public void cancel() {
        key.closeRequest(this);
    }

    /**
     * Called by the key to fulfill the request
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    void fulfill() {
        if (response != null) response.accept(key.getState());
    }
}
