package com.example.ejemplo.Vista;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ejemplo.Controlador.DirectionsAPI;
import com.example.ejemplo.Modelo.Direction;
import com.example.ejemplo.Modelo.Polyline;
import com.example.ejemplo.Modelo.Step;
import com.example.ejemplo.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.maps.android.PolyUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String sFromLocation, sToLocation;
    private static final int REQUEST_CODE_AUTOCOMPLETE_FROM = 1;
    private static final int REQUEST_CODE_AUTOCOMPLETE_TO = 2;
    EditText etUbiActual, etUbiDestino;
    LatLng mFromLatLng, mToLatLng;
    Marker markerFrom = null, markerTo = null;
    private LocationManager ubicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        miLocalizacion();

        Places.initialize(getApplicationContext(), "AIzaSyAnqyuc34HCfl0sIDOhQ2fYs7LJHhozCa8");

        etUbiActual = findViewById(R.id.etUbiActual);
        etUbiDestino = findViewById(R.id.etUbiDestino);
        etUbiActual.setFocusable(false);
        etUbiDestino.setFocusable(false);

        etUbiActual.setOnClickListener(view -> startAutocomplete(REQUEST_CODE_AUTOCOMPLETE_FROM));
        etUbiDestino.setOnClickListener(view -> startAutocomplete(REQUEST_CODE_AUTOCOMPLETE_TO));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    private void startAutocomplete(int requestCode) {
        Places.initialize(getApplicationContext(), "AIzaSyAnqyuc34HCfl0sIDOhQ2fYs7LJHhozCa8");
        // Fields of place data to return after the user has made a selection
        List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE_FROM && resultCode == RESULT_OK) {
            assert data != null;
            Place place = Autocomplete.getPlaceFromIntent(data);
            etUbiActual.setText(place.getAddress());
            mFromLatLng = place.getLatLng();
            setMarkerFrom(mFromLatLng);
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            assert data != null;
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE_TO && resultCode == RESULT_OK) {
            assert data != null;
            Place place = Autocomplete.getPlaceFromIntent(data);
            etUbiDestino.setText(place.getAddress());
            mToLatLng = place.getLatLng();
            setMarkerTo(mToLatLng);
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
        if (etUbiActual != null && etUbiDestino != null) {
            String origin = etUbiActual.getText().toString();
            String destination = etUbiDestino.getText().toString();
            if (origin.isEmpty() || destination.isEmpty()) {
                Toast.makeText(this, "Ingrese la dirección faltante", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                String originEncoded = URLEncoder.encode(origin, "utf-8");
                String destinationEncoded = URLEncoder.encode(destination, "utf-8");
                sFromLocation = originEncoded;
                sToLocation = destinationEncoded;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        callDirectionsAPI();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
    }

    private Marker addMarker(LatLng latLng, String title) {
        MarkerOptions marker = new MarkerOptions()
                .position(latLng)
                .title(title);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20f));
        return mMap.addMarker(marker);
    }

    public void setMarkerFrom(LatLng latLng) {
        if (markerFrom != null) {
            markerFrom.remove();
        }
        markerFrom = addMarker(latLng, "Desde");
    }

    public void setMarkerTo(LatLng latLng) {
        if (markerTo != null) {
            markerTo.remove();
        }
        markerTo = addMarker(latLng, "Hacia");
    }

    private void callDirectionsAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        DirectionsAPI api = retrofit.create(DirectionsAPI.class);
        Call<Direction> call = api.getDirection(sFromLocation, sToLocation, "AIzaSyAnqyuc34HCfl0sIDOhQ2fYs7LJHhozCa8");

        call.enqueue(new Callback<Direction>() {
            @Override
            public void onResponse(@NonNull Call<Direction> call, @NonNull Response<Direction> response) {
                assert response.body() != null;
                for (Step step : response.body().getRoutes().get(0).getLegs().get(0).getSteps()) {
                    Polyline polyline = step.getPolyline();
                    List<LatLng> points = PolyUtil.decode(polyline.getPoints());
                    mMap.addPolyline(new PolylineOptions().addAll(points).width(5).color(Color.GRAY));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Direction> call, @NonNull Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }

    private void miLocalizacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION
            }, 1000);
        }
        ubicacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = ubicacion.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (ubicacion != null){
            Log.d("Latitud",String.valueOf(location.getLongitude()));
            Log.d("Longitud",String.valueOf(location.getLatitude()));
        }
    }
}