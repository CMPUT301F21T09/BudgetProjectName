package com.cmput301f21t09.budgetprojectname.models;

import static org.junit.Assert.assertEquals;


import org.junit.Before;
import org.junit.Test;

/**
 * Tests for UserModel operations excluding follow capabilities
 */
public class UserModelTest {

    private static final String DEFAULT_TEST_UID = "342r598iuygf423biunj2wd31q";
    private static final String DEFAULT_TEST_USERNAME = "testme";
    private static final String DEFAULT_TEST_FIRSTNAME = "Luigi";
    private static final String DEFAULT_TEST_LASTNAME = "Mario";

    private UserModel model;

    /**
     * Initializes test users
     */
    @Before
    public void init() {
        model = new UserModel(DEFAULT_TEST_UID, DEFAULT_TEST_USERNAME, DEFAULT_TEST_FIRSTNAME, DEFAULT_TEST_LASTNAME, null);
    }

    /**
     * Tests getting and setting uid
     */
    @Test
    public void testGetSetUID() {
        final String newUID = "g3450o98ijhg349h8ug435";
        assertEquals(DEFAULT_TEST_UID, model.getUID());
        model.setUID(newUID);
        assertEquals(newUID, model.getUID());
    }

    /**
     * Tests getting and setting username
     */
    @Test
    public void testGetSetUsername() {
        final String newUsername = "emanerus";
        assertEquals(DEFAULT_TEST_USERNAME, model.getUsername());
        model.setUsername(newUsername);
        assertEquals(newUsername, model.getUsername());
    }

    /**
     * Tests getting and setting first name
     */
    @Test
    public void testGetSetFirstName() {
        final String newFirstname = "Waluigi";
        assertEquals(DEFAULT_TEST_FIRSTNAME, model.getFirstName());
        model.setFirstName(newFirstname);
        assertEquals(newFirstname, model.getFirstName());
    }

    /**
     * Tests getting and setting last name
     */
    @Test
    public void testGetSetLastName() {
        final String newLastname = "Wario";
        assertEquals(DEFAULT_TEST_LASTNAME, model.getLastName());
        model.setLastName(newLastname);
        assertEquals(newLastname, model.getLastName());
    }
}