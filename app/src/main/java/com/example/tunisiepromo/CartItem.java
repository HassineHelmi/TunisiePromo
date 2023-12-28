package com.example.tunisiepromo;

public class CartItem {
    private Shoe shoe;
    private int quantity;
    public CartItem(Shoe shoe, int quantity) {
        this.shoe = shoe;
        this.quantity = quantity;
    }

    public Shoe getShoe() {
        return shoe;
    }

    public void setShoe(Shoe shoe) {
        this.shoe = shoe;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getProductName() {
        return shoe.getName();
    }
    public double getPrice() {
        return shoe.getPriceAP() * quantity;
    }
}