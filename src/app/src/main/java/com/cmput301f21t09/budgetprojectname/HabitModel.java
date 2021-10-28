package com.cmput301f21t09.budgetprojectname;
import java.util.Date;

//TODO: Add Frequency
public class HabitModel {
    private String title;
    private String ID;
    private String reason;
    private int streak;
    private Date startDate;
    private Date lastCompleted;

    HabitModel(String title, String reason, Date startDate) {
        this.title = title;
        this.reason = reason;
        this.startDate = startDate;
        //TODO: Generate ID
    }

    String getTitle() {
        return this.title;
    }

    String getReason() {
        return this.reason;
    }

    int getStreak() {
        return this.streak;
    }

    Date getStartDate() {
        return this.startDate;
    }

    String getID() {
        return this.ID;
    }
}
