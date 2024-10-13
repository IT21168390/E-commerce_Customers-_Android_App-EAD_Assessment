package com.example.e_commercecustomers_ead.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commercecustomers_ead.R;
import com.example.e_commercecustomers_ead.api_models.Address;
import com.example.e_commercecustomers_ead.models.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPassword, editTextStreet, editTextCity, editTextZipCode;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize UI elements
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextStreet = findViewById(R.id.editTextStreet);
        editTextCity = findViewById(R.id.editTextCity);
        editTextZipCode = findViewById(R.id.editTextZipCode);
        buttonRegister = findViewById(R.id.buttonRegister);

        // Handle button click
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        // Retrieve data from input fields
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String street = editTextStreet.getText().toString().trim();
        String city = editTextCity.getText().toString().trim();
        String zipCode = editTextZipCode.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)
                || TextUtils.isEmpty(street) || TextUtils.isEmpty(city) || TextUtils.isEmpty(zipCode)) {
            Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create the Address object
        Address address = new Address(street, city, zipCode);

        // Create the User object
        User user = new User(name, email, password, address);

        // Simulate registration (in real case, send the user data to the server)
        performRegistration(user);
    }

    private void performRegistration(User user) {
        // Here you can call your API to register the user
        // Example: call your backend API with the user data

        // Simulate success message
        Toast.makeText(RegisterActivity.this, "User registered successfully!", Toast.LENGTH_SHORT).show();

        // Optionally: Clear the fields after successful registration
        clearFields();
    }

    private void clearFields() {
        editTextName.setText("");
        editTextEmail.setText("");
        editTextPassword.setText("");
        editTextStreet.setText("");
        editTextCity.setText("");
        editTextZipCode.setText("");
    }
}
