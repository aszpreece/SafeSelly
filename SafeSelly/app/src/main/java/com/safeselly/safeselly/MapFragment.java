package com.safeselly.safeselly;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MapFragment extends Fragment {

    private GoogleMap mMap;
    private Button switch_overlay;
    private Boolean switch_value;
    private ArrayList<Marker> marker_list;
    private TileOverlay mOverlay;

    private SupportMapFragment mapFragment;


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maps);
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//        switch_value = true;
//        switch_overlay = findViewById(R.id.switch_button);
//
//    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_maps, container, false);

        // Remove old code
        // SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        // don't recreate fragment everytime ensure last map location/state are maintained
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    //map object
                    mMap = googleMap;
                    try {
                        //Attempts to set the style of the map
                        boolean success = googleMap.setMapStyle(
                                MapStyleOptions.loadRawResourceStyle(
                                        getContext(), R.raw.style_json));
                        if (!success) {
                            Log.e("TAG", "Style parsing failed.");
                        }
                    } catch (Resources.NotFoundException e) {
                        Log.e("TAG", "Can't find style. Error: ", e);
                    }
                    //Positions the camera over selly oak
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(52.441812,-1.935580)));
                    mMap.moveCamera(CameraUpdateFactory.zoomTo(13.0f));
                    addMarkerMap();
                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            for(Marker m : marker_list){
                                marker.hideInfoWindow();
                            }
                            marker.showInfoWindow();
                            return false;
                        }
                    });
                    hideMarkers();
                    addHeatMap();


                }
            });
        }

        // R.id.map is a FrameLayout, not a Fragment
        getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();

        switch_value = true;
        switch_overlay = rootView.findViewById(R.id.switch_button);


        switch_overlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switch_value){
                    remove_overlay();
                    showMarkers();
                    switch_overlay.setText(R.string.button_value_two);
                    switch_value = false;
                }
                else{
                    hideMarkers();
                    addHeatMap();
                    switch_overlay.setText(R.string.button_value_one);
                    switch_value = true;
                }
            }
        });



        return rootView;
    }















    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    private void addHeatMap() {
        ArrayList<LatLng> list = null;

        try {
            //creates a list out of the reports
            list = readItems(R.raw.crime_reports);
        } catch (JSONException e) {
            Toast.makeText(getContext(), "Problem reading list of locations.", Toast.LENGTH_LONG).show();
            Log.e("TAG", "reading list failed");
        }

        // Create a heat map tile provider
        HeatmapTileProvider mProvider = new HeatmapTileProvider.Builder()
                .data(list)
                .build();
        //sets the radius of crimes
        mProvider.setRadius(40);
        // Add a tile overlay to the map, using the heat map tile provider.
        mOverlay =  mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
    }

    private void remove_overlay(){
        mOverlay.remove();
    }
    private void addMarkerMap(){
        ArrayList<LatLng> list;
        marker_list = new ArrayList<>();
        try {
            //creates a list out of the reports
            list = readItems(R.raw.crime_reports);
            for(LatLng coord: list){
                Marker marker = (mMap.addMarker(new MarkerOptions().title(
                        "Crime").position(coord)));
                marker.setTag(coord);
                marker_list.add(marker);
            }

        } catch (JSONException e) {
            Toast.makeText(getContext(), "Problem reading list of locations.", Toast.LENGTH_LONG).show();
            Log.e("TAG", "reading list failed");
        }
    }

    private void hideMarkers(){
        for(Marker m : marker_list){
            m.setVisible(false);
        }
    }
    private void showMarkers(){
        for(Marker m : marker_list){
            m.setVisible(true);
        }
    }

    private ArrayList<LatLng> readItems(int resource) throws JSONException {
        //for each line it gets the lat and long
        ArrayList<LatLng> list = new ArrayList<LatLng>();
        InputStream inputStream = getResources().openRawResource(resource);
        String json = new Scanner(inputStream).useDelimiter("\\A").next();
        JSONArray array = new JSONArray(json);
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            double lat = object.getDouble("lat");
            double lng = object.getDouble("lng");
            list.add(new LatLng(lat, lng));
        }
        return list;
    }
}
