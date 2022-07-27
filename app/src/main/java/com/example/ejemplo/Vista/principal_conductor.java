package com.example.ejemplo.Vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ejemplo.R;
import com.example.ejemplo.Vista.Fragmento.clasificacion;
import com.example.ejemplo.Vista.Fragmento.misIngresos;
import com.example.ejemplo.Vista.Fragmento.pago;
import com.example.ejemplo.Vista.Fragmento.solicitudes;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
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
        fragmentReplace(new solicitudes());

        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.opSolicitudes:
                    fragmentReplace(new solicitudes());
                    Toast.makeText(this, "Estoy en las solicitudes", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.opIngresos:
                    fragmentReplace(new misIngresos());
                    Toast.makeText(this, "Estoy en los ingresos", Toast.LENGTH_SHORT).show();
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
        if (item.getItemId() == R.id.opPerfil){
            //fragmentReplace(new perfil);
        }else if (item.getItemId() == R.id.opVehiculo){

        }else if (item.getItemId() == R.id.opCuenta){

        }else if (item.getItemId() == R.id.opConfiguracion){

        }else if (item.getItemId() == R.id.opSeguridad){

        }else if (item.getItemId() == R.id.opSoporte){

        }else if (item.getItemId() == R.id.opAyuda){

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