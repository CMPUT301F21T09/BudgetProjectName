package com.cmput301f21t09.budgetprojectname.services.database;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;

/**
 * A Readable and Writable Database Location
 * Accepts Item and Query Descriptors
 */
public class DatabaseLocation extends DatabaseQuery{

    /**
     * Firebase DatabaseReference this item points to
     */
    private final DatabaseReference reference;

    /**
     * Create a new database location that points to a given reference in the database
     * @param reference to point to
     */
    DatabaseLocation(@NonNull DatabaseReference reference) {
        super(reference);
        this.reference = reference;
    }

    /**
     * Apply a description to the location
     * @param descriptor to apply
     * @return newly described location
     */
    public DatabaseLocation apply(LocationDescriptor descriptor) {
        return new DatabaseLocation(descriptor.describe(reference));
    }

    /**
     * Set the value of the item at the given location
     * @param o value to set
     */
    public void setItem(Object o) {
        reference.setValue(o);
    }



}
