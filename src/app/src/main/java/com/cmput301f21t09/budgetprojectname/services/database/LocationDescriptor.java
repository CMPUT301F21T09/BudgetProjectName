package com.cmput301f21t09.budgetprojectname.services.database;

import com.google.firebase.database.DatabaseReference;

/**
 * Template for a description of a DatabaseLocation
 */
interface LocationDescriptor {

    /**
     * Return a DatabaseReference with the added description
     * @param reference the reference to add description to
     * @return reference with added description
     */
    DatabaseReference describe(DatabaseReference reference);

}
