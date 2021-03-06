package com.cmput301f21t09.budgetprojectname.models;

import com.google.firebase.firestore.DocumentId;

import java.util.Date;

/**
 * Represents the habit event model containing fields like habit event ID, location, comment, date,
 * image, and habit ID
 */
public class HabitEventModel {

    /**
     * HabitEvent ID
     */
    @DocumentId
    private String ID;

    /**
     * Location of habit event completion (optional)
     */
    private LatLngModel location;

    /**
     * Comment of habit event (optional)
     */
    private String comment;

    /**
     * Date of habit event completion
     */
    private Date date;

    /**
     * Encoded Image string of habit event (optional)
     */
    private String image;

    /**
     * Habit ID to reference completed habit
     */
    private String habitID;

    /**
     * Constructor for creating a habitevent model
     *
     * @param ID       id of habitevent
     * @param location location of habitevent completion (optional)
     * @param date     completion date of habitevent
     * @param comment  comment for habitevent (optional)
     * @param image    image for habitevent (optional)
     * @param habitID  ID of habit
     */
    public HabitEventModel(String ID, LatLngModel location, Date date, String comment, String image, String habitID) {
        this.ID = ID;
        this.location = location;
        this.comment = comment;
        this.date = date;
        this.image = image;
        this.habitID = habitID;
    }

    // need to declare an empty constructor for serialization purposes
    public HabitEventModel() {
    }

    /**
     * Get the id of the habitevent
     *
     * @return habitevent id
     */
    public String getID() {
        return ID;
    }

    /**
     * Sets the id of the habitevent
     *
     * @param ID
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Get the location of habitevent completion
     *
     * @return location
     */
    public LatLngModel getLocation() {
        return location;
    }

    /**
     * Sets the location of the habitevent
     *
     * @param location of habit event completion
     */
    public void setLocation(LatLngModel location) {
        this.location = location;
    }

    /**
     * Get the comment of the habitevent
     *
     * @return habitevent comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the comment of the habitevent
     *
     * @param comment of habit event
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Gets the date of habitevent completion
     *
     * @return date of completion
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date of the habitevent completion
     *
     * @param date of habit event
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets the image of habitevent
     *
     * @return
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the image of habitevent
     *
     * @param image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Gets the habitid of habit the event is referencing
     *
     * @return
     */
    public String getHabitID() {
        return habitID;
    }

    /**
     * Sets the id of habit
     *
     * @param habitID to set as habitid
     */
    public void setHabitID(String habitID) {
        this.habitID = habitID;

    }

}