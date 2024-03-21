package com.example.coffeecraft;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import java.time.LocalDateTime;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import com.google.android.material.textview.MaterialTextView;

public class mainActivity extends AppCompatActivity {



    String email;
    String password;

    LocalDateTime now;
    String date;
    String time;

    private Button suggestCoffeeButton;
    private Spinner feelingsSpinner;
    private CheckBox sugarCheckBox;
    private CheckBox aromasCheckBox;
    private TextView debugTextView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        Intent intent = getIntent();

        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        System.out.println(email);

        showButtons(email, password);

    }

    @SuppressLint("SetTextI18n")
    public void showButtons(String email, String password) {
        // Initialize views
        suggestCoffeeButton = findViewById(R.id.suggestCoffeeButton);
        feelingsSpinner = findViewById(R.id.feelingsSpinner);
        sugarCheckBox = findViewById(R.id.sugarCheckBox);
        aromasCheckBox = findViewById(R.id.aromasCheckBox);
        debugTextView = findViewById(R.id.debugTextView);


        sugarCheckBox.setVisibility(View.VISIBLE);
        aromasCheckBox.setVisibility(View.VISIBLE);
        debugTextView.setVisibility(View.VISIBLE);

        // Set visibility of the suggestCoffeeButton and feelingsSpinner to VISIBLE
        suggestCoffeeButton.setVisibility(View.VISIBLE);
        feelingsSpinner.setVisibility(View.VISIBLE);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.feelings_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        feelingsSpinner.setAdapter(adapter);


        suggestCoffeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click event here
                // For example, you can perform some action when the button is clicked
                // Here, let's just display a toast message
                now = LocalDateTime.now();
                String date = now.getDayOfMonth() + " " + now.getMonth() + " " + now.getYear();
                String time = now.getHour() + ":" + now.getMinute();

                String selectedFeeling = feelingsSpinner.getSelectedItem().toString();

                int sugarChecked = sugarCheckBox.isChecked() ? 1 : 0;
                int aromasChecked = aromasCheckBox.isChecked() ? 1 : 0;

                List<String> outcomes = new ArrayList<>();
                outcomes.add(email);
                outcomes.add(String.valueOf(sugarChecked));
                outcomes.add(String.valueOf(aromasChecked));
                outcomes.add(time);
                outcomes.add(selectedFeeling);

                debugTextView.setText("Outcomes: " + outcomes.toString());

            }
        });
    }
}
