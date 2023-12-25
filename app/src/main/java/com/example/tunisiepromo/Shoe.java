package com.example.tunisiepromo;

public class Shoe {
    private String name;
    private String imageUrl;
    private String category;
    private String description;
    private String price;
    private String productId;

    private double promotionPercentage; // Corrected field name

    public Shoe() {
    }

    public Shoe(String name, String imageUrl, String category, String description, String price, String productId, double promotionPercentage) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.category = category;
        this.description = description;
        this.price = price;
        this.productId = productId;
        this.promotionPercentage = promotionPercentage; // Added the promotionPercentage field to the constructor
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getPromotionPercentage() {
        return promotionPercentage;
    }

    public void setPromotionPercentage(double promotionPercentage) {
        this.promotionPercentage = promotionPercentage;
    }
}
