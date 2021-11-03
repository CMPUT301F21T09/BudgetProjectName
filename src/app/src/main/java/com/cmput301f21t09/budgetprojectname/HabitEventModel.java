package com.cmput301f21t09.budgetprojectname;

import android.graphics.Bitmap;
import android.media.Image;

import java.util.Date;

public class HabitEventModel {
    private final String ID; // TODO: change type depending on Firebase ID type
    private final String location; // TODO: change to Location once this is implemented
    private final String description;
    private final Date date;
    private final Bitmap image;

    HabitEventModel(String location, Date date, String description, Bitmap image) {
        this.ID = "s1w9kwequ290"; // placeholder ID
        this.location = location;
        this.description = description;
        this.date = date;
        this.image = image;
    }

    HabitEventModel(String location, Date date, String description) {
        this.ID = "s1w9kwequ290"; // placeholder ID
        this.location = location;
        this.description = description;
        this.date = date;
        image = null;
    }

    HabitEventModel(String location, Date date, Bitmap image) {
        this.ID = "s1w9kwequ290"; // placeholder ID
        this.location = location;
        this.date = date;
        this.image = image;
        this.description = null;
    }

    public String getID() {
        return ID;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public Bitmap getImage() {
        return image;
    }

}