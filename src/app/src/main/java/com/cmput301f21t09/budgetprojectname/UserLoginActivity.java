package com.cmput301f21t09.budgetprojectname;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class UserLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        // Open UserRegisterActivity
        Button signup = findViewById(R.id.sign_up_button);
        signup.setOnClickListener(v -> startActivity(new Intent(this, UserRegisterActivity.class)));

        // Go Through Sign In Process
        Button signin = findViewById(R.id.sign_in_button);
        signin.setOnClickListener(v -> {
            // TODO: Sign In Process
        });
    }
}