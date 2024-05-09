package com.example.coffeecraft;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import com.example.coffeecraft.network.ApiService;
import com.example.coffeecraft.network.RetrofitClient;
import com.example.coffeecraft.model.BuyCoffeeRequest;
import com.example.coffeecraft.model.PurchaseResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CoffeeSuggestionActivity extends AppCompatActivity {

    private List<String> suggestedCoffeeList;
    private Integer sugarInt;
    private String feeling;
    private String token;
    private ApiService apiService;
    private RecyclerView recyclerView;
    private CoffeeAdapter adapter;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_suggestion);


        context = this.getBaseContext();

        // Retrieve data from intent
        Intent intent = getIntent();
        suggestedCoffeeList = intent.getStringArrayListExtra("suggestedCoffeeList");
        sugarInt = intent.getIntExtra("sugar",0);
        feeling = intent.getStringExtra("mood");
        token = intent.getStringExtra("token");

        // Initialize ApiService instance
        apiService = RetrofitClient.getClient("http://10.0.2.2:8889/api/v1/");

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CoffeeAdapter(this, suggestedCoffeeList);
        recyclerView.setAdapter(adapter);

        // Initialize Buy Button
        Button buyButton = findViewById(R.id.buyButton);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedPosition = adapter.getSelectedPosition();
                if (selectedPosition != RecyclerView.NO_POSITION) {
                    String selectedCoffee = suggestedCoffeeList.get(selectedPosition);
                    buyCoffee(selectedCoffee);
                }
            }
        });

        // Initialize Get New Suggestion Button
        Button getNewSuggestionButton = findViewById(R.id.getNewSuggestionButton);
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

    // Method to execute the buyCoffee call
    private void buyCoffee(String coffeeType) {
        BuyCoffeeRequest request = new BuyCoffeeRequest(feeling,sugarInt,coffeeType);
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
