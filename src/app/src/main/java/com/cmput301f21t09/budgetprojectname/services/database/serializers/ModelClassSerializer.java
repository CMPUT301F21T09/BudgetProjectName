package com.cmput301f21t09.budgetprojectname.services.database.serializers;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.DocumentSnapshot;

/**
 * Serializer that takes a class definition and uses that to serialize and deserialize documents
 * @param <T> type of model to serialize/deserialize
 */
class ModelClassSerializer<T> implements DocumentModelSerializer<T> {
    /**
     * Class definition of model
     */
    private final Class<T> tClass;

    /**
     * Constructor to get a serializer for a given model type
     * @param tClass class definition of model
     */
    ModelClassSerializer(@NonNull Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public Object serialize(T model) {
        return model;
    }

    @Override
    public T deserialize(DocumentSnapshot documentSnapshot) {
        return documentSnapshot.toObject(tClass);
    }
}
