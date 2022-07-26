package com.example.ejemplo.Vista.Fragmento;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ejemplo.R;

import org.w3c.dom.Text;

public class menu_destino extends Fragment {

    TextView tvPasajerosDisponibles, tvTiempoEstimado;
    EditText etPrecio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu_destino, container, false);


        tvPasajerosDisponibles = v.findViewById(R.id.tvCantidadPasajeros);
        tvTiempoEstimado = v.findViewById(R.id.tvTiempoEstimado);



        etPrecio = v.findViewById(R.id.etPrecio);




        Button btnConfirmarViaje = v.findViewById(R.id.btnConfirmarViaje);
        btnConfirmarViaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("precio", etPrecio.getText().toString().trim());

            }
        });
    return v;
    }

}