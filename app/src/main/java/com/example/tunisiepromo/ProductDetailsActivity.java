package com.example.tunisiepromo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ProductDetailsActivity extends AppCompatActivity {

    private ImageView productImageView;
    private TextView productNameTextView;
    private TextView productDescriptionTextView;
    private TextView shoeSizesTextView;
    private NumberPicker quantityNumberPicker;
    private Button goBackShoppingButton;
    private Button addToCartButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        // Initialize UI components
        productImageView = findViewById(R.id.productImageView);
        productNameTextView = findViewById(R.id.productNameTextView);
        productDescriptionTextView = findViewById(R.id.productDescriptionTextView);
        shoeSizesTextView = findViewById(R.id.shoeSizesTextView);
        quantityNumberPicker = findViewById(R.id.quantityNumberPicker);
        goBackShoppingButton = findViewById(R.id.goBackShoppingButton);
        addToCartButton = findViewById(R.id.addToCartButton);

        // Retrieve the shoe information from the intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("shoe")) {
            // Retrieve the selected shoe data
            Shoe selectedShoe = (Shoe) intent.getSerializableExtra("shoe");

            // Set up UI elements with the product details
            productNameTextView.setText(selectedShoe.getName());
            productDescriptionTextView.setText(selectedShoe.getDescription());
            shoeSizesTextView.setText("Available Sizes: " + selectedShoe.getSize().toString());

            // Load the image from the URL using Glide
            Glide.with(this)
                    .load(selectedShoe.getImage_url())
                    .into(productImageView);

            // Set up NumberPicker with default value and range
            quantityNumberPicker.setMinValue(1);
            quantityNumberPicker.setMaxValue(10);
            quantityNumberPicker.setValue(1);

            // Handle Go Back Shopping Button click
            goBackShoppingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Finish the current activity and go back to the previous activity (homepage)
                    finish();
                }
            });

            // Handle Add to Cart Button click
            addToCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the selected quantity from the NumberPicker
                    int selectedQuantity = quantityNumberPicker.getValue();

                    // Implement your logic to add the product to the cart with the selected quantity
                    // You can use SharedPreferences, a database, or any other storage method to save the cart information
                    // For demonstration purposes, we'll just print the quantity for now
                    System.out.println("Adding to cart: Quantity = " + selectedQuantity);
                }
            });
        }
    }
}
