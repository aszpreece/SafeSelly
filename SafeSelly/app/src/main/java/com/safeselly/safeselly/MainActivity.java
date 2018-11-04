package com.safeselly.safeselly;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();

        setBottomNavigation();

//        // Create a fake list of earthquakes.
//        ArrayList<Crime> earthquakes = QueryUtils.extractCrimes();
//
//        // Find a reference to the {@link ListView} in the layout
//        final ListView crimeListView = (ListView) findViewById(R.id.list);
//
//        // Create a new {@link EarthquakeAdapter} that takes a list of earthquakes as input
//        final CrimeAdapter adapter = new CrimeAdapter(this, earthquakes);
//
//        // Set the adapter on the {@link ListView}
//        // so the list can be populated in the user interface
//        crimeListView.setAdapter(adapter);
    }

    private void setBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.navigation_view);
        bottomNav.setSelectedItemId(R.id.navigation_map);
        bottomNav.getMenu().findItem(R.id.navigation_map).setChecked(true);
        MapFragment fragment = new MapFragment();
        loadMapFragment(fragment);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_map:
                        MapFragment fragment = new MapFragment();
                        loadMapFragment(fragment);
                        break;
                    case R.id.navigation_crimes:
                        Fragment newFragment = new CrimeListFragment();
                        loadFragment(newFragment);
                        break;
                    case R.id.navigation_help:
                        Fragment newNewFragment = new InfoFragment();
                        loadFragment(newNewFragment);
                        break;
                }
                return true;
            }
        });
    }


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void loadMapFragment(MapFragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
