package com.cmput301f21t09.budgetprojectname;
import java.util.Date;

//TODO: Add Frequency
public class HabitModel {
    final private String title;
    final private String ID;
    final private String reason;
    final private int streak;
    final private Date startDate;
    final private Date lastCompleted;

    HabitModel(String ID, String title, String reason, Date startDate, Date lastCompleted, int streak) {
        this.title = title;
        this.reason = reason;
        this.startDate = startDate;
        this.ID = ID;
        this.lastCompleted = lastCompleted;
        this.streak = streak;
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
