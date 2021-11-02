package com.cmput301f21t09.budgetprojectname.services.database;

import com.google.firebase.firestore.FirebaseFirestoreException;

/**
 * Enum to specify specific database backend errors
 */
public enum TaskError {
    /**
     * Fully unknown error
     */
    UNKNOWN,

    /**
     * Unknown Firebase Firestore specific exception
     */
    FIRESTORE_UNKNOWN;

    /**
     * Return a task error based off a given FirebaseFirestoreException
     * @param e original exception
     * @return error type as enum
     */
    static TaskError fromFirebaseException(Exception e) {
        // TODO
        if (e instanceof FirebaseFirestoreException) {
            return TaskError.FIRESTORE_UNKNOWN;
        }
        return TaskError.UNKNOWN;
    }
}
