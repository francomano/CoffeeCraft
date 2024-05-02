package com.example.coffeecraft;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        // Get user info from intent
        Intent intent = getIntent();
        String userInfo = intent.getStringExtra("userInfo");
        String token = intent.getStringExtra("token");

        // Display user info
        TextView userInfoTextView = findViewById(R.id.userInfoTextView);
        userInfoTextView.setText(userInfo);

        // Button to return to MainActivity
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            // Create an intent to return to MainActivity
            Intent mainActivityIntent = new Intent(UserInfoActivity.this, MainActivity.class);
            // Pass the token back to MainActivity
            System.out.println(token);
            mainActivityIntent.putExtra("accessToken", token);
            // Start the MainActivity
            startActivity(mainActivityIntent);
            // Finish the current activity
            finish();
        });
    }
}
