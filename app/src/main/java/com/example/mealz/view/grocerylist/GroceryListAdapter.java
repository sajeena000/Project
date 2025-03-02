package com.example.mealz.view.grocerylist;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealz.R;
import com.example.mealz.data.GroceryRepository;
import com.example.mealz.model.GroceryItem;

import java.util.ArrayList;
import java.util.List;

public class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.ViewHolder> {
    private List<GroceryItem> groceryList;
    private GroceryRepository groceryRepository;

    public GroceryListAdapter(List<GroceryItem> groceryList, GroceryRepository groceryRepository) {
        this.groceryList = groceryList != null ? groceryList : new ArrayList<>();
        this.groceryRepository = groceryRepository;
    }

    public void updateList(List<GroceryItem> newList) {
        this.groceryList = newList != null ? newList : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grocery, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GroceryItem item = groceryList.get(position);
        holder.itemName.setText(item.getName());
        holder.itemQuantity.setText("Qty: " + item.getQuantity());
        holder.checkBox.setChecked(item.isPurchased());

        // ✅ Mark item as purchased & Show Toast Message
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.setPurchased(isChecked);
            if (isChecked) {
                Toast.makeText(holder.itemView.getContext(), "Purchased item", Toast.LENGTH_SHORT).show();
            }

            groceryRepository.updateGroceryItem(item, success -> {
                if (success) {
                    notifyItemChanged(position);
                } else {
                    Toast.makeText(holder.itemView.getContext(), "Failed to update", Toast.LENGTH_SHORT).show();
                }
            });
        });

        // ✅ Delete Item
        holder.deleteButton.setOnClickListener(v -> {
            groceryRepository.deleteGroceryItem(item.getId(), success -> {
                if (success) {
                    groceryList.remove(position);
                    notifyItemRemoved(position);
                    // ✅ Show Toast message after successful deletion
                    Toast.makeText(holder.itemView.getContext(), "The item deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(holder.itemView.getContext(), "Failed to delete", Toast.LENGTH_SHORT).show();
                }
            });
        });


        // ✅ Edit Item (Tap to update name & quantity)
        holder.itemName.setOnClickListener(v -> showEditDialog(holder, item, position));
    }

    private void showEditDialog(ViewHolder holder, GroceryItem item, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
        builder.setTitle("Edit Grocery Item");

        View dialogView = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.dialog_edit_grocery, null);
        EditText editName = dialogView.findViewById(R.id.editItemName);
        EditText editQuantity = dialogView.findViewById(R.id.editItemQuantity);

        editName.setText(item.getName());
        editQuantity.setText(String.valueOf(item.getQuantity()));

        builder.setView(dialogView);

        builder.setPositiveButton("Update", (dialog, which) -> {
            String newName = editName.getText().toString().trim();
            String newQuantity = editQuantity.getText().toString().trim();

            if (!newName.isEmpty() && !newQuantity.isEmpty()) {
                item.setName(newName);
                item.setQuantity(Integer.parseInt(newQuantity));

                groceryRepository.updateGroceryItem(item, success -> {
                    if (success) {
                        notifyItemChanged(position);
                        // ✅ Show Toast message after successful update
                        Toast.makeText(holder.itemView.getContext(), "The item updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(holder.itemView.getContext(), "Update failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.show();
    }



    @Override
    public int getItemCount() {
        return groceryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemQuantity;
        CheckBox checkBox;
        Button deleteButton;

        public ViewHolder(View view) {
            super(view);
            itemName = view.findViewById(R.id.itemName);
            itemQuantity = view.findViewById(R.id.itemQuantity);
            checkBox = view.findViewById(R.id.checkBox);
            deleteButton = view.findViewById(R.id.deleteButton);
        }
    }
}
