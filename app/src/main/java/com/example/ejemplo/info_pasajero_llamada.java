package com.example.ejemplo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class info_pasajero_llamada extends AppCompatActivity {
    EditText etNumber;
    ImageButton btCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pasajero_llamada);
        etNumber = findViewById(R.id.et_number);
        btCall = findViewById(R.id.bt_call);

        btCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = etNumber.getText().toString();
                if (phone.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter number!"
                            , Toast.LENGTH_SHORT).show();
                } else {
                    String s = "tel:" + phone;
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(s));
                    startActivity(intent);
                }
            }
        });
    }
    public void anterior(View view){
        Intent anterior = new Intent(this, info_pasajero.class);
        startActivity(anterior);


    }
}