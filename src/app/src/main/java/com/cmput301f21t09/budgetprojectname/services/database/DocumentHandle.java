package com.cmput301f21t09.budgetprojectname.services.database;

import androidx.annotation.NonNull;

import com.cmput301f21t09.budgetprojectname.services.ServiceTask;
import com.cmput301f21t09.budgetprojectname.services.ServiceTaskListener;
import com.cmput301f21t09.budgetprojectname.services.ServiceTaskManager;
import com.cmput301f21t09.budgetprojectname.services.database.serializers.DocumentModelSerializer;
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
     * @param serializer to use for document deserialization
     * @param <S> type of model data represents
     */
    public <S extends T> ServiceTask<S> retrieve(DocumentModelSerializer<S> serializer) {
        ServiceTaskManager<S> taskManager = new ServiceTaskManager<>();
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                S data = serializer.deserialize(task.getResult());
                taskManager.setSuccess(data);
            } else {
                taskManager.setFailure(task.getException());
            }
        });
        return taskManager.getTask();
    }

    /**
     * Retrieve a given document's data as parsed using a provided ModelMapParser
     * @param model to serialize and push to database
     * @param serializer to use for document deserialization
     * @param <S> type of model data represents
     */
    public <S extends T> ServiceTask<Void> save(S model, DocumentModelSerializer<S> serializer) {
        ServiceTaskManager<Void> taskManager = new ServiceTaskManager<>();
        docRef.set(serializer.serialize(model)).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                taskManager.setSuccess(null);
            } else {
                taskManager.setFailure(task.getException());
            }
        });
        return taskManager.getTask();
    }

}
