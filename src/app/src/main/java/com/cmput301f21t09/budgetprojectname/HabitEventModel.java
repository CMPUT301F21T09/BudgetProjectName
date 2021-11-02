package com.cmput301f21t09.budgetprojectname;

import android.media.Image;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Date;

public class HabitEventModel{
    private final String ID; // TODO: change type depending on Firebase ID type
    private final String location; // TODO: change to Location once this is implemented
    private final String comment;
    private final Date date;
    private final Image image;

    HabitEventModel(String location, Date date, String comment, Image image) {
        this.ID = "s1w9kwequ290"; // placeholder ID
        this.location = location;
        this.comment = comment;
        this.date = date;
        this.image = image;
    }

    HabitEventModel(String location, Date date, String comment) {
        this.ID = "s1w9kwequ290"; // placeholder ID
        this.location = location;
        this.comment = comment;
        this.date = date;
        image = null;
    }

    HabitEventModel(String location, Date date, Image image) {
        this.ID = "s1w9kwequ290"; // placeholder ID
        this.location = location;
        this.date = date;
        this.image = image;
        this.comment = null;
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

}
