package com.example.mealz.view.grocerylist;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealz.R;
import com.example.mealz.data.GroceryRepository;
import com.example.mealz.model.GroceryItem;

import java.util.ArrayList;

public class GroceryListFragment extends Fragment {

    private GroceryRepository groceryRepository;
    private RecyclerView recyclerView;
    private GroceryListAdapter adapter;
    private EditText itemName, itemQuantity;
    private Button addButton;

    public GroceryListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grocery_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ✅ Fix: Set the correct title for Grocery List
        requireActivity().setTitle("Grocery List");

        groceryRepository = new GroceryRepository();
        recyclerView = view.findViewById(R.id.recyclerView);
        itemName = view.findViewById(R.id.itemName);
        itemQuantity = view.findViewById(R.id.itemQuantity);
        addButton = view.findViewById(R.id.addButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new GroceryListAdapter(new ArrayList<>(), groceryRepository);
        recyclerView.setAdapter(adapter);

        loadGroceryItems();

        addButton.setOnClickListener(v -> {
            String name = itemName.getText().toString().trim();
            String quantityText = itemQuantity.getText().toString().trim();

            if (!name.isEmpty() && !quantityText.isEmpty()) {
                try {
                    int quantity = Integer.parseInt(quantityText);
                    GroceryItem item = new GroceryItem(null, name, quantity, false);

                    // ✅ Fix: Show success/failure message when adding an item
                    groceryRepository.addGroceryItem(item, success -> {
                        if (success) {
                            Toast.makeText(getContext(), "Item Added!", Toast.LENGTH_SHORT).show();
                            itemName.setText("");  // Clear input fields after adding
                            itemQuantity.setText("");
                        } else {
                            Toast.makeText(getContext(), "Failed to Add Item", Toast.LENGTH_SHORT).show();
                        }
                        loadGroceryItems();
                    });

                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Invalid Quantity", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Please enter item details!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadGroceryItems() {
        groceryRepository.getGroceryItems(items -> {
            adapter.updateList(items);

            // ✅ Fix: Log the data fetched from Firestore
            Log.d("Firestore", "Loaded Grocery Items: " + items.size());
        });
    }
}
