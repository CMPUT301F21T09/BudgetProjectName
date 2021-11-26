package com.cmput301f21t09.budgetprojectname.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Test class for AuthorizationService.
 * Verifies sign in, sign out, and registration are working.
 */
public class AuthorizationServiceTest {
    /**
     * Initialized authorization service instance
     */
    private AuthorizationService authInstance;

    /**
     * Firebase auth instance (needed to delete account after test registration)
     */
    private FirebaseAuth firebaseAuth;

    /**
     * Email of the registered testing account
     */
    private final String testEmail = "123@123.123";

    /**
     * Password of the registered testing account
     */
    private final String testPassword = "123123123";

    /**
     * UID of the registered testing account
     */
    private final String testUID = "5H201HHnUefzI5JV9dIG586kWfg1";

    /**
     * Countdown latch
     */
    private CountDownLatch lock = new CountDownLatch(1);

    /**
     * Set up
     */
    @Before
    public void init() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        authInstance = AuthorizationService.getInstance();
    }

    /**
     * Make sure authorization is being initialized correctly
     */
    @Test
    public void testServiceNotNull() {
        assertNotNull("Auth not initialized correctly", authInstance);
    }

    /**
     * Test that sign in is working with a dummy account
     */
    @Test
    public void testSignIn() throws InterruptedException {
        authInstance.signIn(testEmail, testPassword).addOnCompleteListener(task -> {
            assertTrue("Sign in failed", task.isSuccessful());
        });

        // Check that user is actually signed in
        lock.await(2, TimeUnit.SECONDS);
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        assertNotNull("Sign in succeeded, but current user is null", currentUser);
    }

    /**
     * Test that registration is working
     */
    @Test
    public void testRegister() throws InterruptedException {
        String testRegisterEmail = "abc@aba.abc";
        String testRegisterPassword = "abcabcabc";
        String testUsername = "abc";
        String testFirstName = "Jane";
        String testLastName = "Doe";

        authInstance.register(testRegisterEmail, testRegisterPassword, testUsername, testFirstName, testLastName).addOnCompleteListener(task -> {
            assertTrue("Register failed", task.isSuccessful());
        });

        lock.await(2, TimeUnit.SECONDS);

        // Check user is loaded and delete user after
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        assertNotNull("Register succeeded, but current user is null", currentUser);
        currentUser.delete();
    }

    /**
     * Test signing out
     */
    @Test
    public void testSignOut() throws InterruptedException {
        // Sign in, then sign out
        authInstance.signIn(testEmail, testPassword).addOnCompleteListener(task -> {
            assertTrue("Sign in failed", task.isSuccessful());
        });

        // Sign out
        lock.await(2, TimeUnit.SECONDS);
        authInstance.signOut();
        assertNull("Init sign out failed", firebaseAuth.getCurrentUser());
    }

    /**
     * Test getting current signed in user's id
     */
    @Test
    public void testGetCurrentUserId() throws InterruptedException {
        // Sign in, then test user id
        authInstance.signIn(testEmail, testPassword).addOnCompleteListener(task -> {
            assertTrue("Sign in failed", task.isSuccessful());
        });

        lock.await(2, TimeUnit.SECONDS);
        assertEquals(testUID, authInstance.getCurrentUserId());
    }

    /**
     * Test getting current signed in user's id returns empty string if no user signed in
     */
    @Test
    public void testGetCurrentUserIdWithNoUser() {
        authInstance.signOut();
        assertEquals("", authInstance.getCurrentUserId());
    }
}
