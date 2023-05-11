package com.foysaltech.drawersliding;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapaFragment extends Fragment implements OnMapReadyCallback {

    public static final int REQUEST_CODE = 1;
    SupportMapFragment mapFragment;
    GoogleMap mMap;
    Marker marker;
    private TextView txt_paso1,txt_paso2, ViewPaso1,ViewPaso2;
    private LinearLayout seccion1,seccion2;
    private Button btn_siguiente;

    double lat, longitude;
    private LocationManager locationManager;

    public MapaFragment() {

    }

    public static MapaFragment newInstance(String param1, String param2) {
        MapaFragment fragment = new MapaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
ObtenerCoordendasActual();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_mapa, container, false);
        txt_paso1=view.findViewById(R.id.txt_paso1);
        txt_paso2=view.findViewById(R.id.txt_paso2);
        ViewPaso1=view.findViewById(R.id.ViewPaso1);
        ViewPaso2=view.findViewById(R.id.ViewPaso2);

        txt_paso2=view.findViewById(R.id.txt_paso2);
        seccion1=view.findViewById(R.id.seccion1);
        seccion2=view.findViewById(R.id.seccion2);
        btn_siguiente=view.findViewById(R.id.btn_1);
        btn_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewPaso1.setTextColor(Color.parseColor("#c2c2c2"));
                txt_paso1.setTextColor(Color.parseColor("#c2c2c2"));
                txt_paso2.setBackgroundResource(0);
                seccion2.setVisibility(View.VISIBLE);
                seccion1.setVisibility(View.GONE);
                txt_paso2   .setTextColor(Color.parseColor("#FF018786"));

                ViewPaso2   .setTextColor(Color.parseColor("#292929"));


            }
        });
        return view;
    }

    @Override
    public void onStart() {
        mapFragment =  (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFrag);
        mapFragment.getMapAsync(this);

        super.onStart();
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //startLocService();
                } else {
                    Toast.makeText(getContext(), "Give me permissions", Toast.LENGTH_LONG).show();
                }
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onMapReady(GoogleMap googleMap) {
      //  Coordenadas coordenadas = new Coordenadas();
        mMap = googleMap;
        //lat = guardar.pedido.get(0).getLatitud();
       // longitude = guardar.pedido.get(0).getLongitul();
        LatLng latLng = new LatLng(lat,longitude);
        //Toast.makeText(getContext(), lat+""+longitude, Toast.LENGTH_SHORT).show();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        marker = mMap.addMarker(markerOptions);
     mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void ObtenerCoordendasActual() {


        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        } else {

            getCoordenada();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void getCoordenada() {

        try {

            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(1000);
            locationRequest.setFastestInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            LocationServices.getFusedLocationProviderClient(getContext()).requestLocationUpdates(locationRequest, new LocationCallback() {
                @Override
                public void onLocationAvailability(@NonNull LocationAvailability locationAvailability) {
                    super.onLocationAvailability(locationAvailability);
                }

                @Override
                public void onLocationResult(@NonNull LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    LocationServices.getFusedLocationProviderClient(getContext()).removeLocationUpdates(this);
                    if (locationResult != null && locationResult.getLocations().size() > 0) {
                        int latestLocationIndex = locationResult.getLocations().size() - 1;
                        Toast.makeText(getContext(), lat+""+longitude, Toast.LENGTH_SHORT).show();

                        lat = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                        longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();

                    }
                    }
            }, Looper.myLooper());
        } catch (Exception ex) {
            System.out.println("Error es :" + ex);
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}