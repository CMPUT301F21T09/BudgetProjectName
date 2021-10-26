package com.cmput301f21t09.budgetprojectname;

import android.media.Image;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

// Copied from Justine's PR

public class HabitEventModel {
    private String name;
    private String location; // TODO: change to Location once this is implemented
    private String description;
    private Date date;
    private Image image;

    HabitEventModel(String name, String location, Date date, String description, Image image) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.date = date;
        this.image = image;
    }

    HabitEventModel(String name, String location, Date date, String description) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.date = date;
    }

    HabitEventModel(String name, String location, Date date, Image image) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.image = image;
    }
}