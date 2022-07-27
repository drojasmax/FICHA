package com.example.ejemplo.Controlador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ejemplo.Modelo.Solicitud;
import com.example.ejemplo.R;

import java.util.List;

public class Adaptador extends ArrayAdapter<Solicitud> {

    Context context;
    List<Solicitud> arraySolicitudes;

    public Adaptador(@NonNull Context context, List<Solicitud> arraySolicitudes) {
        super(context, R.layout.item_solicitudes,arraySolicitudes);
        this.context = context;
        this.arraySolicitudes = arraySolicitudes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_solicitudes, null, true);

        return super.getView(position, convertView, parent);
    }
}
