package com.cmput301f21t09.budgetprojectname;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
    private FirebaseAuth firebaseAuth;

    /**
     * Constructor. Configures and instantiates authorization service.
     */
    private AuthorizationService() {
        // Get firebase authentication service
        firebaseAuth = FirebaseAuth.getInstance();
    }

    /**
     * Get authorization service
     * @return Authorization service
     */
    public static AuthorizationService getInstance() {
        if (service == null) {
            service = new AuthorizationService();
        }

        return service;
    }

    /**
     * Sign in
     */
//    public Task<AuthResult> signIn() {
//        // TODO
//        return;
//    }

    /**
     * Register
     */
    public void register() {
        // TODO
    }

    /**
     * Sign out
     */
    public void signOut() {
        firebaseAuth.signOut();
    }
}
