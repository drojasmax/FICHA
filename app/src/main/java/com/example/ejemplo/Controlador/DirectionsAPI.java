package com.example.ejemplo.Controlador;

import com.example.ejemplo.Modelo.Direction;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DirectionsAPI {
    @GET("directions/json")
    Call<Direction> getDirection(@Query("origin") String origin, @Query("destination") String destination, @Query("key") String key);
}
