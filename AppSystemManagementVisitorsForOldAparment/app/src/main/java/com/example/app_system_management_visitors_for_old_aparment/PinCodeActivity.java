package com.example.app_system_management_visitors_for_old_aparment;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class PinCodeActivity extends AppCompatActivity {

    EditText ed1, ed2, ed3, ed4;
    String pin_code="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_code);
        ed1 = findViewById(R.id.ed_1);
        ed2 = findViewById(R.id.ed_2);
        ed3 = findViewById(R.id.ed_3);
        ed4 = findViewById(R.id.ed_4);

        ed1.setOnClickListener(view -> {
            if (ed1.getText().toString().isEmpty()){
                ed1.setText(ed1.getText().toString());
                pin_code= ed1.getText().toString();
            }
        });

        ed2.setOnClickListener(view -> {
            if (ed2.getText().toString().isEmpty()){
                ed2.setText(ed2.getText().toString());
                pin_code+= ed2.getText().toString();
            }
        });

        ed3.setOnClickListener(view -> {
            if (ed3.getText().toString().isEmpty()){
                ed3.setText(ed3.getText().toString());
                pin_code+=ed3.getText().toString();
            }
        });
        ed4.setOnClickListener(view -> {
            if (ed4.getText().toString().isEmpty()){
                ed4.setText(ed4.getText().toString());
                pin_code+= ed4.getText().toString();
            }
        });
        Log.println(Log.INFO,"pin_code", pin_code);
    }
}