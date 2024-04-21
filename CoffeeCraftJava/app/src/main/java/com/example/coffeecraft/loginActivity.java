package com.example.coffeecraft;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;

import java.io.IOException;
import com.example.coffeecraft.network.ApiService;
import com.example.coffeecraft.network.RetrofitClient;
import com.example.coffeecraft.model.TokenResponse;


public class loginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);
        logInBtnListener();
    }

    public void logInBtnListener() {
        TextInputEditText emailEditText = findViewById(R.id.emailTextField);
        TextInputEditText passwordEditText = findViewById(R.id.passwordTextField);
        MaterialButton logInButton = findViewById(R.id.buttonLogIn);
        MaterialButton createAccountButton = findViewById(R.id.createAccountButton);


        ApiService apiService = RetrofitClient.getClient("http://10.0.2.2:8889/api/v1/");

        logInButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            Log.d("email: ", email);

            // Update: Remove the LoginRequest object and pass email and password directly

            apiService.getAccessToken(email, password).enqueue(new Callback<TokenResponse>() {

                @Override
                public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                    Log.d("LoginActivity", "Response code: " + response.code());
                    if (response.isSuccessful()) {
                        TokenResponse tokenResponse = response.body();
                        if (tokenResponse != null && tokenResponse.getAccessToken() != null) {
                            String accessToken = tokenResponse.getAccessToken();
                            Log.d("LoginActivity", "Access Token: " + accessToken);
                            Intent intent = new Intent(loginActivity.this, mainActivity.class);
                            intent.putExtra("email", email);
                            intent.putExtra("password", password);
                            intent.putExtra("accessToken", accessToken);
                            startActivity(intent);
                            loginActivity.this.finish();
                        } else {
                            Log.e("LoginActivity", "Error: Access token not found in response");
                            Toast.makeText(loginActivity.this, "Error: Invalid response from server", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        try {
                            String errorMessage = response.errorBody().string();
                            Log.e("LoginActivity", "Error Message: " + errorMessage);
                            Toast.makeText(loginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            Log.e("LoginActivity", "Error retrieving error message", e);
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<TokenResponse> call, Throwable t) {
                    Log.e("LoginActivity", "Login failed", t);
                    Toast.makeText(loginActivity.this, "Failed to log in. Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        });

        createAccountButton.setOnClickListener(view -> {
            Intent intent2 = new Intent(loginActivity.this, newAccountActivity.class);
            startActivity(intent2);
        });
    }
}
