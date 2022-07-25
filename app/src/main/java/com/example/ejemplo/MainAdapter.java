package com.example.ejemplo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    //Inicializo variable
    JSONArray jsonArray;

    public MainAdapter(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //iniciazlia view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main,parent, false);
        //Return holder view
        // return new RecyclerView.ViewHolder(view); Error
        /*
        ERRORRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
         */
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            //inicializa json object
            JSONObject object = jsonArray.getJSONObject(position);
            //Set name on text view
            holder.tv_correo.setText(object.getString("correo"));
            holder.tv_contra.setText(object.getString("contrasena"));


        }catch (JSONException e){
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        //Return array rize
        return jsonArray.length();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        //inicializar variable
        TextView tv_correo, tv_contra;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Asignar varibale
            tv_correo = itemView.findViewById(R.id.tv_correo);
            tv_contra = itemView.findViewById(R.id.tv_contra);
        }

    }
}
