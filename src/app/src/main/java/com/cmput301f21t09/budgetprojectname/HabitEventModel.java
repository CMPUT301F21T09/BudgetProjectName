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
    /**
     * HabitEvent ID
     */
    private String ID;

    /**
     * Location of habit event completion (optional)
     */
    private String location; // TODO: change to Location type once this is implemented

    /**
     * Comment of habit event (optional)
     */
    private String comment;

    /**
     * Date of habit event completion
     */
    private Date date;

    /**
     * Image of habit event (optional)
     */
    private Image image;

    /**
     * Habit ID to reference completed habit
     */
    private String habitID;

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

    /**
     * Get the id of the habitevent
     * @return habitevent id
     */
    public String getID(){
        return ID;
    }

    /**
     * Sets the id of the habitevent
     * @param ID
     */
    public void setID(String ID){
        this.ID = ID;
    }

    /**
     * Get the location of habitevent completion
     * @return location
     */
    public String getLocation(){
        return location;
    }

    /**
     * Sets the location of the habitevent
     * @param location of habit event completion
     */
    public void setLocation(String location){
        this.location = location;
    }

    /**
     * Get the comment of the habitevent
     * @return habitevent comment
     */
    public String getComment(){
        return comment;
    }

    /**
     * Sets the comment of the habitevent
     * @param comment of habit event
     */
    public void setComment(String comment){
        this.comment = comment;
    }

    /**
     * Gets the date of habitevent completion
     * @return date of completion
     */
    public Date getDate(){
        return date;
    }

    /**
     * Sets the date of the habitevent completion
     * @param date of habit event
     */
    public void setDate(Date date){
        this.date = date;
    }

    /**
     * Gets the image of habitevent
     * @return
     */
    public Image getImage(){
        return image;
    }

    /**
     * Sets the image of habitevent
     * @param image to set
     */
    public void setImage(Image image){
        this.image = image;
    }

    /**
     * Gets the habitid of habit the event is referencing
     * @return
     */
    public String getHabitID() {
        return habitID;
    }

    /**
     * Sets the id of habit
     * @param id to set as habitid
     */
    public void setImage(String id){
        this.habitID = id;
    }

}