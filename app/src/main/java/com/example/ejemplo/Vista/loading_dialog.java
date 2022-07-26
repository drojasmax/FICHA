package com.example.ejemplo.Vista;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.ejemplo.R;

public class loading_dialog {
    private final Activity activity;
    private AlertDialog dialog;

    loading_dialog(Activity myActivity){
        activity = myActivity;
    }
    void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog, null));
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.show();
    }
    void dismissDialog() {
        dialog.dismiss();
    }
}
