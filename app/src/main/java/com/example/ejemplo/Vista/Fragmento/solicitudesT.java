package com.example.ejemplo.Vista.Fragmento;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ejemplo.Modelo.Trayecto;
import com.example.ejemplo.R;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class solicitudesT extends Fragment {

    RecyclerView rvSolicitudes;
    final String URL = "http://200.29.219.25:3307/duvan";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_solicitudes_t, container, false);

        rvSolicitudes = v.findViewById(R.id.rvSolicitudes);



        return v;
    }
    private interface getInter{
        @GET("getT.php")
        Call<Trayecto> getData();
    }
    private void getTrayecto(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getInter inter = retrofit.create(getInter.class);
        Call<Trayecto> call = inter.getData();
        call.enqueue(new Callback<Trayecto>() {
            @Override
            public void onResponse(@NonNull Call<Trayecto> call, @NonNull Response<Trayecto> response) {
                if (response.isSuccessful() || response.body() != null){
                    try {
                        JSONObject object = new JSONObject(String.valueOf(response.body()));

                    }catch (JSONException e){

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Trayecto> call, @NonNull Throwable t) {

            }
        });
    }
}