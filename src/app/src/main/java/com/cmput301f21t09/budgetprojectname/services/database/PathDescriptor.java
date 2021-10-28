package com.cmput301f21t09.budgetprojectname.services.database;

import com.google.firebase.database.DatabaseReference;

/**
 * Descriptor for a new item that is located at the current DatabaseItem's child at a given path
 */
public final class PathDescriptor implements LocationDescriptor {

    /**
     * Path to new query
     */
    private final String path;

    /**
     * Constructor to create a PathDescriptor to locate a child element at the given path
     * @param path to follow to child element
     */
    public PathDescriptor(String path) {
        this.path = path;
    }

    @Override
    public DatabaseReference describe(DatabaseReference reference) {
        return reference.child(path);
    }
}
