package com.example.coffeecraft;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class loginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);
        logInBtnListener();
    }

    public void logInBtnListener(){
        TextInputEditText email = findViewById(R.id.emailTextField);
        TextInputEditText password = findViewById(R.id.passwordTextField);
        MaterialButton logInButton = findViewById(R.id.buttonLogIn);
        MaterialButton createAccount = findViewById(R.id.createAccountButton);

        Intent intent1 = new Intent(this, mainActivity.class);

        logInButton.setOnClickListener(view -> {
            intent1.putExtra("email", email.getText().toString());
            intent1.putExtra("password", password.getText().toString());
            startActivity(intent1);
            loginActivity.this.finish();
        });

        Intent intent2 = new Intent(this, newAccountActivity.class);

        createAccount.setOnClickListener(view -> {
            startActivity(intent2);
            loginActivity.this.finish();
        });
    }
}
