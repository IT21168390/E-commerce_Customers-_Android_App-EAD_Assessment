package com.example.e_commercecustomers_ead.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.e_commercecustomers_ead.R;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout emailInputLayout, passwordInputLayout;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        emailInputLayout = findViewById(R.id.email_input);
        passwordInputLayout = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_button);

        // Handle login button click
        loginButton.setOnClickListener(view -> {
            String email = emailInputLayout.getEditText().getText().toString();
            String password = passwordInputLayout.getEditText().getText().toString();

            if (validateInputs(email, password)) {
                // Proceed with login (e.g., API call)
                Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateInputs(String email, String password) {
        boolean valid = true;

        // Validate email
        if (email.isEmpty()) {
            emailInputLayout.setError("Email is required");
            valid = false;
        } else {
            emailInputLayout.setError(null);
        }

        // Validate password
        if (password.isEmpty()) {
            passwordInputLayout.setError("Password is required");
            valid = false;
        } else {
            passwordInputLayout.setError(null);
        }

        return valid;
    }
}