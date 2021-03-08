package com.example.lotterydbone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailsActivity extends AppCompatActivity {

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
        /*
        In Android, a SimpleAdapter is an easy adapter to map static data to views defined in an XML file (layout).
        In Android you can specify the data backing to a list as an ArrayList of Maps (HashMap or other).
        Each entry in a ArrayList is corresponding to one row of a list. The Map contains the data for each row.
        For parameter details of a SimpleArray see https://abhiandroid.com/ui/simpleadapter.html
        */
        SimpleAdapter adapter = new SimpleAdapter(DetailsActivity.this, playerList, R.layout.list_row, new String[]{"name", "location"}, new int[]{R.id.tvName, R.id.tvLocation});
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