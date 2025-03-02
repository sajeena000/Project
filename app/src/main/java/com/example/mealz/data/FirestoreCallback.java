package com.example.mealz.data;

public interface FirestoreCallback<T> {
    void onResult(T result);
}
