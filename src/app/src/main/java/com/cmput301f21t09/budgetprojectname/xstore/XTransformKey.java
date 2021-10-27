package com.cmput301f21t09.budgetprojectname.xstore;

import androidx.annotation.NonNull;

public class XTransformKey<T, S> extends XKey<T> {

    private final Transform<S, T> transform;
    private T state;

    XTransformKey(@NonNull XKey<S> source, @NonNull Transform<S, T> transform) {
        this.transform = transform;

        XRequest<S> req = source.request();
        assert req != null;
        req.onFulfill(this::handleSourceChange);

        handleSourceChange(req.getState());
    }

    private void handleSourceChange(S source) {
        update(transform.mutate(source));
    }

    @Override
    protected T getState() {
        return state;
    }

    @Override
    protected boolean onUpdate(T newState) {
        if (state.equals(newState)) return false;

        state = newState;
        return true;
    }

    public interface Transform<U, V> {
        V mutate(U u);
    }
}
