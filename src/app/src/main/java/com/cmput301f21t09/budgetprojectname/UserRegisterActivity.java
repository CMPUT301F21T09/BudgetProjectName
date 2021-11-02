package com.cmput301f21t09.budgetprojectname;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class UserRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        // Back to Sign In Screen
        Button back = findViewById(R.id.back_button);
        back.setOnClickListener(v -> {
            finish();
        });

        // GO through register process
        Button create = findViewById(R.id.create_button);
        create.setOnClickListener(v -> {
            // TODO: Register Process
        });
    }
}