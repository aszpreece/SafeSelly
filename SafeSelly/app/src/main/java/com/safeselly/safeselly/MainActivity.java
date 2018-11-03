package com.safeselly.safeselly;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a fake list of earthquakes.
        ArrayList<Crime> earthquakes = QueryUtils.extractCrimes();

        // Find a reference to the {@link ListView} in the layout
        final ListView crimeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link EarthquakeAdapter} that takes a list of earthquakes as input
        final CrimeAdapter adapter = new CrimeAdapter(this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        crimeListView.setAdapter(adapter);
    }
}
