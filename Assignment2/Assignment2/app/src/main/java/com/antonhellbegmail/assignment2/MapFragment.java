package com.antonhellbegmail.assignment2;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.graphics.drawable.Icon;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

import static java.lang.Float.parseFloat;

/**
 * Created by Anton on 2017-09-29.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private MapView map;
    private GoogleMap googleMap;
    private LocationManager locationManager;
    private double startLat;
    private double startLong;
    private float[] markerArray = {BitmapDescriptorFactory.HUE_AZURE, BitmapDescriptorFactory.HUE_BLUE, BitmapDescriptorFactory.HUE_MAGENTA, BitmapDescriptorFactory.HUE_VIOLET,
    BitmapDescriptorFactory.HUE_CYAN, BitmapDescriptorFactory.HUE_ORANGE, BitmapDescriptorFactory.HUE_ROSE};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.map_fragment, container, false);
        initializeComponents(rootView);
        map.onCreate(savedInstanceState);
        locationManager = (LocationManager) getActivity().getSystemService(getContext().LOCATION_SERVICE);
        return rootView;
    }

    @Override
    public void onLowMemory() {
        map.onLowMemory();
        super.onLowMemory();
    }

    @Override
    public void onPause() {
        map.onPause();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        map.onDestroy();
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        map.onResume();
        super.onResume();
    }

    private void initializeComponents(View rootView) {
        map = (MapView) rootView.findViewById(R.id.map);
        map.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }

        Criteria mCritera = new Criteria();
        String bestProvider = String.valueOf(locationManager.getBestProvider(mCritera, true));

        this.googleMap = googleMap;
        ((MainActivity)getActivity()).getController().setMarkers();

        Location mLocation = locationManager.getLastKnownLocation(bestProvider);
        if(mLocation != null){
            startLat = mLocation.getLatitude();
            startLong = mLocation.getLongitude();
            LatLng currentLoc = new LatLng(startLat, startLong);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 13));
            ((MainActivity)getActivity()).getController().setStartPosition(startLong, startLat);
        }

    }


    public void placeMarker(ArrayList<Member> memberList){


        for(int i = 0; i < memberList.size(); i++){
            Log.d("MAPFRAGMENT", "LATITUDE" + memberList.get(i).getLatitude());
            Log.d("MAPFRAGMENT", "LONGITUDE" + memberList.get(i).getLongitude());
            LatLng pos = new LatLng(parseFloat(memberList.get(i).getLatitude()), parseFloat(memberList.get(i).getLongitude()));
            MarkerOptions mark = new MarkerOptions().position(pos).title(memberList.get(i).getName());
            mark.icon(BitmapDescriptorFactory.defaultMarker(markerArray[i % markerArray.length]));
            googleMap.addMarker(mark);
        }

    }

    public double getStartLat() {
        return startLat;
    }

    public void setStartLat(double startLat) {
        this.startLat = startLat;
    }

    public double getStartLong() {
        return startLong;
    }

    public void setStartLong(double startLong) {
        this.startLong = startLong;
    }
}
