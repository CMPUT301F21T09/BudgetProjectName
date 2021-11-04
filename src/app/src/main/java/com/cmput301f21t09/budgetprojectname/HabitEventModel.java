package com.cmput301f21t09.budgetprojectname;

import android.media.Image;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents the habit event model containing fields like habit event ID, location, comment, date,
 * image, and habit ID
 */
public class HabitEventModel{
    private final String ID;
    private final String location; // TODO: change to Location once this is implemented
    private final String comment;
    private final Date date;
    private final Image image;
    private final String habitID; // references habit

    HabitEventModel(String location, Date date, String comment, Image image, String habitID) {
        this.ID = "s1w9kwequ290"; // placeholder ID
        this.location = location;
        this.comment = comment;
        this.date = date;
        this.image = image;
        this.habitID = habitID;
    }

    HabitEventModel(String location, Date date, String comment, String habitID) {
        this.ID = "s1w9kwequ290"; // placeholder ID
        this.location = location;
        this.comment = comment;
        this.date = date;
        this.image = null;
        this.habitID = habitID;
    }

    HabitEventModel(String location, Date date, Image image, String habitID) {
        this.ID = "s1w9kwequ290"; // placeholder ID
        this.location = location;
        this.date = date;
        this.image = image;
        this.comment = null;
        this.habitID = habitID;
    }

    HabitEventModel(Date date, String habitID) {
        this.ID = "s1w9kwequ290"; // placeholder ID
        this.location = null;
        this.date = date;
        this.image = null;
        this.comment = null;
        this.habitID = habitID;
    }

    // need to declare an empty constructor for serialization purposes
    HabitEventModel(){
        this.ID = null;
        this.location = null;
        this.comment = null;
        this.date = new Date();
        this.image = null;
        this.habitID = null;
    }

    public String getID(){
        return ID;
    }

    public String getLocation(){
        return location;
    }

    public String getComment(){
        return comment;
    }

    public Date getDate(){
        return date;
    }

    public Image getImage(){
        return image;
    }

    public String getHabitID() { return habitID;}

}