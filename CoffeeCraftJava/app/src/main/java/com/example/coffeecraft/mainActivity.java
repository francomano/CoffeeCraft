package com.example.coffeecraft;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;

import com.example.coffeecraft.network.ApiService;
import com.example.coffeecraft.network.RetrofitClient;
import com.example.coffeecraft.model.UserOut;



import java.time.LocalDateTime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.coffeecraft.Utils.GetValueSugar;
import com.google.android.material.slider.Slider;
import com.google.android.material.textview.MaterialTextView;

public class mainActivity extends AppCompatActivity {

    String email, password, country, birthdate, date, time, feeling, aroma, token;
    LocalDateTime now;
    private Button suggestCoffeeButton;
    private Slider sugarSlider, milkSlider;
    private MaterialTextView debugTextView, feelingText, aromasText, sugarText, milkText, selectedAroma;
    ImageView depressedEmoji, ecstasyEmoji, happyEmoji, neutralEmoji, sadEmoji;
    ImageView chocolate, caramel, hazelnut, vanilla, speculoos;
    ImageView currentImageView, currentAroma;
    LinearLayout imageEmojiView, slider, imagearomas;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        Intent intent = getIntent();

        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        country = intent.getStringExtra("country");
        birthdate = intent.getStringExtra("date");
        token = intent.getStringExtra("accessToken");
        token = "Bearer " + token;
        //token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MTQxNjgzNDgsInN1YiI6IjIifQ.A4t8xk0eopDjr8yKlLBY5PWtbj8AUlqqu9mO2jL5Ypo";



        // Toast.makeText(this, email, Toast.LENGTH_SHORT).show();
        // Toast.makeText(this, password, Toast.LENGTH_SHORT).show();
        // Toast.makeText(this, country, Toast.LENGTH_SHORT).show();
        // Toast.makeText(this, birthdate, Toast.LENGTH_SHORT).show();

        showButtons();

    }

    @SuppressLint("SetTextI18n")
    public void showButtons() {
        // Initialize views
        suggestCoffeeButton = findViewById(R.id.suggestCoffeeButton);
        sugarSlider = findViewById(R.id.sugarSlider);
        milkSlider = findViewById(R.id.milkSlider);

        imagearomas = findViewById(R.id.imagearomas);
        imageEmojiView = findViewById(R.id.imageemoji);
        slider = findViewById(R.id.slider);

        debugTextView = findViewById(R.id.debugTextView);
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
        debugTextView.setVisibility(View.VISIBLE);
        feelingText.setVisibility(View.VISIBLE);
        imagearomas.setVisibility(View.VISIBLE);
        imageEmojiView.setVisibility(View.VISIBLE);
        aromasText.setVisibility(View.VISIBLE);
        slider.setVisibility(View.VISIBLE);
        aromasText.setVisibility(View.VISIBLE);
        sugarText.setVisibility(View.VISIBLE);
        milkText.setVisibility(View.VISIBLE);
        selectedAroma.setVisibility(View.VISIBLE);


        // Set visibility of the suggestCoffeeButton and feelingsSpinner to VISIBLE
        suggestCoffeeButton.setVisibility(View.VISIBLE);

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

        suggestCoffeeButton.setOnClickListener(v -> debugTextView.setText("Outcome: " + getUserInfo()));
    }

    public int getUserInfo(){
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

        int sugarInt = (int) sugarSlider.getValue();
        String sugar = new GetValueSugar().getSugarValue(sugarInt);
        int milk = (int) milkSlider.getValue();

        ApiService apiService = RetrofitClient.getClient("http://10.0.2.2:8889/api/v1/");
        Log.d("token: ",token);
        Call<UserOut> call_3 = apiService.getCurrentUser(token);
        call_3.enqueue(new Callback<UserOut>() {
            @Override
            public void onResponse(Call<UserOut> call, Response<UserOut> response) {
                if (response.isSuccessful()) {
                    UserOut currentUser = response.body();
                    System.out.println("UserID: " + currentUser.getId() + ", Email: " + currentUser.getEmail());
                } else {
                    System.out.println("Failed to retrieve user: " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<UserOut> call, Throwable t) {
                t.printStackTrace();
            }
        });
        Log.d("feeling",feeling);
        Call<String> call_4 = apiService.suggestCoffee(token, feeling, sugar);
        call_4.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.d("Coffee","Suggested Coffee: " + response.body());
                } else {
                    Log.d("Error", "Failed to get coffee suggestion: " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("FailureCallError","Error fetching coffee suggestion");
                t.printStackTrace();
            }
        });
        return 1;
    }

}
