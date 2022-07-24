package com.example.ejemplo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.ejemplo.databinding.ActivityMapsBinding;
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
    Polyline polyline = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

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
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE_FROM && resultCode == RESULT_OK){
            Place place = Autocomplete.getPlaceFromIntent(data);
            etUbiActual.setText(place.getAddress());
            mFromLatLng = place.getLatLng();
            setMarkerFrom(mFromLatLng);
        }else if (resultCode == AutocompleteActivity.RESULT_ERROR){
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE_TO && resultCode == RESULT_OK){
            Place place = Autocomplete.getPlaceFromIntent(data);
            etUbiDestino.setText(place.getAddress());
            mToLatLng = place.getLatLng();
            setMarkerTo(mToLatLng);
        }else if (resultCode == AutocompleteActivity.RESULT_ERROR){
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();

        }
        if (etUbiActual != null && etUbiDestino != null){
            String origin = etUbiActual.getText().toString();
            String destination = etUbiDestino.getText().toString();
            if (origin.isEmpty() || destination.isEmpty()){
                return;
            }
            try {
                String originEncoded = URLEncoder.encode(origin, "utf-8");
                String destinationEncoded = URLEncoder.encode(destination, "utf-8");
                sFromLocation = originEncoded;
                sToLocation = destinationEncoded;
                callDirectionsAPI();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            callDirectionsAPI();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }
    private Marker addMarker(LatLng latLng, String title){
        MarkerOptions marker = new MarkerOptions()
                .position(latLng)
                .title(title);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f));
        return mMap.addMarker(marker);
    }
    public void setMarkerFrom(LatLng latLng){
        if (markerFrom != null){
            markerFrom.remove();
        }
        markerFrom = addMarker(latLng, "Desde");
    }
    public void setMarkerTo(LatLng latLng){
        if (markerTo != null){
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
        Call<Direction> call = api.getDirection(sFromLocation,sToLocation,"AIzaSyAnqyuc34HCfl0sIDOhQ2fYs7LJHhozCa8");

        call.enqueue(new Callback<Direction>() {
            @Override
            public void onResponse(@NonNull Call<Direction> call, @NonNull Response<Direction> response) {
                assert response.body() != null;
                for (Step step : response.body().getRoutes().get(0).getLegs().get(0).getSteps()){
                    polyline = step.getPolyline();
                    List<LatLng> points = PolyUtil.decode(polyline.getPoints());

                }
            }

            @Override
            public void onFailure(@NonNull Call<Direction> call, @NonNull Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }

}