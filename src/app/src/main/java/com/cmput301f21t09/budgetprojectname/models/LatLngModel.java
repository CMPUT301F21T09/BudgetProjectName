package com.cmput301f21t09.budgetprojectname.models;

/**
 * Custom Latitude Longitude Class to make HabitEventModelClass serializable
 */
public class LatLngModel {

    /**
     * Latitude of location
     */
    private Double latitude;

    /**
     * Longitude of location
     */
    private Double longitude;

    /**
     * Empty Constructor for Firestore
     */
    public LatLngModel() {

    }

    /**
     * Constructor
     *
     * @param latitude  latitude of location
     * @param longitude longitude of location
     */
    public LatLngModel(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Get the latitude
     *
     * @return latitude of the location
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * Set the latitude of the location
     *
     * @param latitude of the location
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * Get the longitude of the location
     *
     * @return longitude of the location
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * Set the longitude of the location
     *
     * @param longitude of the location
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * Set the latitude and the longitude of the location
     *
     * @param latitude  of the location
     * @param longitude of the location
     */
    public void setLocation(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
