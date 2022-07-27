package com.example.ejemplo.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.ejemplo.R;

public class redireccionar_pasajero_conductor extends AppCompatActivity {
    private EditText etPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redireccionar_pasajero_conductor);
        etPhone = (EditText) findViewById(R.id.etPhone);

    }

    public void redireccionarVista(View v){
        Button btnRedireccionar = findViewById(R.id.btnSiguienteRedirec);
        EditText telefono = findViewById(R.id.etPhone);
        String numero = telefono.getText().toString();
        if (!numero.equals("")){
            RadioGroup rgClases = findViewById(R.id.rgClases);
            int id = rgClases.getCheckedRadioButtonId();
            switch (id){
                case R.id.rbPasajero:
                    btnRedireccionar.setOnClickListener(view -> {

                        Intent i = new Intent(getApplicationContext(), crear_pasajero.class);
                        i.putExtra("dato", etPhone.getText().toString());
                        i.putExtra("telefono",numero);
                        startActivity(i);


                    });
                    break;
                case R.id.rbConductor:
                    btnRedireccionar.setOnClickListener(view -> {
                        Intent i = new Intent(getApplicationContext(),crear_conductor.class);

                        i.putExtra("dato", etPhone.getText().toString());
                        i.putExtra("telefono",numero);
                        startActivity(i);


                    });
                    break;
                default:
                    Toast.makeText(redireccionar_pasajero_conductor.this, "Escoga una opción", Toast.LENGTH_SHORT).show();
            }
        }
    }



}


