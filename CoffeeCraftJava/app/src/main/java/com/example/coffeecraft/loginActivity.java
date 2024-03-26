package com.example.coffeecraft;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class loginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) { //this method is called by every activity
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

        ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == loginActivity.RESULT_OK) {
                    finish();
                }
            });

        createAccount.setOnClickListener(view -> {
            mStartForResult.launch(intent2);
            //this is the line of code that destroy the buttons of the login and create the ones of the create account
        });
    }
}
