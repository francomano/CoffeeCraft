package com.example.coffeecraft;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class birthAndCountryActivity extends AppCompatActivity {

    MaterialTextView dateTxt;
    String selectedCountry = "--select a country--";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.birthandcountryactivity);

        Intent intent_ = getIntent();

        String email = intent_.getStringExtra("email");
        String password = intent_.getStringExtra("password");

        MaterialButton dateBtn = findViewById(R.id.dateBtn);
        MaterialButton finishBtn = findViewById(R.id.finishBtn);
        MaterialTextView nodate = findViewById(R.id.nodate);
        MaterialTextView nocountry = findViewById(R.id.nocountry);

        dateTxt = findViewById(R.id.dateTxt);
        dateBtn.setOnClickListener(v -> clickButton());
        finishBtn.setOnClickListener( v -> {
            if (!Objects.equals(selectedCountry, "--select a country--") && !dateTxt.getText().toString().equals("")) {
                nodate.setText("");
                nocountry.setText("");
                Intent intent = new Intent(this, loginActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                intent.putExtra("country", selectedCountry);
                intent.putExtra("date", dateTxt.getText().toString());
                // save new account in the db here
                startActivity(intent);
                finish();
            } else if (dateTxt.getText().toString().equals("")){
                nodate.setText("please enter your birth date");
            } else {
                nodate.setText("");
                nocountry.setText("please select your country");
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.country_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.country_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCountry = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void clickButton(){

        final Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            dateTxt.setText(dayOfMonth + "-" +(month+1)+ "-" +year);
        }, y, m, d);

        dialog.show();
    }
}
