package com.cmput301f21t09.budgetprojectname.models;

import static org.junit.Assert.assertEquals;


import org.junit.Before;
import org.junit.Test;

/**
 * Tests for UserModel operations excluding follow capabilities
 */
public class LatLngModelTest {

    private static final Double DEFAULT_TEST_LATITUDE = 458.235;
    private static final Double DEFAULT_TEST_LONGITUDE = 43689.8;

    private LatLngModel model;

    /**
     * Initializes test users
     */
    @Before
    public void init() {
        model = new LatLngModel(DEFAULT_TEST_LATITUDE, DEFAULT_TEST_LONGITUDE);
    }

    /**
     * Tests getting and setting latitude
     */
    @Test
    public void testGetSetLatitude() {
        final Double newLatitude = 876590.67534;
        assertEquals(DEFAULT_TEST_LATITUDE, model.getLatitude());
        model.setLatitude(newLatitude);
        assertEquals(newLatitude, model.getLatitude());
    }

    /**
     * Tests getting and setting longitude
     */
    @Test
    public void testGetSetLongitude() {
        final Double newLongitude = 89435.6452;
        assertEquals(DEFAULT_TEST_LONGITUDE, model.getLongitude());
        model.setLongitude(newLongitude);
        assertEquals(newLongitude, model.getLongitude());
    }

}