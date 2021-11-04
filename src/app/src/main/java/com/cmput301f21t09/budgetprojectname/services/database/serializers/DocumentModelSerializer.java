package com.cmput301f21t09.budgetprojectname.services.database.serializers;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;

/**
 * Document to/from Model Serializer interface
 *
 * Handles serializing and deserializing data to and from Firestore friendly formats
 *
 * @param <T> model type to serialize/deserialize
 */
public interface DocumentModelSerializer<T> {

    /**
     * Returns an instance of the DocumentModelSerializer that uses the given parser
     * @param mapParser parser to use for serialization/deserialization
     * @param <S> type of model to serialize/deserialize
     * @return instance of DocumentModelSerializer
     */
    static <S> DocumentModelSerializer<S> getInstance(ModelMapParser<S> mapParser) {
        return new MappedModelSerializer<>(mapParser);
    }

    /**
     * Parse an object from the database that represents a date to a date object
     * @param dateObject object to covert to date
     * @return date representation of object
     */
    static Date parseAsDate(Object dateObject) {

        return ((Timestamp)dateObject).toDate();
    }

    /**
     * Serialize a given model to a Firestore friendly object
     * @param model to serialize
     * @return serialized data
     */
    Object serialize(T model);

    /**
     * Deserializes a given document to a model
     * @param documentSnapshot Firestore Document to deserialize
     * @return deserialized model
     */
    T deserialize(DocumentSnapshot documentSnapshot);
}
