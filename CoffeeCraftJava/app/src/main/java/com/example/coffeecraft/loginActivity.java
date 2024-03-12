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

        Intent intent = new Intent(this, mainActivity.class);

        logInButton.setOnClickListener(view -> {
            intent.putExtra("email", email.getText().toString());
            intent.putExtra("password", password.getText().toString());
            startActivity(intent);
            loginActivity.this.finish();
        });
    }
}
