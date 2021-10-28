package com.cmput301f21t09.budgetprojectname.services.database;

import com.google.firebase.database.Query;

/**
 * A Readable Query from a database
 * Accepts Query Descriptors
 */
public class DatabaseQuery {

    /**
     * Firebase query this references
     */
    private final Query query;

    /**
     * Create a new database query that references the given defined query
     * @param query to point to
     */
    DatabaseQuery(Query query) {
        this.query = query;
    }

    /**
     * Apply a description to the current DatabaseItem query
     * @param descriptor to apply
     * @return newly described query
     */
    public DatabaseQuery apply(QueryDescriptor descriptor) {
        return new DatabaseQuery(descriptor.describe(query));
    }

    public void onResponse() {

    }
}
