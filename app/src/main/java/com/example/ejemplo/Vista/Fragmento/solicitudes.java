package com.example.ejemplo.Vista.Fragmento;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ejemplo.Controlador.Adaptador;
import com.example.ejemplo.Modelo.Solicitud;
import com.example.ejemplo.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.ArrayList;

public class solicitudes extends Fragment {

    ListView lv;
    Adaptador adaptador;
    public static ArrayList<Solicitud> arrayListSolicitudes = new ArrayList<>();
    String url = "http://200.29.219.25:3307/duvan/conseguirSolicitudes.php";
    Solicitud solicitud;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_solicitudes, container, false);
        lv = v.findViewById(R.id.listView);
        adaptador = new Adaptador(getContext(), arrayListSolicitudes);
        lv.setAdapter(adaptador);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                ProgressDialog dialog = new ProgressDialog(view.getContext());
                CharSequence[] dialogoItem = {"Ver info","Editar info","Eliminar info"};
                builder.setTitle(arrayListSolicitudes.get(position).getFk_idP());
                builder.setItems(dialogoItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                break;
                            case 1:
                                break;
                            case 2:
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
        listarDatos();
        return v;
    }

    private void listarDatos() {
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            arrayListSolicitudes.clear();
            try {
                JSONObject json = new JSONObject(response);
                String consultar = json.getString("consultar");
                JSONArray jsonArray = json.getJSONArray("respuesta");
                if (consultar.equals("consultar")) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String fk_idP = object.getString("fk_idP");
                        String fk_patente = object.getString("fk_patente");
                        String nombreInicio = object.getString("nombreInicio");
                        String latInicio = object.getString("latInicio");
                        String lonInicio = object.getString("lonInicio");
                        String nombreDestino = object.getString("nombreDestino");
                        String latDestino = object.getString("latInicio");
                        String lonDestino = object.getString("lonInicio");
                        solicitud = new Solicitud(fk_idP, fk_patente, nombreInicio, latInicio, lonInicio, nombreDestino, latDestino, lonDestino);
                        arrayListSolicitudes.add(solicitud);
                        adaptador.notifyDataSetChanged();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show());
        RequestQueue volleyQuery = Volley.newRequestQueue(getContext());
        volleyQuery.add(request);
    }
}