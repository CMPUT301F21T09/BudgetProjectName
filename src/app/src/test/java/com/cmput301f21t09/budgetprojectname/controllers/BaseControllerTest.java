package com.cmput301f21t09.budgetprojectname.controllers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests for Base Controller implemetation
 */
public class BaseControllerTest {

    private boolean testCondition;

    /**
     * Tests creation of a habit
     */
    @Test
    public void testAddNotifyListener() {
        this.testCondition = false;
        BaseController controller = new BaseController() {};

        controller.attachListener(() -> {
            this.testCondition = true;
        });

        assertFalse(this.testCondition);

        controller.notifyListener();

        assertTrue(this.testCondition);
    }
}
