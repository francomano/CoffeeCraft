package com.example.coffeecraft;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoffeeAdapter extends RecyclerView.Adapter<CoffeeAdapter.ViewHolder> {

    private List<String> coffeeList;
    private int selectedPosition = RecyclerView.NO_POSITION; // Initially no item is selected
    private Map<String, Integer> coffeeImageMap; // Map to store coffee type and corresponding image resource
    private int defaultImageResource; // Default image resource ID

    // Constructor
    public CoffeeAdapter(Context context, List<String> coffeeList) {
        this.coffeeList = coffeeList;
        this.coffeeImageMap = createCoffeeImageMap(context);
    }

    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView coffeeImageView;
        public TextView coffeeNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            coffeeImageView = itemView.findViewById(R.id.coffeeImageView);
            coffeeNameTextView = itemView.findViewById(R.id.coffeeNameTextView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coffee_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String coffeeType = coffeeList.get(position);
        holder.coffeeNameTextView.setText(coffeeType);
        Integer imageResource = coffeeImageMap.get(coffeeType);
        if (imageResource != null) {
            holder.coffeeImageView.setImageResource(imageResource);
        } else {
            holder.coffeeImageView.setImageResource(defaultImageResource);
        }

        // Highlight selected item
        holder.itemView.setSelected(selectedPosition == position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemChanged(selectedPosition); // Deselect previously selected item
                selectedPosition = holder.getAdapterPosition(); // Update selected position
                notifyItemChanged(selectedPosition); // Highlight newly selected item
            }
        });
    }

    @Override
    public int getItemCount() {
        return coffeeList.size();
    }

    // Method to create a mapping between coffee types and corresponding image
    // resources
    private Map<String, Integer> createCoffeeImageMap(Context context) {
        Map<String, Integer> imageMap = new HashMap<>();
        imageMap.put("espresso", R.drawable.espresso);
        /*
        imageMap.put("latte", R.drawable.latte);
        imageMap.put("black_coffee", R.drawable.black_coffee);
        imageMap.put("mocha", R.drawable.mocha);
        imageMap.put("americano", R.drawable.americano);
        imageMap.put("cappuccino", R.drawable.cappuccino);
        imageMap.put("flat_white", R.drawable.flat_white);
        imageMap.put("cafe_au_lait", R.drawable.cafe_au_lait);
        imageMap.put("macchiato", R.drawable.macchiato);
        imageMap.put("cold_brew", R.drawable.cold_brew);
        imageMap.put("irish_coffee", R.drawable.irish_coffee);
        imageMap.put("frappe", R.drawable.frappe);
        imageMap.put("vietnamese_coffee", R.drawable.vietnamese_coffee);
        imageMap.put("affogato", R.drawable.affogato);
        imageMap.put("red_eye", R.drawable.red_eye);

         */

        // Default image resource
        defaultImageResource = R.drawable.default_coffee_image;

        return imageMap;
    }

    // Method to get the selected position
    public int getSelectedPosition() {
        return selectedPosition;
    }
}
