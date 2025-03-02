package com.example.mealz.view.recipes;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.mealz.R;
import com.example.mealz.data.RecipeRepository;
import com.example.mealz.model.Recipe;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateRecipeDialog extends DialogFragment {
    private EditText recipeNameInput, ingredientsInput, instructionsInput;
    private Button saveButton, cancelButton;
    private RecipeRepository recipeRepository;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(requireContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_create_recipe, null);
        dialog.setContentView(view);

        // Initialize Views
        recipeNameInput = view.findViewById(R.id.recipeNameInput);
        ingredientsInput = view.findViewById(R.id.ingredientsInput);
        instructionsInput = view.findViewById(R.id.instructionsInput);
        saveButton = view.findViewById(R.id.saveButton);
        cancelButton = view.findViewById(R.id.cancelButton);
        recipeRepository = new RecipeRepository();

        // Button Listeners
        saveButton.setOnClickListener(v -> saveRecipe());
        cancelButton.setOnClickListener(v -> dismiss());

        return dialog;
    }

    private void saveRecipe() {
        String name = recipeNameInput.getText().toString().trim();
        String ingredientsText = ingredientsInput.getText().toString().trim();
        String instructions = instructionsInput.getText().toString().trim();

        if (name.isEmpty() || ingredientsText.isEmpty() || instructions.isEmpty()) {
            Log.e("CreateRecipeDialog", "Empty fields detected! Recipe cannot be added.");
            Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("CreateRecipeDialog", "Creating recipe: " + name);

        Recipe newRecipe = new Recipe(null, name, Arrays.asList(ingredientsText.split(",")), instructions, "");

        recipeRepository.addRecipe(newRecipe, success -> {
            if (success) {
                Log.d("CreateRecipeDialog", "Recipe successfully added to Firestore!");
                dismiss();
            } else {
                Log.e("CreateRecipeDialog", "Failed to add recipe to Firestore!");
                Toast.makeText(getContext(), "Failed to add recipe", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

