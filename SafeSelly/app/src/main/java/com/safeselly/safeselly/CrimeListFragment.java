package com.safeselly.safeselly;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

public class CrimeListFragment extends Fragment {

    public static HashMap<String, String> outcomeDescriptions = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_view, container, false);

        loadOutcomeData();
        // Find a reference to the {@link ListView} in the layout
        final ListView crimeListView = rootView.findViewById(R.id.list);

        ArrayList<Crime> c = new ArrayList<Crime>();
        // Create a new {@link EarthquakeAdapter} that takes a list of earthquakes as input
        final CrimeAdapter adapter = new CrimeAdapter(getContext(), c);

        refreshCrimes(adapter);
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        crimeListView.setAdapter(adapter);

        return rootView;
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
