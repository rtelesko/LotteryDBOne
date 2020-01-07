package com.example.lotterydbone;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailsActivity extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        DBHandler db = new DBHandler(this);

        // Scenario 1: Retrieve only the player names
        // ArrayList<String> playerList = db.getPlayerNames();
        // ListView lv = (ListView) findViewById(R.id.lvPlayer);
        // ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, playerList);
        // lv.setAdapter(arrayAdapter);

        // Scenario 2: Retrieve all player information
        ArrayList<HashMap<String, String>> playerList = db.getPlayers();
        ListView lvPlayer = findViewById(R.id.lvPlayer);
        ListAdapter adapter = new SimpleAdapter(DetailsActivity.this, playerList, R.layout.list_row, new String[]{"name", "location"}, new int[]{R.id.tvName, R.id.tvLocation});
        lvPlayer.setAdapter(adapter);

        // Returning to Main Activity
        Button back = findViewById(R.id.btBack);
        // Go back to Main Activity
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}