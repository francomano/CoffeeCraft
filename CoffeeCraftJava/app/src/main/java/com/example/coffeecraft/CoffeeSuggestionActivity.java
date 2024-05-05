package com.example.coffeecraft;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
}
