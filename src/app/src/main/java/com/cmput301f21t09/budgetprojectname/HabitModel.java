package com.cmput301f21t09.budgetprojectname;
import java.util.Date;

//TODO: Add Frequency
// TODO: DEPRICATED USE com.cmput301f21t09.budgetprojectname.models.HabitModel instead!
/**
 * Class for a habit
 */
@Deprecated
public class HabitModel {
    final private String title;
    final private String ID;
    final private String reason;
    final private int streak;
    final private Date startDate;
    final private Date lastCompleted;

    /**
     * Constructor for HabitModel.
     * @param ID a unique identifier for the habit
     * @param title the name of the habit (max 20 char)
     * @param reason a reason for the habit (max 30 char)
     * @param startDate the date the habit will start
     * @param lastCompleted when the habit has last been completed
     * @param streak a counter of the amount of times the habit has been completed
     */
    HabitModel(String ID, String title, String reason, Date startDate, Date lastCompleted, int streak) {
        this.title = title;
        this.reason = reason;
        this.startDate = startDate;
        this.ID = ID;
        this.lastCompleted = lastCompleted;
        this.streak = streak;
    }

    /**
     * A method to return the title of the habit
     * @return The title of the habit
     */
    String getTitle() {
        return this.title;
    }

    /**
     * A method to return the reason of the habit
     * @return The habit reason
     */
    String getReason() {
        return this.reason;
    }

    /**
     * A method to return the streak counter for the habit
     * @return The streak counter
     */
    int getStreak() {
        return this.streak;
    }

    /**
     * A method to return the start date of the habit
     * @return The start date of the habit
     */
    Date getStartDate() {
        return this.startDate;
    }

    /**
     * A method to return the ID of the habit
     * @return A unique ID for the habit
     */
    String getID() {
        return this.ID;
    }

    /**
     * A method to return the last completed date of the habit
     * @return The last completed date for the habit
     */
    Date getLastCompleted() {
        return this.lastCompleted;
    }
}
