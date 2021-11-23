package com.cmput301f21t09.budgetprojectname.models;

import java.util.HashMap;

public interface IUserModel {

    String getUID();
    String getUsername();
    String getFirstName();
    String getLastName();
    HashMap<String, Integer> getSocial();
    
}
