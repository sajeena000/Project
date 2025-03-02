package com.example.mealz.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealz.R;
import com.example.mealz.model.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private List<Recipe> recipeList;
    private OnRecipeClickListener listener;

    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

    public RecipeAdapter(List<Recipe> recipeList, OnRecipeClickListener listener) {
        this.recipeList = recipeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.recipeName.setText(recipe.getName());

        // Load image with Glide
        Glide.with(holder.itemView.getContext()).load(recipe.getImageUrl()).into(holder.recipeImage);

        holder.itemView.setOnClickListener(v -> listener.onRecipeClick(recipe));
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView recipeName;
        ImageView recipeImage;

        public ViewHolder(View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipeName);
            recipeImage = itemView.findViewById(R.id.recipeImage);
        }
    }
}
