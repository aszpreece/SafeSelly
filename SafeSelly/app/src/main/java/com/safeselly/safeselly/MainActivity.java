package com.safeselly.safeselly;

import android.content.Intent;
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
        loadMapFragment(new MapFragment());
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_map:
                        loadMapFragment(new MapFragment());

                        break;
                    case R.id.navigation_crimes:
                        loadFragment(new CrimeListFragment());
                        break;
                    case R.id.navigation_help:
                        loadFragment(new InfoFragment());
                        break;
                }
                return true;
            }
        });
    }


    private void loadFragment(Fragment fragment) {
        getSupportActionBar().show();
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }


    private void loadMapFragment(MapFragment fragment) {
        getSupportActionBar().hide();
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }


    public void callEmergency(View v) {
        // Opens an email app to send the order summary
        Intent intent = new Intent(Intent.ACTION_DIAL); // only phone dialer apps should handle this
        intent.setData(Uri.parse("tel:" + getString(R.string.emergency_number)));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void callNonEmergency(View v) {
        // Opens an email app to send the order summary
        Intent intent = new Intent(Intent.ACTION_DIAL); // only phone dialer apps should handle this
        intent.setData(Uri.parse("tel:" + getString(R.string.non_emergency_number)));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void emailSelly(View v) {
        // Opens an email app to send the order summary
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {getString(R.string.selly_police_email)});
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void callSecurityEmergency(View v) {
        // Opens an email app to send the order summary
        Intent intent = new Intent(Intent.ACTION_DIAL); // only phone dialer apps should handle this
        intent.setData(Uri.parse("tel:" + getString(R.string.uob_emergency_security_number)));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void callSecurityNonEmergency(View v) {
        // Opens an email app to send the order summary
        Intent intent = new Intent(Intent.ACTION_DIAL); // only phone dialer apps should handle this
        intent.setData(Uri.parse("tel:" + getString(R.string.uob_non_emergency_security_number)));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void emailSecurity(View v) {
        // Opens an email app to send the order summary
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {getString(R.string.security_services_email)});
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


}
