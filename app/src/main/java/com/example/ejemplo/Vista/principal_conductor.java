package com.example.ejemplo.Vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ejemplo.R;
import com.example.ejemplo.Vista.Fragmento.clasificacion;
import com.example.ejemplo.Vista.Fragmento.misIngresos;
import com.example.ejemplo.Vista.Fragmento.pago;
import com.example.ejemplo.Vista.Fragmento.solicitudes;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class principal_conductor extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout dl;
    Toolbar toolbar;
    NavigationView nav;
    BottomNavigationView bottomNav;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_conductor);
        Init();
        dl = findViewById(R.id.dl);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                dl,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        dl.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        //fragmentReplace(new solicitudes());

        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.opSolicitudes:
                    fragmentReplace(new solicitudes());
                    break;
                case R.id.opIngresos:
                    fragmentReplace(new misIngresos());
                    break;
                case R.id.opClasificacion:
                    fragmentReplace(new clasificacion());
                    break;
                case R.id.opPago:
                    fragmentReplace(new pago());
                    break;
            }
            return true;
        });
    }

    private void Init(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nav = findViewById(R.id.nav);
        bottomNav = findViewById(R.id.bottomNav);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        dl.closeDrawer(GravityCompat.START);
        if (item.getItemId() == R.id.opPerfil){

        }else if (item.getItemId() == R.id.opVehiculo){
            Intent i = new Intent(this, miVehiculo.class);
            startActivity(i);
        }else if (item.getItemId() == R.id.opCuenta){
            Intent i = new Intent(this, cuentaB.class);
            startActivity(i);
        }else if (item.getItemId() == R.id.opConfiguracion){
            Intent i = new Intent(this, miVehiculo.class);
            startActivity(i);
        }else if (item.getItemId() == R.id.opSeguridad){
            Intent i = new Intent(this, miVehiculo.class);
            startActivity(i);
        }else if (item.getItemId() == R.id.opSoporte){
            Intent i = new Intent(this, miVehiculo.class);
            startActivity(i);
        }else if (item.getItemId() == R.id.opAyuda){
            Intent i = new Intent(this, miVehiculo.class);
            startActivity(i);
        }
        return false;
    }
    private void fragmentReplace(Fragment fragment){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}