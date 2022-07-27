package com.example.ejemplo.Vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.ejemplo.R;
import com.google.android.material.navigation.NavigationView;

public class principal_conductor extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout dl;
    Toolbar toolbar;
    NavigationView nav;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_conductor);
        Init();
        nav.setNavigationItemSelectedListener(this);

        //Fragmento principal
    }

    private void Init(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nav = findViewById(R.id.nav);
        dl = findViewById(R.id.dl);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                dl,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        dl.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.opPerfil){

        }else if (item.getItemId() == R.id.opVehiculo){

        }else if (item.getItemId() == R.id.opCuenta){

        }else if (item.getItemId() == R.id.opConfiguracion){

        }else if (item.getItemId() == R.id.opSeguridad){

        }else if (item.getItemId() == R.id.opSoporte){

        }else if (item.getItemId() == R.id.opAyuda){

        }
        return false;
    }
}