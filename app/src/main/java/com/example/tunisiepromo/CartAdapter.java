package com.example.tunisiepromo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItems;
    private CartItemClickListener cartItemClickListener;
    public CartAdapter(List<CartItem> cartItems, CartItemClickListener cartItemClickListener) {
        this.cartItems = cartItems;
        this.cartItemClickListener = cartItemClickListener;
    }

    public CartAdapter(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
        return new CartViewHolder(view);
    }
    public interface CartItemClickListener {
        void onRemoveItemClick(int position);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        holder.bind(cartItem);

        // Set up click listener for the remove button
        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the remove button click
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    cartItemClickListener.onRemoveItemClick(adapterPosition);
                }
            }
        });
    }

    private void removeFromCart(int position) {
        // Get the CartItem instance to be removed
        CartItem removedItem = cartItems.get(position);

        // Remove the item from the cart
        ShoppingCartManager.removeFromCart(removedItem);

        // Notify the adapter that an item has been removed
        notifyItemRemoved(position);

        // Update the total price in the activity
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        double totalPrice = ShoppingCartManager.calculateTotalPrice();
        // Notify your activity or fragment to update the UI with the new total price
        // For example, you can use an interface callback or an EventBus
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImageView;
        private TextView productNameTextView;
        private TextView quantityTextView;
        private TextView priceTextView;
        private Button removeButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.productImageView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            removeButton = itemView.findViewById(R.id.removeButton); // Add this line to initialize removeButton
        }

        public void bind(CartItem cartItem) {
            // Load the product image
            Glide.with(itemView.getContext())
                    .load(cartItem.getShoe().getImage_url())
                    .into(productImageView);

            productNameTextView.setText(cartItem.getShoe().getName());
            quantityTextView.setText("Quantity: " + cartItem.getQuantity());
            priceTextView.setText("Price: $" + cartItem.getPrice());
        }
    }
}