package com.cmput301f21t09.budgetprojectname;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Handles sign-in page
 */
public class UserLoginActivity extends AppCompatActivity {
    /**
     * Email text field
     */
    private EditText emailEditText;

    /**
     * Password text field
     */
    private EditText passwordEditText;

    /**
     * Sign up button (for if user wants to register)
     */
    private Button signUpButton;

    /**
     * Sign in button
     */
    private Button signInButton;

    /**
     * Progress indicator
     */
    private ProgressBar progressIndicator;

    /**
     * Firebase auth instance
     */
    private FirebaseAuth firebaseAuth;

    /**
     * Verify login input.
     * Make notification on fields that have been filled out incorrectly.
     *
     * @param email The email to check
     * @param password The password to check
     * @return Whether the input is valid
     */
    private boolean verify(String email, String password) {
        boolean valid = true;

        // Check email format
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Enter a valid email.");
            valid = false;
        }

        // Check if they entered password
        if (password.isEmpty()) {
            passwordEditText.setError("Enter a password.");
            valid = false;
        }

        return valid;
    }

    /**
     * Attempt to sign in and go to the main activity
     */
    private void attemptSignIn() {
        // Get current value of fields
        String emailText = emailEditText.getText().toString().trim();
        String passwordText = passwordEditText.getText().toString().trim();

        // Verify input
        if (!verify(emailText, passwordText)) {
            return;
        }

        // Loading indicator while verifying sign in
        progressIndicator.setVisibility(View.VISIBLE);

        // Verify sign in info
        firebaseAuth.signInWithEmailAndPassword(emailText, passwordText) .addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Tell user account creation was successful
                Toast.makeText(UserLoginActivity.this, "Sign in successful.",
                        Toast.LENGTH_SHORT).show();

                // Bring to home screen
                startActivity(new Intent(this, MainActivity.class));
            } else {
                // Tell user account creation failed, along with error message
                Toast.makeText(UserLoginActivity.this,
                        "Sign in failed: " + task.getException().getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Stop loading indicator
        progressIndicator.setVisibility(View.INVISIBLE);
    }

    /**
     * Checks if user is already signed into app.
     * Starts main activity if already signed in.
     */
    private void checkIfSignedIn() {
        // Loading indicator while determining if user is signed in
        progressIndicator.setVisibility(View.VISIBLE);

        // Check if user already signed in
        if (firebaseAuth.getCurrentUser() != null) {
            // Go to main activity if already signed in
            startActivity(new Intent(this, MainActivity.class));
        }

        // Stop loading indicator
        progressIndicator.setVisibility(View.INVISIBLE);
    }

    /**
     * Start the registration activity
     */
    private void openSignUp() {
        startActivity(new Intent(this, UserRegisterActivity.class));
    }

    /**
     * This activity should be the entry point for the application.
     * If Firebase authentication says user is signed in, we can
     * start up the main activity right away.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        // Get fields
        emailEditText = findViewById(R.id.email_edittext);
        passwordEditText = findViewById(R.id.password_edittext);

        // Get progress indicator
        progressIndicator = findViewById(R.id.progress_circular);

        // Get buttons
        signUpButton = findViewById(R.id.sign_up_button);
        signInButton = findViewById(R.id.sign_in_button);

        // Get firebase auth instance
        firebaseAuth = FirebaseAuth.getInstance();

        // Sign up button: open registration activity
        signUpButton.setOnClickListener(v -> openSignUp());

        // Sign in button: attempt to sign in
        signInButton.setOnClickListener(v -> attemptSignIn());

        // Check if already signed in
        checkIfSignedIn();
    }
}