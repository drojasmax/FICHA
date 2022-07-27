package com.example.ejemplo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ejemplo.Modelo.Pasajero;

import java.util.List;

public class Adapter extends ArrayAdapter<Pasajero> {
    Context context;
    List<Pasajero> arraypasajeros;

    public Adapter(@NonNull Context context, List<Pasajero>arraypasajeros) {
        super(context, R.layout.activity_perfil, arraypasajeros);
        this.context = context;
        this.arraypasajeros = arraypasajeros;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_perfil, null, true);

        EditText etNombreP= view.findViewById(R.id.etNombreP);
        EditText etApellidoP= view.findViewById(R.id.etApellidoP);
        EditText etTelefonoP= view.findViewById(R.id.etTelefonoP);
        EditText etCorreoP= view.findViewById(R.id.etCorreoP);

        etNombreP.setText(arraypasajeros.get(position).getNombre());
        etApellidoP.setText(arraypasajeros.get(position).getApellido());
        etTelefonoP.setText(arraypasajeros.get(position).getTelefono());
        etCorreoP.setText(arraypasajeros.get(position).getCorreo());

        return super.getView(position, convertView, parent);
    }

}
