package com.example.ejemplo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class redireccionar_pasajero_conductor extends AppCompatActivity {
private EditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redireccionar_pasajero_conductor);


        etPhone = (EditText) findViewById(R.id.etPhone);

    }

        //Metodo para boton Enviar
        public void Enviar(View view){
            Intent i = new Intent(this, crear_conductor.class);
            i.putExtra("dato", etPhone.getText().toString());
            startActivity(i);

        }

    }