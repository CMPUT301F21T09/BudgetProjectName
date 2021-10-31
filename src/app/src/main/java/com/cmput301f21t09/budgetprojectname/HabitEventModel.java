package com.cmput301f21t09.budgetprojectname;

import android.media.Image;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

public class HabitEventModel {
    private final String ID; // TODO: change type depending on Firebase ID type
    private final String name;
    private final String location; // TODO: change to Location once this is implemented
    private final String description;
    private final Date date;
    private final Image image;

    HabitEventModel(String name, String location, Date date, String description, Image image) {
        this.ID = "s1w9kwequ290"; // placeholder ID
        this.name = name;
        this.location = location;
        this.description = description;
        this.date = date;
        this.image = image;
    }

    HabitEventModel(String name, String location, Date date, String description) {
        this.ID = "s1w9kwequ290"; // placeholder ID
        this.name = name;
        this.location = location;
        this.description = description;
        this.date = date;
        image = null;
    }

    HabitEventModel(String name, String location, Date date, Image image) {
        this.ID = "s1w9kwequ290"; // placeholder ID
        this.name = name;
        this.location = location;
        this.date = date;
        this.image = image;
        this.description = null;
    }

}