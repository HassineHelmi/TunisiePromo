package com.example.tunisiepromo;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartManager {
    private static List<CartItem> cartItems = new ArrayList<>();

    public static void addToCart(Shoe shoe, int quantity) {
        CartItem cartItem = new CartItem(shoe, quantity);
        cartItems.add(cartItem);
    }

    public static List<CartItem> getCartItems() {
        return cartItems;
    }

    public static void removeFromCart(CartItem cartItem) {
        cartItems.remove(cartItem);
    }

    public static double calculateTotalPrice() {
        double totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getShoe().getPriceAP() * cartItem.getQuantity();
        }
        return totalPrice;
    }
}

