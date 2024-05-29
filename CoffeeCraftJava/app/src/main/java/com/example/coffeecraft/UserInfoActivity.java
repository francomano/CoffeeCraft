package com.example.coffeecraft;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.coffeecraft.Utils.HistoricAdapter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.view.View;


public class UserInfoActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        context = this.getBaseContext();

        // Get user info from intent
        Intent intent = getIntent();
        String userInfo = intent.getStringExtra("userInfo");
        String token = intent.getStringExtra("token");

        // Display user info
        TextView userInfoTextView = findViewById(R.id.userInfoTextView);
        userInfoTextView.setText(userInfo);

        // Button to return to MainActivity
        MaterialButton backButton = findViewById(R.id.backButton);
        MaterialButton reviewButton = findViewById(R.id.reviewButton);
        ImageButton notGood = findViewById(R.id.buttonNext);
        ImageButton good = findViewById(R.id.buttonBuy);
        LinearLayout review = findViewById(R.id.reviewView);
        MaterialTextView coffeetype = findViewById(R.id.coffeeType);

        backButton.setOnClickListener(v -> {
            // Create an intent to return to MainActivity
            Intent mainActivityIntent = new Intent(UserInfoActivity.this, MainActivity.class);
            // Pass the token back to MainActivity
            System.out.println(token);
            mainActivityIntent.putExtra("accessToken", token);
            // Start the MainActivity
            startActivity(mainActivityIntent);
            // Finish the current activity
            finish();
        });

        reviewButton.setOnClickListener(v -> {
            String lastCoffee = null;
            try {
                lastCoffee = getCoffeeType();
            } catch (FileNotFoundException ignore) {
            }
            if(!Objects.equals(lastCoffee, "")) {
                coffeetype.setText("The last prediction was " + lastCoffee);
                reviewButton.setVisibility(View.GONE);
                review.setVisibility(View.VISIBLE);
                notGood.setOnClickListener(v1 -> {
                    Toast.makeText(context, "Thank you for the feedback", Toast.LENGTH_SHORT).show();
                    review.setVisibility(View.GONE);
                    reviewButton.setVisibility(View.VISIBLE);
                });
                good.setOnClickListener(v2 -> {
                    Toast.makeText(context, "Thank you for the feedback", Toast.LENGTH_SHORT).show();
                    review.setVisibility(View.GONE);
                    reviewButton.setVisibility(View.VISIBLE);
                });
            } else {
                Toast.makeText(context, "You've never bought coffee !", Toast.LENGTH_SHORT).show();
            }
        });



        try {
            getHistoric();
        } catch (FileNotFoundException e) {
            Toast.makeText(context, "No historic !", Toast.LENGTH_SHORT).show();
        }


    }

    public String getCoffeeType() throws FileNotFoundException {
        String filename = "last.ser";
        String lastType;
        try (FileInputStream fis = context.openFileInput(filename)){
            ObjectInputStream ois = new ObjectInputStream(fis);
            lastType = (String) ois.readObject();
            fis.close();
            ois.close();
        } catch (IOException | ClassCastException | ClassNotFoundException e) {
            return "";
        }
        return lastType;
    }

    public void getHistoric() throws FileNotFoundException {

        String filename = "historic.ser";
        HashMap<String, Integer> hashMap = null;

        try (FileInputStream fis = context.openFileInput(filename)) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            hashMap = (HashMap<String, Integer>) ois.readObject();
            fis.close();
            ois.close();
        } catch (IOException | ClassCastException | ClassNotFoundException e) {
            Toast.makeText(context, "No historic !", Toast.LENGTH_SHORT).show();
        }

        TableLayout tableLayout = findViewById(R.id.tableLayout);

        if (hashMap != null) {

            List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(hashMap.entrySet());
            sortedEntries.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

            for (int i = 0; i < sortedEntries.size(); i++) {
                Map.Entry<String, Integer> entry = sortedEntries.get(i);
                String name = entry.getKey();
                Integer score = entry.getValue();

                // Create a new row
                TableRow row = new TableRow(this);

                // Rank TextView
                TextView rankTextView = new TextView(this);
                rankTextView.setText(String.valueOf(i + 1)); // Rank starts from 1
                rankTextView.setTextColor(ContextCompat.getColor(this, R.color.black)); // Set text color
                row.addView(rankTextView); // Add to row

                // Name TextView
                TextView nameTextView = new TextView(this);
                nameTextView.setText(name);
                nameTextView.setTextColor(ContextCompat.getColor(this, R.color.black)); // Set text color
                row.addView(nameTextView); // Add to row

                // Score TextView
                TextView scoreTextView = new TextView(this);
                scoreTextView.setText(String.valueOf(score));
                scoreTextView.setTextColor(ContextCompat.getColor(this, R.color.black)); // Set text color
                row.addView(scoreTextView); // Add to row

                // Add row to the table layout
                tableLayout.addView(row);
            }
        }
    }
}
