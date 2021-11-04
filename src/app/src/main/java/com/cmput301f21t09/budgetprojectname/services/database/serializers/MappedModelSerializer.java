package com.cmput301f21t09.budgetprojectname.services.database.serializers;

import com.google.firebase.firestore.DocumentSnapshot;

/**
 * Serializer that uses a ModelMapParser to serialize
 * @param <T> model type
 */
class MappedModelSerializer<T> implements DocumentModelSerializer<T> {
    /**
     * Parser to use to serialize and deserialize
     */
    private final ModelMapParser<T> parser;

    /**
     * Constructor that takes in a parser to use for serializing/deserializing
     * @param parser to use for operations
     */
    MappedModelSerializer(ModelMapParser<T> parser) {
        this.parser = parser;
    }

    @Override
    public Object serialize(T model) {
        return parser.parseModel(model);
    }

    @Override
    public T deserialize(DocumentSnapshot documentSnapshot) {
        return parser.parseMap(documentSnapshot.getData(), documentSnapshot.getId());
    }
}
