package com.example.ejemplo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.security.KeyManagementException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class crear_conductor extends AppCompatActivity {
    Button btnFecha;
    EditText etFecha;
    private int dia, mes, ano,hora;
    private EditText tvPhone;
    EditText etNombre, etApellido, etCorreo, etContra, etFoto;
    Button btnRegistrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_conductor);
        //Fecha
        btnFecha = (Button)findViewById(R.id.btnFecha);
        etFecha = (EditText)findViewById(R.id.etFecha);

        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);

        etCorreo = findViewById(R.id.etCorreoElectronico);
        etContra = findViewById(R.id.etPass);
        tvPhone = (EditText)findViewById(R.id.tvPhone);
        etFoto = findViewById(R.id.etFoto);
        btnRegistrar = findViewById(R.id.btnAgregar);

        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view==btnFecha){
                    final Calendar c =Calendar.getInstance();
                    dia=c.get(Calendar.DAY_OF_MONTH);
                    mes=c.get(Calendar.MONTH);
                    ano=c.get(Calendar.YEAR);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(crear_conductor.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int dayOfMonth, int monthOfYear, int year) {
                            etFecha.setText(dayOfMonth+"/"+monthOfYear+"/"+year);

                        }
                    }
                    ,dia,mes,ano);
                    datePickerDialog.show();
                }
            }
        });

        //Alojar de manera temporal
        String dato = getIntent().getStringExtra("dato");
        tvPhone.setText(dato);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertarDatos();
            }
        });

    }

    private void insertarDatos() {
        final String nombre = etNombre.getText().toString().trim();
        final String apellido = etApellido.getText().toString().trim();
        final String fechaNac = etFecha.getText().toString().trim();
        final String correo = etCorreo.getText().toString().trim();
        final String contrasena = etContra.getText().toString().trim();
        final String telefono = tvPhone.getText().toString().trim();
        final String foto = etFoto.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando...");
        if (nombre.isEmpty()) {
            Toast.makeText(this, "Ingrese nombre", Toast.LENGTH_SHORT).show();
            return;
        } else if (apellido.isEmpty()) {
            Toast.makeText(this, "Ingrese Apellido", Toast.LENGTH_SHORT).show();
            return;
        }else if(fechaNac.isEmpty()){
            Toast.makeText(this, "Ingrese Fecha", Toast.LENGTH_SHORT).show();

        } else if (correo.isEmpty()) {
            Toast.makeText(this, "Ingrese Correo", Toast.LENGTH_SHORT).show();
            return;
        } else if (contrasena.isEmpty()) {
            Toast.makeText(this, "Ingrese Contrasena", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (foto.isEmpty()) {
            Toast.makeText(this, "Ingrese Foto", Toast.LENGTH_SHORT).show();
            return;
        } else {
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, "http://200.29.219.25:3307/duvan/crearConductor.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equalsIgnoreCase("Registro exitoso")) {
                        Toast.makeText(crear_conductor.this, "Conductor Creado", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        startActivity(new Intent(getApplicationContext(), Login.class));
                        finish();

                    } else {
                        Toast.makeText(crear_conductor.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(crear_conductor.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            })

            {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("nombre", nombre);
                    params.put("apellido", apellido);
                    params.put("fechaNac", fechaNac);
                    params.put("correo", correo);
                    params.put("contrasena", contrasena);
                    params.put("telefono", telefono);
                    params.put("foto", foto);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(crear_conductor.this);
            requestQueue.add(request);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}