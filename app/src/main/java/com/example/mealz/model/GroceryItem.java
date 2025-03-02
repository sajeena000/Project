package com.example.mealz.model;

public class GroceryItem {
    private String id;
    private String name;
    private int quantity;
    private boolean isPurchased;

    public GroceryItem() {
        // Required empty constructor for Firebase
    }

    public GroceryItem(String id, String name, int quantity, boolean isPurchased) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.isPurchased = isPurchased;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }
}
