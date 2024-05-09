package com.example.coffeecraft;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
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
import java.util.stream.Collectors;

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
        MaterialTextView userInfoTextView = findViewById(R.id.userInfoTextView);
        userInfoTextView.setText(userInfo);

        // Button to return to MainActivity
        MaterialButton backButton = findViewById(R.id.backButton);
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

        try {
            getHistoric();
        } catch (FileNotFoundException e) {
            Toast.makeText(context, "No historic !", Toast.LENGTH_SHORT).show();
        }


    }

    public void getHistoric() throws FileNotFoundException {

        String filename = "historic.ser";
        HashMap<String, Integer> hashMap = null;

        try (FileInputStream fis = context.openFileInput(filename)) {
            ObjectInputStream ois = new ObjectInputStream(fis);
            hashMap = (HashMap<String, Integer>) ois.readObject();
            fis.close();
            ois.close();
        } catch (IOException | ClassCastException | ClassNotFoundException e){
            Toast.makeText(context, "No historic !", Toast.LENGTH_SHORT).show();
        }

        ListView listView;
        List<Map.Entry<String, Integer>> sortedEntries;

        if(hashMap != null){

            sortedEntries = new ArrayList<>(hashMap.entrySet());
            sortedEntries.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

            listView = findViewById(R.id.listViewCoffee);
            HistoricAdapter adapter = new HistoricAdapter(this, sortedEntries);
            listView.setAdapter(adapter);
            System.out.println(hashMap);
        }
    }
}
