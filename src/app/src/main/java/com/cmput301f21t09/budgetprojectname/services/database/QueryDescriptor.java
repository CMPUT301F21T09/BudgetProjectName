package com.cmput301f21t09.budgetprojectname.services.database;

import com.google.firebase.database.Query;

/**
 * Template for a description of a new query
 */
interface QueryDescriptor {

    /**
     * Return a query with the added description
     * @param query the query to add description to
     * @return query with added description
     */
    Query describe(Query query);
}
