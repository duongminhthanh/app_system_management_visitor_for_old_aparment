package com.example.app_system_management_visitors_for_old_aparment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    EditText edit_username, edit_password;
    Button btn_login;
    FirebaseDatabase database;
    DatabaseReference myRef;
    boolean result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edit_username = findViewById(R.id.edit_name);
        edit_password = findViewById(R.id.edit_password);
        btn_login = findViewById(R.id.btn_login);
        database = FirebaseDatabase.getInstance();

        btn_login.setOnClickListener(view -> {
            if (!edit_username.getText().toString().equals("") && !edit_password.
                    getText().toString().equals("")) {
               if (readDataUsername() && readDataPassword()){
                   Intent mainIntent=new Intent(this,MainActivity.class);
                   startActivity(mainIntent);
               }
            }
        });
    }

    public boolean readDataUsername() {
        myRef = database.getReference("account");
        myRef.child("username").get().addOnCompleteListener(task1 -> {
            if (!task1.isSuccessful()) {
                Toast.makeText(this, "Incorrect username", Toast.LENGTH_LONG).show();
                result=false;
//                Log.e("firebase", "Error getting data", task.getException());
            } else {
                Toast.makeText(this, "Correct username", Toast.LENGTH_LONG).show();
//                Log.d("firebase", String.valueOf(task.getResult().getValue()));
              result=true;
            }
        });
        return result;
    }
    public boolean readDataPassword(){
        myRef = database.getReference("password");
        myRef.child("username").get().addOnCompleteListener(task1 -> {
            if (!task1.isSuccessful()) {
                Toast.makeText(this, "Incorrect password", Toast.LENGTH_LONG).show();
                result=false;
//                Log.e("firebase", "Error getting data", task.getException());
            } else {
                Toast.makeText(this, "Correct password", Toast.LENGTH_LONG).show();
//                Log.d("firebase", String.valueOf(task.getResult().getValue()));
                result=true;
            }
        });
        return result;
    }
}