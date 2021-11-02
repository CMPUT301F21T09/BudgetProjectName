package com.cmput301f21t09.budgetprojectname.services.database.serializers;

import com.google.firebase.firestore.DocumentSnapshot;

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
     * Returns an instance of the DocumentModelSerializer that uses the given class definition
     * @param tClass class definition to use for serialization/deserialization
     * @param <S> type of model to serialize/deserialize
     * @return instance of DocumentModelSerializer
     */
    static <S> DocumentModelSerializer<S> getInstance(Class<S> tClass) {
        return new ModelClassSerializer<>(tClass);
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
