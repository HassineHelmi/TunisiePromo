package com.example.tunisiepromo;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoadingPage extends AppCompatActivity {

        @Override
        protected void onCreate(android.os.Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.welcomepage);
            Button button1 = findViewById(R.id.loginButton);
            Button button2 = findViewById(R.id.createAccountButton);

            //setting click listener for the buttons
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start LoginActivity
                    startActivity(new Intent(LoadingPage.this, LoginPage.class));
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start CreateAccountActivity
                    startActivity(new Intent(LoadingPage.this, SignupPage.class));
                }
            });
        }



}
