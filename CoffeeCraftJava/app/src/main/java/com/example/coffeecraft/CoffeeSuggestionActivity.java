package com.example.coffeecraft;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CoffeeSuggestionActivity extends AppCompatActivity {

    private String suggestedCoffee;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_suggestion);

        // Retrieve data from intent
        Intent intent = getIntent();
        suggestedCoffee = intent.getStringExtra("suggestedCoffee");
        token = intent.getStringExtra("token");

        // Initialize views
        ImageView coffeeImageView = findViewById(R.id.coffeeImageView);
        TextView coffeeNameTextView = findViewById(R.id.coffeeNameTextView);
        Button acceptButton = findViewById(R.id.acceptButton);
        Button discardButton = findViewById(R.id.discardButton);

        // Set suggested coffee image based on coffee type
        int imageResource = getImageResourceForCoffee(suggestedCoffee);
        coffeeImageView.setImageResource(imageResource);

        // Set suggested coffee name
        coffeeNameTextView.setText(suggestedCoffee);

        // Handle accept button click
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return to MainActivity with token
                Intent intent = new Intent(CoffeeSuggestionActivity.this, MainActivity.class);
                intent.putExtra("accessToken", token);
                startActivity(intent);
                finish();
            }
        });

        // Handle discard button click
        discardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return to MainActivity with token
                Intent intent = new Intent(CoffeeSuggestionActivity.this, MainActivity.class);
                intent.putExtra("accessToken", token);
                startActivity(intent);
                finish();
            }
        });
    }

    // Method to get the image resource for a given coffee type
    private int getImageResourceForCoffee(String coffeeType) {
        switch (coffeeType) {
            case "espresso":
                return R.drawable.espresso;
            case "cappuccino":
                return R.drawable.cappuccino;
            // Add cases for other coffee types
            case "americano":
                return R.drawable.americano;
            case "cortado":
                return R.drawable.cortado;
            default:
                return R.drawable.default_coffee_image; // Default image
        }
    }
}
