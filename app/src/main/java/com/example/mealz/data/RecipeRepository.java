package com.example.mealz.data;

import android.util.Log;
import com.example.mealz.model.Recipe;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class RecipeRepository {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference recipeRef = db.collection("user_recipes");

    // ✅ Add new user-created recipe
    public void addRecipe(Recipe recipe, FirestoreCallback<Boolean> callback) {
        if (recipe == null) {
            Log.e("RecipeRepository", "Recipe object is NULL!");
            callback.onResult(false);
            return;
        }

        Log.d("RecipeRepository", "Attempting to add recipe: " + recipe.getName());

        recipeRef.add(recipe)
                .addOnSuccessListener(documentReference -> {
                    Log.d("RecipeRepository", "Recipe added successfully! ID: " + documentReference.getId());
                    callback.onResult(true);
                })
                .addOnFailureListener(e -> {
                    Log.e("RecipeRepository", "Failed to add recipe: " + e.getMessage(), e);
                    callback.onResult(false);
                });
    }

    // ✅ Retrieve user-created recipes
    public void getUserCreatedRecipes(FirestoreCallback<List<Recipe>> callback) {
        Log.d("RecipeRepository", "Fetching user-created recipes...");

        recipeRef.get().addOnCompleteListener(task -> {
            List<Recipe> userRecipes = new ArrayList<>();
            if (task.isSuccessful() && task.getResult() != null) {
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    Recipe recipe = doc.toObject(Recipe.class);
                    recipe.setId(doc.getId());
                    userRecipes.add(recipe);
                }
                Log.d("RecipeRepository", "Fetched " + userRecipes.size() + " user recipes.");
            } else {
                Log.e("RecipeRepository", "Failed to fetch user recipes!", task.getException());
            }
            callback.onResult(userRecipes);
        });
    }
}
