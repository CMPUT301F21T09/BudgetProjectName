package com.cmput301f21t09.budgetprojectname.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for ServiceTask and ServiceTaskManager implementations
 */
public class ServiceTaskManagerTest {

    private static final String DEFAULT_TASK_RESULT = "WHY DID IT SPAWN THERE?";

    private boolean onTaskComplete1;
    private boolean onTaskComplete2;
    private ServiceTaskManager<String> manager;

    @Before
    public void setUp() {
        onTaskComplete1 = false;
        onTaskComplete2 = false;
        manager = new ServiceTaskManager<>();
    }

    /**
     * Tests setting the success state of the task
     */
    @Test
    public void testSetSuccess() {
        ServiceTask<String> task = manager.getTask();
        assertFalse(task.isSuccessful());
        manager.setSuccess(DEFAULT_TASK_RESULT);
        assertTrue(task.isSuccessful());
        assertEquals(DEFAULT_TASK_RESULT, task.getResult());
    }

    /**
     * Tests setting the failure state of a task
     */
    @Test
    public void testSetFailure() {
        Exception e = new Exception();
        ServiceTask<String> task = manager.getTask();
        assertFalse(task.isFailure());
        manager.setFailure(e);
        assertTrue(task.isFailure());
        assertEquals(e, task.getException());
    }

    /**
     * Tests notifying a single listener
     */
    @Test
    public void testSingleListener() {
        ServiceTask<String> task = manager.getTask();
        task.addTaskCompleteListener(this::updateCond1);
        assertFalse(onTaskComplete1);
        manager.setSuccess(DEFAULT_TASK_RESULT);
        assertTrue(onTaskComplete1);
    }

    /**
     * Tests notifying multiple listeners
     */
    @Test
    public void testMultipleListeners() {
        ServiceTask<String> task = manager.getTask();
        task.addTaskCompleteListener(this::updateCond1);
        task.addTaskCompleteListener(this::updateCond2);
        assertFalse(onTaskComplete1);
        assertFalse(onTaskComplete2);
        manager.setSuccess(DEFAULT_TASK_RESULT);
        assertTrue(onTaskComplete1);
        assertTrue(onTaskComplete2);
    }

    /**
     * Tests removeListener functionality
     */
    @Test
    public void testRemoveListener() {
        ServiceTask<String> task = manager.getTask();
        ServiceTaskListener<String> uc1 = this::updateCond1;
        ServiceTaskListener<String> uc2 = this::updateCond2;
        task.addTaskCompleteListener(uc1);
        task.addTaskCompleteListener(uc2);
        assertFalse(onTaskComplete1);
        assertFalse(onTaskComplete2);
        task.removeTaskCompleteListener(uc1);
        manager.setSuccess(DEFAULT_TASK_RESULT);
        assertFalse(onTaskComplete1);
        assertTrue(onTaskComplete2);
    }

    private void updateCond1(ServiceTask<String> task) {
        onTaskComplete1 = true;
    }

    private void updateCond2(ServiceTask<String> task) {
        onTaskComplete2 = true;
    }


}