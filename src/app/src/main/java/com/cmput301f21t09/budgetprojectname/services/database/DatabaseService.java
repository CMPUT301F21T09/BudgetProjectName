package com.cmput301f21t09.budgetprojectname.services.database;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Service for accessing database backend
 *
 * This service accomplishes two things:
 * 1. Abstraction of the database backend
 * 2. Enforce a moderate structure of the database
 *
 * DO NOT USE IN GLOBAL SCOPE.
 * A new instance of this service should be created only if being used within an activity.
 * (Or an object that is only to be used within an activity or fragment)
 */
public class DatabaseService {
    /**
     * Firebase backend instance
     */
    private final FirebaseFirestore backend = FirebaseFirestore.getInstance();

    /**
     * Get an instance of the database service
     * @return instance of database service
     */
    public static DatabaseService getInstance() {
        return new DatabaseService();
    }

    /**
     * Private constructor
     */
    private DatabaseService() {}

    /**
     * Get a handle for a given collection
     * @param collection specifier for the given collection
     * @param <T> base data type the collection holds
     * @return handle that points to the specified collection
     */
    public <T> CollectionHandle<T> getCollection(@NonNull CollectionSpecifier<T> collection) {
        return new CollectionHandle<>(backend.collection(collection.getId()));
    }
}
