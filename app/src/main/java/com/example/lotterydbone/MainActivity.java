package com.example.lotterydbone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    // GUI controls
    private EditText etName, etLocation;
    private Button btSave;

    // Validation of name and location
    public static boolean validateInput(String txt) {
        String regex = "[a-zA-Z]+";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(txt);
        return matcher.find();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get references to the EditText and Button
        etName = findViewById(R.id.etName);
        etLocation = findViewById(R.id.etLocation);
        btSave = findViewById(R.id.btSave);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etName.getText().toString();
                String location = etLocation.getText().toString();
                if (validateInput(username) && validateInput(location)) {
                    DBHandler dbHandler = new DBHandler(MainActivity.this);
                    dbHandler.insertUserDetails(username, location);
                    Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Details inserted successfully!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "Please insert name and city!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Clear old entries when activity is resumed
        etName.setText("");
        etLocation.setText("");
        // Start with name again
        etName.requestFocus();
    }
}
