package com.cmput301f21t09.budgetprojectname.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cmput301f21t09.budgetprojectname.MainActivity;
import com.cmput301f21t09.budgetprojectname.R;
import com.cmput301f21t09.budgetprojectname.services.AuthorizationService;

/**
 * Responsible for user registration
 */
public class UserRegisterActivity extends AppCompatActivity {
    /**
     * Minimum password character length
     */
    private static final int MINIMUM_PASSWORD_LENGTH = 8;

    /**
     * First name text field
     */
    private EditText firstNameEditText;

    /**
     * Last name text field
     */
    private EditText lastNameEditText;

    /**
     * Email text field
     */
    private EditText emailEditText;

    /**
     * Username text field
     */
    private EditText usernameEditText;

    /**
     * Password text field
     */
    private EditText passwordEditText;

    /**
     * Back button (go back to login screen)
     */
    private Button backButton;

    /**
     * Create account button
     */
    private Button createButton;

    /**
     * Progress indicator
     */
    private ProgressBar progressIndicator;

    /**
     * Class for handling registration input
     */
    public static class RegistrationData {
        /**
         * First name
         */
        private final String firstName;

        /**
         * Last name
         */
        private final String lastName;

        /**
         * Email
         */
        private final String email;

        /**
         * Username
         */
        private final String username;

        /**
         * Password
         */
        private final String password;

        /**
         * Constructor
         *
         * @param firstName First name
         * @param lastName  Last name
         * @param email     Email address
         * @param username  Unique username
         * @param password  Password
         */
        RegistrationData(String firstName, String lastName, String email, String username,
                         String password) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.username = username;
            this.password = password;
        }
    }

    /**
     * Verify registration input.
     * Make notification on fields that have been filled out incorrectly.
     *
     * @param input The user's input for registration
     * @return Whether the input is valid
     */
    private boolean verify(RegistrationData input) {
        boolean valid = true;

        // Check first name
        if (input.firstName.isEmpty()) {
            firstNameEditText.setError("Enter a valid first name.");
            valid = false;
        }

        // Check last name
        if (input.lastName.isEmpty()) {
            lastNameEditText.setError("Enter a valid last name.");
            valid = false;
        }

        // Check username
        if (input.username.isEmpty()) {
            usernameEditText.setError("Enter a valid username.");
            valid = false;
        } // TODO: Check their username is not taken

        // Check email format
        if (!Patterns.EMAIL_ADDRESS.matcher(input.email).matches()) {
            emailEditText.setError("Enter a valid email.");
            valid = false;
        }

        // Check if they entered password and password length
        if (input.password.isEmpty()) {
            passwordEditText.setError("Enter a password.");
            valid = false;
        } else if (input.password.length() < MINIMUM_PASSWORD_LENGTH) {
            passwordEditText.setError("Password must be at least 8 characters.");
            valid = false;
        }

        return valid;
    }

    /**
     * Generate a user in the database with the given input.
     * Input should be verified
     *
     * @param input The input
     */
    private void generateUser(RegistrationData input) {
        // TODO: Create a new user in Firebase
    }

    /**
     * Attempt to create an account with what the user has currently entered in the fields
     */
    private void attemptCreateAccount() {
        // Grab the input from the text fields
        RegistrationData input = new RegistrationData(
                firstNameEditText.getText().toString().trim(),
                lastNameEditText.getText().toString().trim(),
                emailEditText.getText().toString().trim(),
                usernameEditText.getText().toString().trim(),
                passwordEditText.getText().toString().trim());

        // Verify the registration input
        if (!verify(input)) {
            return;
        }

        // Loading indicator while verifying sign up
        progressIndicator.setVisibility(View.VISIBLE);

        // Register with Firebase
        AuthorizationService.getInstance().register(input.email, input.password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Tell user account creation was successful
                Toast.makeText(UserRegisterActivity.this, "Account creation successful",
                        Toast.LENGTH_SHORT).show();

                // Generate the account in Firebase
                generateUser(input);

                // Bring to home screen
                startActivity(new Intent(this, MainActivity.class));
            } else {
                // Tell user account creation failed, along with error message
                if (task.getException() != null) {
                    Toast.makeText(UserRegisterActivity.this,
                            "Account creation failed: " + task.getException().getMessage(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserRegisterActivity.this,
                            "Account creation failed: Unknown error",
                            Toast.LENGTH_SHORT).show();
                }
            }

            // Stop loading indicator
            progressIndicator.setVisibility(View.INVISIBLE);
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        // Get fields
        firstNameEditText = findViewById(R.id.first_name_edittext);
        lastNameEditText = findViewById(R.id.last_name_edittext);
        emailEditText = findViewById(R.id.email_edittext);
        usernameEditText = findViewById(R.id.username_edittext);
        passwordEditText = findViewById(R.id.password_edittext);

        // Get buttons
        backButton = findViewById(R.id.back_button);
        createButton = findViewById(R.id.create_button);

        // Get progress indicator
        progressIndicator = findViewById(R.id.progress_circular);

        // Back to Sign In Screen
        backButton.setOnClickListener(v -> finish());

        // Go through register process
        createButton.setOnClickListener(v -> attemptCreateAccount());
    }
}
