package com.example.mealz.data;

import android.util.Log;

import com.example.mealz.model.GroceryItem;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class GroceryRepository {
    private static final String TAG = "Firestore";
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference groceryRef = db.collection("grocery_list");

    // ✅ Add Grocery Item to Firestore
    public void addGroceryItem(GroceryItem item, FirestoreCallback<Boolean> callback) {
        groceryRef.add(item)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "Item Added: " + item.getName());
                    callback.onResult(true);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to add item", e);
                    callback.onResult(false);
                });
    }

    // ✅ Retrieve Grocery Items from Firestore
    public void getGroceryItems(FirestoreCallback<List<GroceryItem>> callback) {
        groceryRef.get().addOnCompleteListener(task -> {
            List<GroceryItem> items = new ArrayList<>();
            if (task.isSuccessful() && task.getResult() != null) {
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    GroceryItem item = doc.toObject(GroceryItem.class);
                    item.setId(doc.getId());
                    items.add(item);
                    Log.d(TAG, "Fetched Item: " + item.getName() + ", Quantity: " + item.getQuantity());
                }
                Log.d(TAG, "Total Items Loaded: " + items.size());
            } else {
                Log.e(TAG, "Failed to load items", task.getException());
            }
            callback.onResult(items);
        });
    }

    // ✅ Update Grocery Item in Firestore
    public void updateGroceryItem(GroceryItem item, FirestoreCallback<Boolean> callback) {
        if (item.getId() == null) {
            Log.e(TAG, "Update Failed: Item ID is null");
            callback.onResult(false);
            return;
        }
        groceryRef.document(item.getId()).set(item)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Item Updated: " + item.getName());
                    callback.onResult(true);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to update item", e);
                    callback.onResult(false);
                });
    }

    // ✅ Delete Grocery Item from Firestore
    public void deleteGroceryItem(String id, FirestoreCallback<Boolean> callback) {
        if (id == null) {
            Log.e(TAG, "Delete Failed: Item ID is null");
            callback.onResult(false);
            return;
        }
        groceryRef.document(id).delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Item Deleted: " + id);
                    callback.onResult(true);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to delete item", e);
                    callback.onResult(false);
                });
    }
}
