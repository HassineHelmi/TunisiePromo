package com.example.tunisiepromo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AddToCartActivity extends AppCompatActivity implements CartAdapter.CartItemClickListener {
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private TextView totalPriceTextView;
    private Button goBackShoppingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        // Initialize UI components
        recyclerView = findViewById(R.id.cartRecyclerView);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        goBackShoppingButton = findViewById(R.id.goBackShoppingButton);

        // Set up RecyclerView and adapter
        cartAdapter = new CartAdapter(ShoppingCartManager.getCartItems(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cartAdapter);

        // Update total price
        updateTotalPrice();

        // Set up Go Back Shopping Button click
        goBackShoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to the homepage
                startActivity(new Intent(AddToCartActivity.this, HomePage.class));
                finish(); // Finish the current activity to prevent coming back to it using the back button
            }
        });
    }

    private void updateTotalPrice() {
        double totalPrice = ShoppingCartManager.calculateTotalPrice();
        totalPriceTextView.setText("Total Price: $" + totalPrice);
    }

    @Override
    public void onRemoveItemClick(int position) {
        // Handle item removal here
        CartItem cartItemToRemove = ShoppingCartManager.getCartItems().get(position);
        ShoppingCartManager.removeFromCart(cartItemToRemove);

        // Update the total price dynamically
        updateTotalPrice();

        // Notify the adapter that an item has been removed
        cartAdapter.notifyItemRemoved(position);
    }
}