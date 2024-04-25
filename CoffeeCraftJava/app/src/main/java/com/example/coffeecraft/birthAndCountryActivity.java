package com.example.coffeecraft;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coffeecraft.Utils.GetAge;
import com.example.coffeecraft.Utils.GetCountryCode;
import com.example.coffeecraft.model.UserCreateOpen;
import com.example.coffeecraft.model.UserOut;
import com.example.coffeecraft.network.ApiService;
import com.example.coffeecraft.network.RetrofitClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.Calendar;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

                UserCreateOpen newUser = new UserCreateOpen();
                newUser.setEmail(email);
                newUser.setPassword(password);
                newUser.setFullName("New User");
                newUser.setAge(GetAge.getAge(dateTxt.getText().toString()));
                newUser.setCountry(new GetCountryCode().getCode(selectedCountry));

                Log.d("email: ", email);
                Log.d("password: ", password);
                Log.d("age: ", String.valueOf(GetAge.getAge(dateTxt.getText().toString())));
                Log.d("country", selectedCountry);

                ApiService apiService = RetrofitClient.getClient("http://10.0.2.2:80/api/v1/");
                Call<UserOut> call_2 = apiService.createUserOpen(newUser);
                call_2.enqueue(new Callback<UserOut>() {
                    @Override
                    public void onResponse(Call<UserOut> call, Response<UserOut> response) {
                        if (response.isSuccessful()) {
                            Log.d("User_ID:","User Created: ID = " + response.body().getId());
                            Intent intent = new Intent(birthAndCountryActivity.this, loginActivity.class);
                            intent.putExtra("email", email);
                            intent.putExtra("password", password);
                            intent.putExtra("country", selectedCountry);
                            intent.putExtra("date", dateTxt.getText().toString());
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
            String monthStr = "";
            if(month < 10){
                monthStr = "0"+(String.valueOf(month+1));
            } else {
                monthStr = String.valueOf(month+1);
            }
            String dayStr = "";
            if(dayOfMonth < 10){
                dayStr = "0"+String.valueOf(dayOfMonth);
            } else {
                dayStr = String.valueOf(dayOfMonth);
            }
            dateTxt.setText(year + "-" +monthStr+ "-" +dayStr);
        }, y, m, d);

        dialog.show();
    }
}
