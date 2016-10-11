package com.bis_idea.blacklist;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MapFragmentClass extends Fragment implements OnMapReadyCallback {

    GoogleMap googleMap;
    private MapView mMapView = null;
    private ArrayList<String[]> coordinates = new ArrayList<>();

    public static MapFragmentClass newInstance() {
        Bundle args = new Bundle();
        MapFragmentClass fragment = new MapFragmentClass();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapsInitializer.initialize(MapFragmentClass.this.getActivity().getBaseContext());
        try {
            parser(new ParseTask().execute().get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.map_layout, container, false);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
        return v;
    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        googleMap = gMap;
        googleMap.setMyLocationEnabled(true);
        double x = Double.parseDouble(coordinates.get(0)[0]);
        double y = Double.parseDouble(coordinates.get(0)[1]);
        LatLng sydney = new LatLng(x, y);
        googleMap.addMarker(new MarkerOptions()
                .position(sydney).title(coordinates.get(0)[2])
                .snippet(coordinates.get(0)[3])
                .draggable(true));

        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(15).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        //перемещение маркера
        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {}

            @Override
            public void onMarkerDragEnd(Marker marker) {
                LatLng sydney = marker.getPosition();
                googleMap.addMarker(new MarkerOptions()
                        .position(sydney).title(sydney.toString())
                        .snippet(sydney.toString()));
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(15).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }

            @Override
            public void onMarkerDrag(Marker marker) {}
        });
    }

    public void parser(String strJson) {
        coordinates.clear();
        try {
            JSONArray cor = new JSONArray(strJson);
            for (int i = 0; i < cor.length(); i++) {
                JSONObject place = cor.getJSONObject(i);
                String[] str = {place.getString("LatLngX"), place.getString("LatLngY"),place.getString("position"),place.getString("snippet")};
                coordinates.add(str);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}