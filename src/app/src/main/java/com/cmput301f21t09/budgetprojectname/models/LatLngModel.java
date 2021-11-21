package com.cmput301f21t09.budgetprojectname.models;

import androidx.annotation.Nullable;

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

    public void setLocation(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj.getClass().isInstance(LatLngModel.class)) {
            LatLngModel latLngModel = (LatLngModel) obj;

            return this.getLatitude().equals(latLngModel.getLatitude())
                    && this.getLongitude().equals(latLngModel.getLongitude());
        }

        return false;
    }
}
