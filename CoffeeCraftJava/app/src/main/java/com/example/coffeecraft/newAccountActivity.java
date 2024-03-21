package com.example.coffeecraft;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import org.w3c.dom.Text;

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
                Intent intent = new Intent(this, mainActivity.class);
                intent.putExtra("email", email1.getText().toString());
                intent.putExtra("password", password1.getText().toString());
                startActivity(intent);
                newAccountActivity.this.finish();
            }
        });
    }

    public String checkInputsEditText(TextInputEditText email1, TextInputEditText email2, TextInputEditText password1,
                                    TextInputEditText password2){
        String email1Str = email1.getText().toString();
        String email2Str = email2.getText().toString();

        if (!email1Str.equals(email2Str)) return ("Emails must be the same");

        String password1Str = password1.getText().toString();
        String password2Str = password2.getText().toString();

        if(!password1Str.equals(password2Str)) return ("Passwords must be the same");

        return "checkCompleted";
    }
}