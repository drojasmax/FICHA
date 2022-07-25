package com.example.ejemplo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class Login extends AppCompatActivity {
    //Iniciar Variable
    EditText etCorreo, etContra;
    Button btnLogin;
    RecyclerView recyclerView;
    String sBaseURL = "http://200.29.219.25:3307/duvan/validarPasajero.php";
    String sCorreo;
    String sContra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Asignar variable
        etCorreo = findViewById(R.id.etCorreo);
        etContra = findViewById(R.id.etContra);
        btnLogin = findViewById(R.id.btnLogin);


        //Llmar metodo
        getPassenger();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get values from edit Text
                sCorreo = etCorreo.getText().toString().trim();
                sContra = etContra.getText().toString().trim();
                //Check cnodition
                if (!sCorreo.isEmpty() && !sContra.isEmpty()){
                    //When both values are not empty
//Llama metodo
                    addPassenger();

                }

            }
        });
    }

    //Interfaz APi
    private interface getInter{
        //Get request
        @GET("passenger")
        Call<String> STRING_CALL(
                @Query("page") String page ,
                @Query("size") String size

        );
    }
    private void getPassenger(){
        //inicializa progreso de dialogo
        ProgressDialog dialog  = ProgressDialog.show(
                this, "", "Por favor espere", true
        );
        //inicializa retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(sBaseURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        //inicializa interfaz
        getInter inter = retrofit.create(getInter.class);
        //Pass input values
        Call <String> call = inter.STRING_CALL("756", "25");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null){
                    //cuando el response esta redi y el cuerpo no este vacio
                    //dismiss dialog
                    dialog.dismiss();
                    try {
                        //inicializa json object
                        JSONObject object = new JSONObject(response.body());
                        JSONArray jsonArray = object.getJSONArray("data");
                        //inicializa layout manager
                        GridLayoutManager layoutManager = new GridLayoutManager(
                                Login.this, 2);
                        //Set layout manager
                        recyclerView.setLayoutManager(layoutManager);
                        //Set adapter
                        recyclerView.setAdapter(new MainAdapter(jsonArray));

                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }
    //Interfaz de api
    private interface postInter{
        //Post request
        @FormUrlEncoded
        @POST("passenger")
        //inicializa string call
        Call <String> STRING_CALL(
                @Field("correo") String name,
                @Field("contrasena") String pass
        );

    }
    // Interfaz de API
    private void addPassenger(){
        //Iniciar retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(sBaseURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        //Iinicializa interfaz
        postInter inter = retrofit.create(postInter.class);
        //Pass input values
        Call <String> call = inter.STRING_CALL(sCorreo, sContra);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //ver condicional
                if (response.isSuccessful() && response.body() != null){
                    //cuando el response esta y el cuerpo no este video
                    //Limpiamos los editores de texto
                    etCorreo.getText().clear();
                    etContra.getText().clear();
                    //Conseguir el metodo
                    getPassenger();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}