package com.cmput301f21t09.budgetprojectname.xstore;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import java.util.function.Consumer;

public class XMasterKeyTest {

    @Test
    public void xMasterKey_InitialState_ReturnsCorrectState() {
        XKey<String> key = new XMasterKey<>();
        key.update("testState");

        assertEquals(key.request().getState(), "testState");
    }

    @Test
    public void xMasterKey_StateChanged_ReturnsCorrectState() {
        XKey<String> key = new XMasterKey<>();
        key.update("testState");

        assertEquals(key.request().getState(), "testState");

        key.update("newState");
        assertEquals(key.request().getState(), "newState");
    }

    @Test
    public void xMasterKey_StateChanged_NotifiesListeners() {
        Consumer callback = mock(Consumer.class);

        XKey<String> key = new XMasterKey<>();

        XRequest<String> req = key.request();
        req.onFulfill(callback);

        key.update("testState");

        verify(callback).accept(eq("testState"));
    }
}
