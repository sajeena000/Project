package com.example.mealz.model;

import java.util.List;

public class Recipe {
    private String id;
    private String name;
    private List<String> ingredients;
    private String instructions;
    private String imageUrl;

    // Empty constructor for Firestore
    public Recipe() {}

    public Recipe(String id, String name, List<String> ingredients, String instructions, String imageUrl) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.imageUrl = imageUrl;
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

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
