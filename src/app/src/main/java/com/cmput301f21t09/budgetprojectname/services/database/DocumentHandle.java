package com.cmput301f21t09.budgetprojectname.services.database;

import androidx.annotation.NonNull;

import com.cmput301f21t09.budgetprojectname.services.database.serializers.DocumentModelSerializer;
import com.cmput301f21t09.budgetprojectname.services.database.serializers.ModelMapParser;
import com.cmput301f21t09.budgetprojectname.services.database.result.TaskResult;
import com.google.firebase.firestore.DocumentReference;

/**
 * Handle for accessing and writing to a given document
 * @param <T> base class the document represents
 */
public final class DocumentHandle<T> {

    /**
     * Document reference this entry points to
     */
    private final DocumentReference docRef;

    /**
     * Constructor to create a DocumentHandle that points to a given document in the backend
     * @param docRef document reference the handle points to
     */
    DocumentHandle(@NonNull DocumentReference docRef) {
        this.docRef = docRef;
    }

    /**
     * Retrieve a given document's data as parsed using a provided ModelMapParser
     * @param listener to invoke on task completion
     * @param parser to use for deserialization
     * @param <S> type of model data represents
     */
    public <S extends T> void retrieve(TaskListener<S> listener, ModelMapParser<S> parser) {
        registerGetListener(listener, DocumentModelSerializer.getInstance(parser));
    }

    /**
     * Retrieve a given document's data as parsed using the class definition to deserialize
     * @param listener to invoke on task completion
     * @param tClass class definition to use to deserialize
     * @param <S> type of model data represents
     */
    public <S extends T> void retrieve(TaskListener<S> listener, Class<S> tClass) {
        registerGetListener(listener, DocumentModelSerializer.getInstance(tClass));
    }

    /**
     * Listen to updates on a given document's data as parsed using a provided ModelMapParser
     * @param listener to invoke on task completion
     * @param parser to use for deserialization
     * @param <S> type of model data represents
     */
    public <S extends T> void listen(TaskListener<S> listener, ModelMapParser<S> parser) {
        registerSnapshotListener(listener, DocumentModelSerializer.getInstance(parser));
    }

    /**
     * Listen to updates on a given document's data as parsed using the class definition to deserialize
     * @param listener to invoke on task completion
     * @param tClass class definition to use to deserialize
     * @param <S> type of model data represents
     */
    public <S extends T> void listen(TaskListener<S> listener, Class<S> tClass) {
        registerSnapshotListener(listener, DocumentModelSerializer.getInstance(tClass));
    }

    /**
     * Registers a listener for a "get" operation on the document
     * @param listener to invoke on task completion
     * @param serializer to use for document deserialization
     * @param <S> type of model data represents
     */
    private <S extends T> void registerGetListener(TaskListener<S> listener, DocumentModelSerializer<S> serializer) {
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                S data = serializer.deserialize(task.getResult());
                listener.onResponse(TaskResult.getInstance(data));
            } else {
                listener.onResponse(TaskResult.getInstance(TaskError.fromFirebaseException(task.getException())));
            }
        });
    }

    /**
     * Registers a listener for a "snapshot update" operation on the document
     * @param listener to invoke on task completion
     * @param serializer to use for document deserialization
     * @param <S> type of model data represents
     */
    private <S extends T> void registerSnapshotListener(TaskListener<S> listener, DocumentModelSerializer<S> serializer) {
        docRef.addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                listener.onResponse(TaskResult.getInstance(TaskError.fromFirebaseException(e)));
            } else {
                S data = serializer.deserialize(snapshot);
                listener.onResponse(TaskResult.getInstance(data));
            }
        });
    }
}
