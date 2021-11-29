package com.cmput301f21t09.budgetprojectname.services;

import com.cmput301f21t09.budgetprojectname.models.UserModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

/**
 * Authorization service. Handles user sign in, sign out, and registration.
 */
public class AuthorizationService {
    /**
     * Service
     */
    private static AuthorizationService service;

    /**
     * Firebase auth instance
     */
    private final FirebaseAuth firebaseAuth;

    /**
     * Constructor. Configures and instantiates authorization service.
     */
    private AuthorizationService() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    /**
     * Get authorization service
     *
     * @return Authorization service
     */
    public static AuthorizationService getInstance() {
        if (service == null) {
            service = new AuthorizationService();
        }

        return service;
    }

    /**
     * Sign in with given email and password
     *
     * @param email    The email to sign in with
     * @param password The password to sign in with
     * @return `Task` of `AuthResult` with the result of the sign in attempt
     */
    public Task<AuthResult> signIn(String email, String password) {
        return firebaseAuth.signInWithEmailAndPassword(email, password);
    }

    /**
     * Register with given email and password
     *
     * @param email    The email to register
     * @param password The password to register
     * @return `Task` of `AuthResult` with the result of the registration attempt
     */
    public Task<AuthResult> register(String email, String password, String username,
                                     String firstname, String lastname) {
        return firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Publish user info to Database
                        UserModel user = new UserModel();
                        user.setUsername(username);
                        user.setUID(task.getResult().getUser().getUid());
                        user.setFirstName(firstname);
                        user.setLastName(lastname);
                        user.setSocial(new HashMap<String, Integer>());
                        user.commit();
                    }
                });
    }

    /**
     * Sign out
     */
    public void signOut() {
        firebaseAuth.signOut();
    }

    /**
     * Check if signed in
     */
    public boolean isSignedIn() {
        return firebaseAuth.getCurrentUser() != null;
    }

    /**
     * Get current signed in user's id
     *
     * @return The current signed in user's id. Return empty string if user doesn't exist
     */
    public String getCurrentUserId() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            return currentUser.getUid();
        } else {
            return "";
        }
    }
}
