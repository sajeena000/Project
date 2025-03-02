package com.example.mealz.view.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealz.R;
import com.example.mealz.data.RecipeRepository;
import com.example.mealz.model.Recipe;
import com.example.mealz.view.adapters.RecipeAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MyRecipesFragment extends Fragment implements RecipeAdapter.OnRecipeClickListener {
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipeList = new ArrayList<>();
    private RecipeRepository recipeRepository;
    private FloatingActionButton addRecipeButton;

    public MyRecipesFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_recipes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // ✅ FIX: Pass `this` as the click listener to the adapter
        recipeAdapter = new RecipeAdapter(recipeList, this);
        recyclerView.setAdapter(recipeAdapter);

        addRecipeButton = view.findViewById(R.id.addRecipeButton);
        addRecipeButton.setOnClickListener(v -> showCreateRecipeDialog());

        recipeRepository = new RecipeRepository();
        loadUserRecipes();
    }

    private void loadUserRecipes() {
        recipeRepository.getUserCreatedRecipes(userRecipes -> {
            recipeList.clear();
            recipeList.addAll(userRecipes);
            recipeAdapter.notifyDataSetChanged();
        });
    }

    private void showCreateRecipeDialog() {
        CreateRecipeDialog dialog = new CreateRecipeDialog();
        dialog.show(getParentFragmentManager(), "CreateRecipeDialog");
    }

    // ✅ FIX: Implement the required `OnRecipeClickListener` method
    @Override
    public void onRecipeClick(Recipe recipe) {
        Toast.makeText(getContext(), "Clicked: " + recipe.getName(), Toast.LENGTH_SHORT).show();
    }
}
