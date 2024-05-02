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

import com.example.coffeecraft.model.UserOut;
import com.example.coffeecraft.network.ApiService;
import com.example.coffeecraft.network.RetrofitClient;
import com.example.coffeecraft.Utils.GetValueSugar;
import com.google.android.material.slider.Slider;
import com.google.android.material.textview.MaterialTextView;

import java.time.LocalDateTime;

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
        token = "Bearer " + token;

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
        Call<UserOut> call_3 = apiService.getCurrentUser(token);
        call_3.enqueue(new Callback<UserOut>() {
            @Override
            public void onResponse(Call<UserOut> call, Response<UserOut> response) {
                if (response.isSuccessful()) {
                    UserOut currentUser = response.body();
                    Log.d("UserInfo", "UserID: " + currentUser.getId() + ", Email: " + currentUser.getEmail());
                } else {
                    Log.d("UserInfo", "Failed to retrieve user: " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<UserOut> call, Throwable t) {
                Log.e("UserInfo", "Error fetching user info: " + t.getMessage());
            }
        });
    }

    public void suggestCoffee() {
        int sugarInt = (int) sugarSlider.getValue();
        String sugar = new GetValueSugar().getSugarValue(sugarInt);

        ApiService apiService = RetrofitClient.getClient("http://10.0.2.2:8889/api/v1/");
        Call<String> call = apiService.suggestCoffee(token, feeling, sugar);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.d("Coffee", "Suggested Coffee: " + response.body());
                } else {
                    Log.d("Error", "Failed to get coffee suggestion: " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("FailureCallError", "Error fetching coffee suggestion");
                t.printStackTrace();
            }
        });
    }
}
