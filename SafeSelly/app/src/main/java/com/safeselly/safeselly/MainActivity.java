package com.safeselly.safeselly;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    protected void refreshCrimes(CrimeAdapter a) {
        new PollCrimesActivity(a).execute("");
    }
}
