package com.cmput301f21t09.budgetprojectname.services.database;

import com.google.firebase.firestore.FirebaseFirestore;

public class DatabaseService {
    private final FirebaseFirestore backend = FirebaseFirestore.getInstance();

    public static DatabaseService getInstance() {
        return new DatabaseService();
    }

    private DatabaseService() {}

    public <T> CollectionHandle<T> getCollection(CollectionSpecifier<T> collection) {
        return new CollectionHandle<>(backend.collection(collection.getId()));
    }
}
