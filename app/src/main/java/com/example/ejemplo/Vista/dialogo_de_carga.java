package com.example.ejemplo.Vista;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class dialogo_de_carga {

    Activity activity;
    AlertDialog dialog;

    dialogo_de_carga(Activity myActivity){
        activity = myActivity;
    }
    void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();

    }
    void dismissDialog(){
        dialog.dismiss();
    }
}
