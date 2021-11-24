package com.cmput301f21t09.budgetprojectname.models;

import com.cmput301f21t09.budgetprojectname.services.ServiceTask;
import com.cmput301f21t09.budgetprojectname.services.ServiceTaskManager;
import com.cmput301f21t09.budgetprojectname.services.database.CollectionSpecifier;
import com.cmput301f21t09.budgetprojectname.services.database.DatabaseService;
import com.cmput301f21t09.budgetprojectname.services.database.serializers.DocumentModelSerializer;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class UserModel implements IUserModel {

    /**
     * Collection ID for habits as represented in backend
     */
    public static final String USER_COLLECTION_ID = "users";

    /**
     * User's uid as set using the auth provider
     */
    private String uid;

    /**
     * User's username
     */
    private String username;

    /**
     * User's first name
     */
    private String firstname;

    /**
     * User's social details
     * key(user_id): value(0==incoming_request, 1==following, 2==incoming_request and following)
     */
    private HashMap<String, Integer> social;

    /**
     * User's last name
     */
    private String lastname;

    @Override
    public String getUID() {
        return uid;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getFirstName() {
        return firstname;
    }

    @Override
    public String getLastName() {
        return lastname;
    }

    /**
     * Get the Social HashMap
     * @return social HashMap
     */
    @Override
    public HashMap<String, Integer> getSocial() {
        return social;
    }

    /**
     * Set the uid of the model
     * @param uid to set to
     */
    public void setUID(String uid) {
        this.uid = uid;
    }

    /**
     * Set the username of the model
     * @param username to set to
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Set the first name of the model
     * @param firstname to set to
     */
    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Set the last name of the model
     * @param lastname to set to
     */
    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Set the social of the model
     * @param social to set to
     */
    public void setSocial(HashMap<String, Integer> social) {
        this.social = social;
    }

    /**
     * Commit the user model to the database
     *
     * @return task representing status of save task
     */
    public ServiceTask<Void> commit() {
        // TODO: prevent multiple commits at the same time through the same model
        // TODO: throw if editing non-current user
        ServiceTaskManager<Void> tman = new ServiceTaskManager<>();

        HashMap<String, Object> map = new HashMap<>();
        map.put("username", getUsername());
        map.put("firstname", getFirstName());
        map.put("lastname", getLastName());
        map.put("social", getSocial());

        FirebaseFirestore.getInstance().collection(USER_COLLECTION_ID)
                .document(this.uid)
                .set(map)
                .addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                tman.setSuccess(null);
            } else tman.setFailure(task.getException());
        });
        return tman.getTask();
    }
}
