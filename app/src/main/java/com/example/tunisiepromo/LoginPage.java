package com.example.tunisiepromo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Get references to the text fields and buttons
        EditText emailEditText = findViewById(R.id.emailfield);
        EditText passwordEditText = findViewById(R.id.passwordfield);
        Button loginButton = findViewById(R.id.loginButton);

        // Set up click listener for the login button
        loginButton.setOnClickListener(view -> {
            // Get user input from email and password fields
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // Perform input validation if needed

            // Use Firebase Authentication service for login
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // User login successful
                            user = mAuth.getCurrentUser();
                            Toast.makeText(LoginPage.this, "Authentication success.", Toast.LENGTH_SHORT).show();

                            // Add code to execute after successful login
                            // Redirect to home page
                            startActivity(new Intent(LoginPage.this, HomePage.class));
                        } else {

                        }
                    });
        });
    }
}
