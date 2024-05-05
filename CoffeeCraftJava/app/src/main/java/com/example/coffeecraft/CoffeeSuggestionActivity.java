package com.example.coffeecraft;

import com.example.coffeecraft.model.BuyCoffeeRequest;
import com.example.coffeecraft.model.PurchaseResponse;

import com.example.coffeecraft.network.ApiService;
import com.example.coffeecraft.network.RetrofitClient;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import android.util.Log;

import android.widget.LinearLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoffeeSuggestionActivity extends AppCompatActivity {

    private List<String> suggestedCoffeeList;
    private String token;
    private Integer sugarInt;
    private String feeling;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_suggestion);

        // Retrieve data from intent
        Intent intent = getIntent();
        suggestedCoffeeList = intent.getStringArrayListExtra("suggestedCoffeeList");
        sugarInt = intent.getIntExtra("sugar",0);
        feeling = intent.getStringExtra("mood");
        token = intent.getStringExtra("token");

        // Initialize ApiService instance
        apiService = RetrofitClient.getClient("http://10.0.2.2:8889/api/v1/");

        // Initialize views
        LinearLayout coffeeImagesLayout = findViewById(R.id.coffeeImagesLayout);
        Button buyButton = findViewById(R.id.buyButton);
        Button getNewSuggestionButton = findViewById(R.id.getNewSuggestionButton);

        // Display suggested coffees
        for (String coffeeName : suggestedCoffeeList) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(getImageResourceForCoffee(coffeeName));
            imageView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            coffeeImagesLayout.addView(imageView);
        }

        // Handle buy button click
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Assuming the first coffee is selected for buying
                String selectedCoffee = suggestedCoffeeList.get(0);
                buyCoffee(selectedCoffee);
            }
        });

        // Handle get new suggestion button click
        getNewSuggestionButton.setOnClickListener(new View.OnClickListener() {
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

    // Method to execute the buyCoffee call
    private void buyCoffee(String coffeeType) {
        BuyCoffeeRequest request = new BuyCoffeeRequest("happy", 3, coffeeType);
        Call<PurchaseResponse> call = apiService.buyCoffee(token, request);
        call.enqueue(new Callback<PurchaseResponse>() {
            @Override
            public void onResponse(Call<PurchaseResponse> call, Response<PurchaseResponse> response) {
                if (response.isSuccessful()) {
                    // Handle successful response
                    PurchaseResponse purchaseResponse = response.body();
                    // Access fields of purchaseResponse and process accordingly
                    String mood = purchaseResponse.getMood();
                    int sugar = purchaseResponse.getSugar();
                    String coffee_type = purchaseResponse.getCoffeeType();
                    System.out.println("you bought "+coffee_type+"!");

                } else {
                    // Handle unsuccessful response
                    Log.e("Error", "Failed to buy coffee: " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<PurchaseResponse> call, Throwable t) {
                // Handle failure
                Log.e("Failure", "Error buying coffee", t);
            }
        });
    }
}




