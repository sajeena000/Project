package com.example.mealz.presenter.mealplan;

import com.example.mealz.model.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public interface MealPlanPresenter {
    void getPlannedMeals();

    void getUsername();

    void deletePlanMeal(long networkId, String userId, long date);

    void deleteFromFirebase(Meal meal);
}
