package com.example.mealz.presenter.mealdetails;

import com.example.mealz.data.backup.BackUpRemoteDataSourceImpl;
import com.example.mealz.model.Ingredient;
import com.example.mealz.model.Meal;
import com.example.mealz.model.NetworkMeal;

import java.util.List;

public interface MealDetailsPresenter {
    void getMealById(long id);
    void insertFavMeal(Meal meal);
    void insertPlanMeal(Meal meal);
    void insertMeal(Meal meal);

    void downloadMealImage(Meal meal);

    void downloadIngredientImages(List<Ingredient> ingredients);

    void isFavMealExist(long networkId);

    void deleteMeal(Meal meal);

    void getUserId();

    void backUp(Meal meal);

    void removeMealFromFavorites(Meal meal);
}
