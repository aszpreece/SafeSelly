package com.safeselly.safeselly;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static HashMap<String, String> outcomeDescriptions = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadOutcomeData();
        // Find a reference to the {@link ListView} in the layout
        final ListView crimeListView = findViewById(R.id.list);

        ArrayList<Crime> c = new ArrayList<Crime>();
        // Create a new {@link EarthquakeAdapter} that takes a list of earthquakes as input
        final CrimeAdapter adapter = new CrimeAdapter(this, c);

        refreshCrimes(adapter);
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        crimeListView.setAdapter(adapter);
    }

    private void loadOutcomeData() {
        InputStream is = getResources().openRawResource(R.raw.outcome_categories);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                //set splitter
                String[] tokens = line.split(",");
                outcomeDescriptions.put(tokens[0], tokens[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
        protected void refreshCrimes(CrimeAdapter a) {
        new PollCrimesActivity(a).execute("");
    }
}
