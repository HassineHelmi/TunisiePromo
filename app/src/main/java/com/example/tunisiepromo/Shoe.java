package com.example.tunisiepromo;

import java.io.Serializable;
import java.util.List;

public class Shoe  implements Serializable {
    private String shoeId;
    private String Name;
    private String description;
    private String image_url;  // Ensure that the field names match the keys in the JSON data
    private double priceAP;
    private double priceBP;
    private int promotionPercentage;
    private List<Integer> size;
    private String category;

    public Shoe() {
    }

    public Shoe(String shoeId, String name, String description, String imageUrl, double priceBP, double priceAP, int promotionPercentage, List<Integer> size, String category) {
        this.shoeId = shoeId;
        this.Name = name;
        this.description = description;
        this.image_url = imageUrl;
        this.priceAP = priceAP;
        this.priceBP = priceBP;
        this.promotionPercentage = promotionPercentage;
        this.size = size;
        this.category = category;
    }

    public String getShoeId() {
        return shoeId;
    }

    public void setShoeId(String shoeId) {
        this.shoeId = shoeId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImageUrl(String imageUrl) {
        this.image_url = imageUrl;
    }

    public double getPriceAP() {
        return priceAP;
    }

    public void setPriceAP(double priceAP) {
        this.priceAP = priceAP;
    }

    public double getPriceBP() {
        return priceBP;
    }

    public void setPriceBP(double priceBP) {
        this.priceBP = priceBP;
    }

    public int getPromotionPercentage() {
        return promotionPercentage;
    }

    public void setPromotionPercentage(int promotionPercentage) {
        this.promotionPercentage = promotionPercentage;
    }

    public List<Integer> getSize() {
        return size;
    }

    public void setSize(List<Integer> size) {
        this.size = size;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
