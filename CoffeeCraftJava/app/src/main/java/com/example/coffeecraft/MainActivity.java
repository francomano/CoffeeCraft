package com.example.coffeecraft;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.coffeecraft.Utils.Coffee;
import com.example.coffeecraft.model.UserOut;
import com.example.coffeecraft.network.ApiService;
import com.example.coffeecraft.network.RetrofitClient;
import com.google.android.material.slider.Slider;
import com.google.android.material.textview.MaterialTextView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    String email, password, country, birthdate, date, time, feeling, aroma, token;
    LocalDateTime now;
    private Slider sugarSlider, milkSlider;
    private MaterialTextView feelingText, aromasText, sugarText, milkText, selectedAroma;
    ImageView depressedEmoji, ecstasyEmoji, happyEmoji, neutralEmoji, sadEmoji;
    ImageView chocolate, caramel, hazelnut, vanilla, speculoos;
    ImageView currentImageView, currentAroma;
    LinearLayout imageEmojiView, slider, imagearomas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        Intent intent = getIntent();

        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        country = intent.getStringExtra("country");
        birthdate = intent.getStringExtra("date");
        token = intent.getStringExtra("accessToken");
        if (token != null && token.length() >= 6 && !token.substring(0, 6).equals("Bearer")) {
            token = "Bearer " + token;
        }

        showButtons();

    }

    public void showButtons() {
        // Initialize views
        sugarSlider = findViewById(R.id.sugarSlider);
        milkSlider = findViewById(R.id.milkSlider);

        imagearomas = findViewById(R.id.imagearomas);
        imageEmojiView = findViewById(R.id.imageemoji);
        slider = findViewById(R.id.slider);

        feelingText = findViewById(R.id.feelingtext);
        sugarText = findViewById(R.id.sugartext);
        milkText = findViewById(R.id.milkText);
        aromasText = findViewById(R.id.amorastext);
        selectedAroma = findViewById(R.id.selectedAroma);

        depressedEmoji = findViewById(R.id.emoji_depressed);
        sadEmoji = findViewById(R.id.emoji_sad);
        neutralEmoji = findViewById(R.id.emoji_neutral);
        happyEmoji = findViewById(R.id.emoji_happy);
        ecstasyEmoji = findViewById(R.id.emoji_ecstasy);

        chocolate = findViewById(R.id.chocolate);
        caramel = findViewById(R.id.caramel);
        vanilla = findViewById(R.id.vanilla);
        speculoos = findViewById(R.id.speculoos);
        hazelnut = findViewById(R.id.noisette);

        sugarSlider.setVisibility(View.VISIBLE);
        milkSlider.setVisibility(View.VISIBLE);
        feelingText.setVisibility(View.VISIBLE);
        imagearomas.setVisibility(View.VISIBLE);
        imageEmojiView.setVisibility(View.VISIBLE);
        aromasText.setVisibility(View.VISIBLE);
        slider.setVisibility(View.VISIBLE);
        aromasText.setVisibility(View.VISIBLE);
        sugarText.setVisibility(View.VISIBLE);
        milkText.setVisibility(View.VISIBLE);
        selectedAroma.setVisibility(View.VISIBLE);

        currentImageView = depressedEmoji;
        currentAroma = hazelnut;

        depressedEmoji.setOnClickListener(view -> {
            feeling = "depressed";
            currentImageView.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
            currentImageView = depressedEmoji;
            view.setBackgroundColor(Color.parseColor("#D3D3D3"));
        });
        sadEmoji.setOnClickListener(view -> {
            feeling = "sad";
            currentImageView.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
            currentImageView = sadEmoji;
            view.setBackgroundColor(Color.parseColor("#D3D3D3"));
        });
        neutralEmoji.setOnClickListener(view -> {
            feeling = "normal";
            currentImageView.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
            currentImageView = neutralEmoji;
            view.setBackgroundColor(Color.parseColor("#D3D3D3"));
        });
        happyEmoji.setOnClickListener(view -> {
            feeling = "happy";
            currentImageView.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
            currentImageView = happyEmoji;
            view.setBackgroundColor(Color.parseColor("#D3D3D3"));
        });
        ecstasyEmoji.setOnClickListener(view -> {
            feeling = "ecstatic";
            currentImageView.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
            currentImageView = ecstasyEmoji;
            view.setBackgroundColor(Color.parseColor("#D3D3D3"));
        });

        hazelnut.setOnClickListener(view -> {
            aroma = hazelnut.getContentDescription().toString();
            currentAroma.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
            currentAroma = hazelnut;
            selectedAroma.setText(currentAroma.getContentDescription().toString());
            view.setBackgroundColor(Color.parseColor("#D3D3D3"));
        });
        chocolate.setOnClickListener(view -> {
            aroma = chocolate.getContentDescription().toString();
            currentAroma.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
            currentAroma = chocolate;
            selectedAroma.setText(currentAroma.getContentDescription().toString());
            view.setBackgroundColor(Color.parseColor("#D3D3D3"));
        });
        vanilla.setOnClickListener(view -> {
            aroma = vanilla.getContentDescription().toString();
            currentAroma.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
            currentAroma = vanilla;
            selectedAroma.setText(currentAroma.getContentDescription().toString());
            view.setBackgroundColor(Color.parseColor("#D3D3D3"));
        });
        speculoos.setOnClickListener(view -> {
            aroma = speculoos.getContentDescription().toString();
            currentAroma.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
            currentAroma = speculoos;
            selectedAroma.setText(currentAroma.getContentDescription().toString());
            view.setBackgroundColor(Color.parseColor("#D3D3D3"));
        });
        caramel.setOnClickListener(view -> {
            aroma = caramel.getContentDescription().toString();
            currentAroma.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
            currentAroma = caramel;
            selectedAroma.setText(currentAroma.getContentDescription().toString());
            view.setBackgroundColor(Color.parseColor("#D3D3D3"));
        });

        Button suggestCoffeeButton = findViewById(R.id.suggestCoffeeButton);
        Button getInfoButton = findViewById(R.id.getInfoButton);

        suggestCoffeeButton.setVisibility(View.VISIBLE);
        getInfoButton.setVisibility(View.VISIBLE);

        suggestCoffeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suggestCoffee();
            }
        });

        getInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserInfo();
            }
        });
    }

    public void getUserInfo() {

        ApiService apiService = RetrofitClient.getClient("http://10.0.2.2:8889/api/v1/");
        Log.d("token: ", token);
        Call<UserOut> call_3 = apiService.getCurrentUser(token);
        call_3.enqueue(new Callback<UserOut>() {
            @Override
            public void onResponse(Call<UserOut> call, Response<UserOut> response) {
                if (response.isSuccessful()) {
                    UserOut currentUser = response.body();
                    // Start UserInfoActivity with user info
                    Intent userInfoIntent = new Intent(MainActivity.this, UserInfoActivity.class);
                    userInfoIntent.putExtra("userInfo",
                            "User ID: " + currentUser.getId() + ", Email: " + currentUser.getEmail());
                    userInfoIntent.putExtra("token", token); // Pass token to UserInfoActivity
                    startActivity(userInfoIntent);
                } else {
                    Log.d("Retrieval Error", "Failed to retrieve user: " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<UserOut> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void suggestCoffee() {

        if (milkSlider != null) {
            int milkInt = (int) milkSlider.getValue();
        }
        int sugarInt = (int) sugarSlider.getValue();
        now = LocalDateTime.now();

        int hour = now.getHour();
        String partOfDay;
        if (hour >= 5 && hour < 12) {
            partOfDay = "morning";
        } else if (hour >= 12 && hour < 18) {
            partOfDay = "afternoon";
        } else {
            partOfDay = "evening";
        }


        ApiService apiService = RetrofitClient.getClient("http://10.0.2.2:8889/api/v1/");
        Call<List<String>> call = apiService.suggestCoffee(token, feeling, sugarInt);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response){
                ArrayList<Coffee> coffees = new ArrayList<>();
                if (response.isSuccessful()) {
                    // Start CoffeeSuggestionActivity with suggested coffee and token
                    // espresso = "espresso"
                    // latte = "latte"
                    // black_coffee = "black_coffee"
                    // mocha = "mocha"
                    // americano = "americano"
                    // cappuccino = "cappuccino"
                    // flat_white = "flat_white"
                    // cafe_au_lait = "cafe_au_lait"
                    // macchiato = "macchiato"
                    // cold_brew = "cold_brew"
                    // irish_coffee = "irish_coffee"
                    // frappe = "frappe"
                    // vietnamese_coffee = "vietnamese_coffee"
                    // affogato = "affogato"
                    // red_eye = "red_eye"
                    Intent suggestionIntent = new Intent(MainActivity.this, CoffeeSuggestionActivity.class);
                    for(String coffee : response.body()){
                        if(!Objects.equals(coffee, "espresso")) {
                            coffees.add(new Coffee(coffee, R.drawable.espresso));
                        if(!Objects.equals(coffee, "latte")) {
                            coffees.add(new Coffee(coffee, R.drawable.latte));
                        if(!Objects.equals(coffee, "black_coffee")) {
                            coffees.add(new Coffee(coffee, R.drawable.ristretto));
                        if(!Objects.equals(coffee, "mocha")) {
                            coffees.add(new Coffee(coffee, R.drawable.mocha));
                        if(!Objects.equals(coffee, "americano")) {
                            coffees.add(new Coffee(coffee, R.drawable.americano));
                        if(!Objects.equals(coffee, "cappuccino")) {
                            coffees.add(new Coffee(coffee, R.drawable.cappuccino));
                            if(!Objects.equals(coffee, "flat_white")) {
                            coffees.add(new Coffee(coffee, R.drawable.flat_white));
                        if(!Objects.equals(coffee, "cafe_au_lait")) {
                            coffees.add(new Coffee(coffee, R.drawable.breve));
                        if(!Objects.equals(coffee, "macchiato")) {
                            coffees.add(new Coffee(coffee, R.drawable.macchiato));
                        if(!Objects.equals(coffee, "cold_brew")) {
                            coffees.add(new Coffee(coffee, R.drawable.glace));
                        if(!Objects.equals(coffee, "irish_coffee")) {
                            coffees.add(new Coffee(coffee, R.drawable.irish));
                        if(!Objects.equals(coffee, "frappe")) {
                            coffees.add(new Coffee(coffee, R.drawable.frappe));
                        if(!Objects.equals(coffee, "vietnamese_coffee")) {
                            coffees.add(new Coffee(coffee, R.drawable.vienna));
                        if(!Objects.equals(coffee, "affogato")) {
                            coffees.add(new Coffee(coffee, R.drawable.mocaccino));
                        if(!Objects.equals(coffee, "red_eye")) {
                            coffees.add(new Coffee(coffee, R.drawable.raf));
                        } else {
                            coffees.add(new Coffee(coffee, R.drawable.espresso));
                        }
                    }
                    suggestionIntent.putParcelableArrayListExtra("suggestedCoffeeList", coffees);
                    suggestionIntent.putExtra("mood", feeling);
                    suggestionIntent.putExtra("sugar",sugarInt);
                    suggestionIntent.putExtra("token", token);
                    startActivity(suggestionIntent);
                } else {
                    Log.d("Error", "Failed to get coffee suggestion: " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("FailureCallError", "Error fetching coffee suggestion");
                t.printStackTrace();
            }
        });
    }
}
