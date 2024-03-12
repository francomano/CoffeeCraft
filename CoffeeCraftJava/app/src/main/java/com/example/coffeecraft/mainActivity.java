package com.example.coffeecraft;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textview.MaterialTextView;

public class mainActivity extends AppCompatActivity {

    Context context;

    String email;
    String password;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);
        context = this.getBaseContext();

        Intent intent = getIntent();

        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        System.out.println(email);

        showInfo();

    }

    @SuppressLint("SetTextI18n")
    public void showInfo(){
        MaterialTextView setEmail = findViewById(R.id.welcomeEmail);
        setEmail.setText("Welcome, " + email + " !");
        MaterialTextView setPassword = findViewById(R.id.welcomePassword);
        setPassword.setText("Password : " + password);

    }
}
