package com.food.food;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import  java.util.*;

public class MapActivity extends Activity {

    private GoogleMap googleMap;
    private ArrayList<CustomLocation> locations;


    private void initialMarkers(){
        locations = new ArrayList<>();
        locations.add(new CustomLocation(getResources().getDrawable(R.drawable.goldencoffee), "GoldenCofee", new LatLng(53.891702, 27.552007), "+375(25)5754444"));
        locations.add(new CustomLocation(getResources().getDrawable(R.drawable.falcone), "Falcone Restaurant", new LatLng(53.901997, 27.542701), "+375(29)3777776"));
        locations.add(new CustomLocation(getResources().getDrawable(R.drawable.lido), "Lido", new LatLng(53.916988, 27.585269), "8(017)2848208"));
    }

    private void setMarkers(){
        for(CustomLocation temp : locations) {
            googleMap.addMarker(new MarkerOptions()
                    .position(temp.getLatLng())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        createMapView();
        initialMarkers();
        setMarkers();
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                return true;
            }
        });

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                LayoutInflater inflater = (LayoutInflater)getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View viewCatalog = inflater.inflate(R.layout.window_info, null);

                CustomLocation cl = findCustomLocation(marker.getPosition(), locations);
                ImageView im = (ImageView) viewCatalog.findViewById(R.id.info_image);
                im.setImageDrawable(cl.getImage());

                TextView text = (TextView) viewCatalog.findViewById(R.id.info_text);
                StringBuilder sb =  new StringBuilder();
                sb.append(cl.getName() + "\n" + cl.getPhone());
                text.setText(sb.toString());
                text.setTextSize(15);
                text.setTextColor(getResources().getColor(R.color.black));

                return viewCatalog;
            }
        });
    }

    public static CustomLocation findCustomLocation(LatLng lat, ArrayList<CustomLocation> locations){
        for(CustomLocation temp : locations){
            if(temp.getLatLng().equals(lat)){
                return temp;
            }
        }
        return null;
    }

    private void createMapView(){
        try {
            if(googleMap == null){
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapView)).getMap();
                googleMap.setMyLocationEnabled(true);
                UiSettings uiSettings = googleMap.getUiSettings();
                uiSettings.setMyLocationButtonEnabled(true);
                uiSettings.setZoomControlsEnabled(true);
                uiSettings.setZoomGesturesEnabled(true);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(53.903234, 27.560410), 11.0f));
                if(googleMap == null) {
                    Toast.makeText(getApplicationContext(),
                            "Error creating map", Toast.LENGTH_SHORT).show();                }
            }
        } catch (NullPointerException exception){
            Log.e("mapApp", exception.toString());
        }
    }
}
