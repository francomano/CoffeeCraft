package com.example.coffeecraft;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import com.google.android.material.textview.MaterialTextView;

public class mainActivity extends AppCompatActivity {



    String email;
    String password;

    LocalDateTime now;
    String date;
    String time;
    String feeling;

    private Button suggestCoffeeButton;
    private CheckBox sugarCheckBox;
    private CheckBox aromasCheckBox;
    private TextView debugTextView, feelingText;
    ImageView depressedEmoji;
    ImageView sadEmoji;
    ImageView neutralEmoji;
    ImageView happyEmoji;
    ImageView joyfullEmoji;
    ImageView currentImageView;
    LinearLayout imageEmojiView, checkBoxes;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        Intent intent = getIntent();

        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        System.out.println(email);

        showButtons();

    }

    @SuppressLint("SetTextI18n")
    public void showButtons() {
        // Initialize views
        suggestCoffeeButton = findViewById(R.id.suggestCoffeeButton);
        sugarCheckBox = findViewById(R.id.sugarCheckBox);
        aromasCheckBox = findViewById(R.id.aromasCheckBox);
        debugTextView = findViewById(R.id.debugTextView);
        feelingText = findViewById(R.id.feelingtext);

        depressedEmoji = findViewById(R.id.emoji_depressed);
        sadEmoji = findViewById(R.id.emoji_sad);
        neutralEmoji = findViewById(R.id.emoji_neutral);
        happyEmoji = findViewById(R.id.emoji_happy);
        joyfullEmoji = findViewById(R.id.emoji_joyfull);
        imageEmojiView = findViewById(R.id.imageemoji);
        checkBoxes = findViewById(R.id.checkBoxes);

        imageEmojiView.setVisibility(View.VISIBLE);
        sugarCheckBox.setVisibility(View.VISIBLE);
        aromasCheckBox.setVisibility(View.VISIBLE);
        debugTextView.setVisibility(View.VISIBLE);
        feelingText.setVisibility(View.VISIBLE);

        // Set visibility of the suggestCoffeeButton and feelingsSpinner to VISIBLE
        suggestCoffeeButton.setVisibility(View.VISIBLE);
        checkBoxes.setVisibility(View.VISIBLE);

        currentImageView = depressedEmoji;

        depressedEmoji.setOnClickListener(view -> {
            feeling = depressedEmoji.getContentDescription().toString();
            currentImageView.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
            currentImageView = depressedEmoji;
            view.setBackgroundColor(Color.parseColor("#D3D3D3"));
        });
        sadEmoji.setOnClickListener(view -> {
            feeling = sadEmoji.getContentDescription().toString();
            currentImageView.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
            currentImageView = sadEmoji;
            view.setBackgroundColor(Color.parseColor("#D3D3D3"));
        });
        neutralEmoji.setOnClickListener(view -> {
            feeling = neutralEmoji.getContentDescription().toString();
            currentImageView.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
            currentImageView = neutralEmoji;
            view.setBackgroundColor(Color.parseColor("#D3D3D3"));
        });
        happyEmoji.setOnClickListener(view -> {
            feeling = happyEmoji.getContentDescription().toString();
            currentImageView.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
            currentImageView = happyEmoji;
            view.setBackgroundColor(Color.parseColor("#D3D3D3"));
        });
        joyfullEmoji.setOnClickListener(view -> {
            feeling = joyfullEmoji.getContentDescription().toString();
            currentImageView.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
            currentImageView = joyfullEmoji;
            view.setBackgroundColor(Color.parseColor("#D3D3D3"));
        });


        suggestCoffeeButton.setOnClickListener(v -> debugTextView.setText("Outcomes: " + getUserInfo()));
    }

    public List<String> getUserInfo(){
        now = LocalDateTime.now();
        //String date = now.getDayOfMonth() + " " + now.getMonth() + " " + now.getYear();
        //String time = now.getHour() + ":" + now.getMinute();

        int hour = now.getHour();
        String partOfDay;
        if (hour >= 5 && hour < 12) {
            partOfDay = "morning";
        } else if (hour >= 12 && hour < 18) {
            partOfDay = "afternoon";
        } else {
            partOfDay = "evening";
        }


        int sugarChecked = sugarCheckBox.isChecked() ? 1 : 0;
        int aromasChecked = aromasCheckBox.isChecked() ? 1 : 0;

        List<String> outcomes = new ArrayList<>();
        outcomes.add(email);
        outcomes.add(String.valueOf(sugarChecked));
        outcomes.add(String.valueOf(aromasChecked));
        outcomes.add(partOfDay);
        outcomes.add(feeling);

        return outcomes;
    }

}
