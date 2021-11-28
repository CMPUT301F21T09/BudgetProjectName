package com.cmput301f21t09.budgetprojectname.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.cmput301f21t09.budgetprojectname.services.ServiceTaskManager;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for Service Task Controller implementation
 */
public class ServiceTaskControllerTest {

    private static final String DEFAULT_TASK_RESULT = "WHY ARE THERE SO MANY BUGS?";
    private static final String DEFAULT_TASK_KEY = "NO DWARF LEFT BEHIND";

    private boolean onTaskCompleteCondition;
    private ServiceTaskManager<String> manager;
    private ServiceTaskController<String> controller;

    @Before
    public void setUp() {
        onTaskCompleteCondition = false;
        manager = new ServiceTaskManager<>();
        controller = new ServiceTaskController<String>() {
            @Override
            protected void onTaskComplete(String key) {
                super.onTaskComplete(key);
                onTaskCompleteCondition = key.equals(DEFAULT_TASK_KEY);
            }
        };
        controller.registerTask(DEFAULT_TASK_KEY, manager.getTask());
    }

    /**
     * Tests has task of registered and not registered task
     */
    @Test
    public void testHasTask() {
        assertTrue(controller.hasTask(DEFAULT_TASK_KEY));
        assertFalse(controller.hasTask(DEFAULT_TASK_KEY + "LIKE AND SUBSCRIBE"));
    }

    /**
     * Tests onTaskComplete
     */
    @Test
    public void testOnTaskComplete() {
        assertFalse(onTaskCompleteCondition);
        manager.setSuccess(DEFAULT_TASK_RESULT);
        assertTrue(onTaskCompleteCondition);
    }

    /**
     * Tests isTaskComplete of unfinished and finished tasks
     */
    @Test
    public void testIsTaskComplete() {
        assertFalse(controller.isTaskComplete(DEFAULT_TASK_KEY));
        manager.setSuccess(DEFAULT_TASK_RESULT);
        assertTrue(controller.isTaskComplete(DEFAULT_TASK_KEY));
    }

    /**
     * Tests isTaskSuccessful of unfinished and finished tasks
     */
    @Test
    public void testIsTaskSuccessful() {
        assertFalse(controller.isTaskSuccessful(DEFAULT_TASK_KEY));
        manager.setSuccess(DEFAULT_TASK_RESULT);
        assertFalse(controller.isTaskFailure(DEFAULT_TASK_KEY));
        assertTrue(controller.isTaskSuccessful(DEFAULT_TASK_KEY));
    }

    /**
     * Tests isTaskFailure of unfinished and finished tasks
     */
    @Test
    public void testIsTaskFailure() {
        assertFalse(controller.isTaskFailure(DEFAULT_TASK_KEY));
        manager.setFailure(new Exception());
        assertFalse(controller.isTaskSuccessful(DEFAULT_TASK_KEY));
        assertTrue(controller.isTaskFailure(DEFAULT_TASK_KEY));
    }

    /**
     * Tests getException of registered and unregistered tasks
     */
    @Test
    public void testGetException() {
        Exception e = new Exception();
        manager.setFailure(e);
        assertEquals(e, controller.getException(DEFAULT_TASK_KEY));
        assertNull(controller.getException("BEEPING BOOPING SOUNDS"));
    }

    /**
     * Tests getResult of registered and unregistered tasks
     */
    @Test
    public void testGetResult() {
        manager.setSuccess(DEFAULT_TASK_RESULT);
        assertEquals(DEFAULT_TASK_RESULT, controller.getResult(DEFAULT_TASK_KEY));
        assertNull(controller.getException("BEEPING BOOPING SOUNDS"));
    }

}
