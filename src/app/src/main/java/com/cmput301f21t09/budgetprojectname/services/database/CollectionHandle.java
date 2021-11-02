package com.cmput301f21t09.budgetprojectname.services.database;

import com.google.firebase.firestore.CollectionReference;

/**
 * Handle for accessing documents and querying a specified collection
 * @param <T> base class the documents in this collection represent
 */
public class CollectionHandle<T> {

    /**
     * Backend reference of the collection this is a handle for
     */
    private final CollectionReference cRef;

    /**
     * Constructor for collection handle that points to the given collection
     * @param cRef collection to point to
     */
    CollectionHandle(CollectionReference cRef) {
        this.cRef = cRef;
    }

    /**
     * Get handle for a specific entry in the collection
     * @param id entry unique identifier
     * @return handle for accessing the specified entry's data
     */
    DocumentHandle<T> getEntry(String id) {
        return new DocumentHandle<>(cRef.document(id));
    }
}
