package com.example.tunisiepromo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SignupPage extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Get references to the text fields and buttons
        EditText emailEditText = findViewById(R.id.editTextTextEmailAddress);
        EditText passwordEditText = findViewById(R.id.editTextTextPassword);
        EditText firstNameEditText = findViewById(R.id.editTextFirstName);
        EditText lastNameEditText = findViewById(R.id.editTextLastName);
        Spinner spinner = findViewById(R.id.spinner);
        Button signupButton = findViewById(R.id.SignupButton);

        // Set up click listener for the create account button
        signupButton.setOnClickListener(view -> {
            // Get user input from email and password fields
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            // Perform input validation if needed

            // Example input validation for email
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                // Display an error message, the email is not valid
                Toast.makeText(SignupPage.this, "Invalid email address", Toast.LENGTH_SHORT).show();
                return; // Stop further processing
            }

            // Example input validation for password (minimum 6 characters)
            if (password.length() < 6) {
                // Display an error message, the password is too short
                Toast.makeText(SignupPage.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                return; // Stop further processing
            }

            // Use Firebase Authentication service for account creation
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Account creation successful
                            Toast.makeText(SignupPage.this, "Account created successfully", Toast.LENGTH_SHORT).show();

                            // Add code to execute after successful account creation
                            // For example, you might navigate to another activity
                             startActivity(new Intent(SignupPage.this, LoginPage.class));

                        } else {
                            // Account creation failed, display an error message
                            Toast.makeText(SignupPage.this, "Account creation failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        // Add any additional setup or functionality here
    }
}
