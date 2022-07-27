package com.example.ejemplo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ejemplo.Modelo.Pasajero;

import java.util.ArrayList;

public class Perfil extends AppCompatActivity {


    Adapter adapter;
    public static ArrayList<Pasajero>pasajeroArrayList=new ArrayList<>();
    String url = "http://200.29.219.25:3307/duvan/conseguirPasajeros.php";
    Pasajero pasajero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        adapter = new Adapter(this, pasajeroArrayList);

    }
}