package com.cmput301f21t09.budgetprojectname.models;

import java.util.Date;

public interface IHabitModel {

    String getID();

    String getTitle();

    String getReason();

    int getStreak();

    Date getStartDate();

    Date getLastCompleted();

}
