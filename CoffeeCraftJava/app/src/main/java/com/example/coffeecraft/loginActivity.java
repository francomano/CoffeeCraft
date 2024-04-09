package com.example.coffeecraft;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.io.IOException;
import android.util.Log;

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

        //ApiService apiService = RetrofitClient.getClient("https://localhost/api/v1/");
        ApiService apiService = RetrofitClient.getClient("https://10.0.2.2:8080/api/v1/");

        logInButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            Log.d("email: ", email);

            LoginRequest loginRequest = new LoginRequest(email, password);  // Create LoginRequest object with email and password

            apiService.getAccessToken(loginRequest).enqueue(new Callback<TokenResponse>() {

                @Override
                public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                    Log.d("LoginActivity", "Response code: " + response.code());
                    if (response.isSuccessful()) {

                        String accessToken = response.body().getAccessToken();
                        Log.d("LoginActivity", "Access Token: " + accessToken);
                        // Save the access token (e.g., in SharedPreferences) for future API calls
                        // Start mainActivity
                        Intent intent = new Intent(loginActivity.this, mainActivity.class);
                        intent.putExtra("email", email);
                        intent.putExtra("password", password);
                        intent.putExtra("accessToken", accessToken);  // Pass the access token to mainActivity
                        startActivity(intent);
                        loginActivity.this.finish();

                    } else {
                        // Handle error
                        try {
                            String errorMessage = response.errorBody().string();

                            // Log error message
                            Log.e("LoginActivity", "Error Message: " + errorMessage);

                            // Show error message to the user
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
                    // Handle failure
                    Toast.makeText(loginActivity.this, "Failed to log in. Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        });

        Intent intent2 = new Intent(this, newAccountActivity.class);

        ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == loginActivity.RESULT_OK) {
                        finish();
                    }
                });

        createAccountButton.setOnClickListener(view -> {
            mStartForResult.launch(intent2);
            // This is the line of code that destroys the buttons of the login and creates the ones of the create account

        });
    }
}
