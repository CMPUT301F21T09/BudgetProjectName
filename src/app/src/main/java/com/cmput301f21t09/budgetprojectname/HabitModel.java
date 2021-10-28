package com.cmput301f21t09.budgetprojectname;
import java.util.Date;

public class HabitModel {
    private String name;
    private String description;
    private int streak;
    private String[] frequency;
    private Date startDate;
    private Date lastCompleted = null;

    HabitModel(String name, String description, String[] frequency, Date startDate) {
        this.name = name;
        this.description = description;
        this.frequency = frequency;
        this.startDate = startDate;
    }

    String getName() {
        return this.name;
    }

    String getDescription() {
        return this.description;
    }

    int getStreak() {
        return this.streak;
    }

    String[] getFrequency() {
        return this.frequency;
    }

    Date getStartDate() {
        return this.startDate;
    }

    void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    void setName(String name) {
        this.name = name;
    }

    void setDescription(String description) {
        this.description = description;
    }

    void setFrequency(String[] frequency) {
        this.frequency = frequency;
    }

    void setCompleted() {
        this.streak += 1;
        this.lastCompleted = new Date();
    }
}
