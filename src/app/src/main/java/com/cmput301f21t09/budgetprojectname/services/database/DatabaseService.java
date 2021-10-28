package com.cmput301f21t09.budgetprojectname.services.database;

import com.google.firebase.database.FirebaseDatabase;

public class DatabaseService {

    private static final FirebaseDatabase db = FirebaseDatabase.getInstance();

    /**
     * Return a DatabaseLocation pointing to the root of the database
     * @return location of database root
     */
    private static DatabaseLocation getDatabaseRoot() {
        return new DatabaseLocation(db.getReference());
    }
}
