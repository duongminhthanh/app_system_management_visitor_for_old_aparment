package com.example.app_system_management_visitors_for_old_aparment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText edit_username, edit_password;
    Button btn_login;

    DatabaseReference myRef;
    boolean checkAcc;
    String name="",pass="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edit_username = findViewById(R.id.edit_name);
        edit_password = findViewById(R.id.edit_password);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(view -> {
            edit_username.setText(edit_username.getText().toString().trim());
            edit_password.setText(edit_password.getText().toString().trim());
            name = edit_username.getText().toString();
            pass = edit_password.getText().toString();
            readDataAccount(name, pass);
            if (checkAcc == true) {
                Toast.makeText(LoginActivity.this, "Correct username and password"
                        , Toast.LENGTH_SHORT).show();
                Log.d("check acc is ", String.valueOf(checkAcc));
                Intent login = new Intent(this, MainActivity.class);
                startActivity(login);
            } else if (checkAcc == false) {
                Log.d("check acc is ", String.valueOf(checkAcc));
                Toast.makeText(LoginActivity.this, "Incorrect username and password"
                        , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void readDataAccount(String name,String pass) {

        myRef = FirebaseDatabase.getInstance().getReference().child("account");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                String username = snapshot.child("username")
                        .getValue().toString();
                String password = (snapshot.child("password")
                        .getValue()).toString();
                Log.d("true or false username", String.valueOf(name.equals(username)));
                Log.d("true or false password", String.valueOf(pass.equals(password)));
                checkAcc= (name.equals(username) && pass.equals(password));
//                Log.d("Check acc is: ", String.valueOf(checkAcc));

            }
            @Override
            public void onCancelled( DatabaseError error) {

            }
        });
    }


}