package com.example.coffeecraft;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import com.example.coffeecraft.model.UserCreateOpen;
import com.example.coffeecraft.network.ApiService;
import com.example.coffeecraft.network.RetrofitClient;
import com.example.coffeecraft.model.UserOut;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.coffeecraft.Utils.PasswordUtils;

import org.w3c.dom.Text;

import java.util.regex.Pattern;

public class newAccountActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newaccountactivity);

        buttonListener();
    }

    public void buttonListener(){
        MaterialButton accountButton = findViewById(R.id.buttonAccount);
        TextInputEditText email1 = findViewById(R.id.emailTextField);
        TextInputEditText email2 = findViewById(R.id.confirmEmailTextField);
        TextInputEditText password1 = findViewById(R.id.passwordTextField);
        TextInputEditText password2 = findViewById(R.id.confirmPasswordTextField);

        accountButton.setOnClickListener(view -> {
            String checkInputsEditText = checkInputsEditText(email1, email2, password1, password2);

            if (!checkInputsEditText.equals("checkCompleted")){
                MaterialTextView error = findViewById(R.id.res);
                error.setText(checkInputsEditText);
            } else {
                UserCreateOpen newUser = new UserCreateOpen();
                newUser.setEmail(email1.getText().toString());
                newUser.setPassword(password1.getText().toString());
                newUser.setFullName("New User");
                newUser.setAge(30);
                newUser.setCountry("US");
                ApiService apiService = RetrofitClient.getClient("http://10.0.2.2:80/api/v1/");
                Call<UserOut> call_2 = apiService.createUserOpen(newUser);
                call_2.enqueue(new Callback<UserOut>() {
                    @Override
                    public void onResponse(Call<UserOut> call, Response<UserOut> response) {
                        if (response.isSuccessful()) {
                            Log.d("User_ID:","User Created: ID = " + response.body().getId());
                            Intent intent = new Intent(newAccountActivity.this, birthAndCountryActivity.class);
                            intent.putExtra("email", email1.getText().toString());
                            intent.putExtra("password", password1.getText().toString());
                            startActivity(intent);
                            finish();
                        } else {
                            Log.d("Error: ", "Failed to create user: " + response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserOut> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }

    public String checkInputsEditText(TextInputEditText email1, TextInputEditText email2, TextInputEditText password1,
                                    TextInputEditText password2){
        String email1Str = email1.getText().toString();
        if(!Patterns.EMAIL_ADDRESS.matcher(email1Str).matches()){
            return "please enter a correct email address";
        }
        String email2Str = email2.getText().toString();

        if (!email1Str.equals(email2Str)) return ("Emails must be the same");

        String password1Str = password1.getText().toString();
        if(!PasswordUtils.isPasswordStrong(password1Str)){
            if(password1Str.length() < 8) return "Password should contain minimum 8 characters";
            return "Password should contain a capital letter, a digit and a special character";
        } else {
            String password2Str = password2.getText().toString();

            if (!password1Str.equals(password2Str)) return ("Passwords must be the same");

            return "checkCompleted";
        }
    }
}
