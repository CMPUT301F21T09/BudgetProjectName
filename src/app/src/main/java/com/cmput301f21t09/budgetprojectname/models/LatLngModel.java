package com.cmput301f21t09.budgetprojectname.models;

/**
 * Custom Latitude Longitude Class to make HabitEventModelClass serializable
 */
public class LatLngModel {
    private Double latitude;
    private Double longitude;

    public LatLngModel() {

    }

    public LatLngModel(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


}
