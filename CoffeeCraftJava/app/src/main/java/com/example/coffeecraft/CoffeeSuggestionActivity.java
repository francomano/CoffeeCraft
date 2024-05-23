package com.example.coffeecraft;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coffeecraft.Utils.Coffee;
import com.example.coffeecraft.model.BuyCoffeeRequest;
import com.example.coffeecraft.model.PurchaseResponse;
import com.example.coffeecraft.network.ApiService;
import com.example.coffeecraft.network.RetrofitClient;
import com.google.android.material.textview.MaterialTextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoffeeSuggestionActivity extends AppCompatActivity {
    private int currentCoffeeIndex = 0;

    private ApiService apiService;
    ArrayList<Coffee> coffeeList;
    Context context;
    Integer sugarInt;
    String feeling;
    String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coffeesuggestion);

        context = this.getBaseContext();

        Intent intent = getIntent();

        sugarInt = intent.getIntExtra("sugar", 0);
        feeling = intent.getStringExtra("mood");token = intent.getStringExtra("token");
        token = intent.getStringExtra("token");

        apiService = RetrofitClient.getClient("http://10.0.2.2:8889/api/v1/");

        coffeeList = intent.getParcelableArrayListExtra("suggestedCoffeeList");

        ImageView coffeeImage = findViewById(R.id.coffeeImage);
        MaterialTextView coffeeDescription = findViewById(R.id.coffeeDescription);
        ImageButton buttonBuy = findViewById(R.id.buttonBuy);
        ImageButton buttonNext = findViewById(R.id.buttonNext);

        updateCoffeeInfo(coffeeImage, coffeeDescription);

        buttonBuy.setOnClickListener(v -> buyCoffee(coffeeList.get(currentCoffeeIndex)));

        buttonNext.setOnClickListener(v -> {
            currentCoffeeIndex = (currentCoffeeIndex + 1) % coffeeList.size();
            updateCoffeeInfo(coffeeImage, coffeeDescription);
        });
    }

    private void updateCoffeeInfo(ImageView coffeeImage, MaterialTextView coffeeDescription) {
        Coffee coffee = coffeeList.get(currentCoffeeIndex);
        // Mise à jour de l'image et de la description du café
        String idxStr = String.valueOf(currentCoffeeIndex+1) + " : ";
        String descr = idxStr + coffee.getDescription();
        coffeeDescription.setText(descr);
        if(!coffee.getDescription().equals("espresso")){
            coffeeImage.setImageResource(coffee.getImageResourceId());
        } else {
            coffeeImage.setImageResource(R.drawable.espresso);
        }

    }

    private void buyCoffee(Coffee coffee) {
        BuyCoffeeRequest request = new BuyCoffeeRequest(feeling,sugarInt,coffee.getDescription());
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
                    System.out.println("You bought " + coffee_type + "!");
                    Toast.makeText(CoffeeSuggestionActivity.this, "You bought " + coffee_type + " !", Toast.LENGTH_SHORT).show();
                    try {
                        setHistoric(coffee_type);
                    } catch (IOException e) {
                        Toast.makeText(CoffeeSuggestionActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle unsuccessful response
                    System.err.println("Failed to buy coffee: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<PurchaseResponse> call, Throwable t) {
                // Handle failure
                System.err.println("Error buying coffee: " + t.getMessage());
            }
        });
    }

    public void setHistoric(String coffee_type) throws IOException {

        HashMap<String, Integer> hashMap = null;
        String filename = "historic.ser";

        try (FileInputStream fis = context.openFileInput(filename)) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            hashMap = (HashMap<String, Integer>) ois.readObject();
            fis.close();
            ois.close();
        } catch (IOException | ClassCastException | ClassNotFoundException e){
            Toast.makeText(context, "No historic !", Toast.LENGTH_SHORT).show();
        }

        if(hashMap == null){
            hashMap = new HashMap<>();
            hashMap.put(coffee_type, 1);
        } else {
            if(hashMap.containsKey(coffee_type)){
                int n = hashMap.get(coffee_type);
                hashMap.replace(coffee_type, n+1);
            } else {
                hashMap.put(coffee_type, 1);
            }
        }

        try (FileOutputStream fos = context.openFileOutput(filename, MODE_PRIVATE)) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(hashMap);
            fos.close();
            oos.close();
        } catch (IOException | ClassCastException e){
            Toast.makeText(context, "Error writing file !", Toast.LENGTH_SHORT).show();
        }
    }
}
