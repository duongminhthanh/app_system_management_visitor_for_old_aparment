package com.example.app_system_management_visitors_for_old_aparment;

import android.content.Intent;
import android.os.Bundle;
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

    String name = "", pass = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edit_username = findViewById(R.id.edit_name);
        edit_password = findViewById(R.id.edit_password);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(view -> {
            if (edit_username.getText().toString().isEmpty() &&
                    edit_password.getText().toString().isEmpty()) {
                edit_username.setText(edit_username.getText().toString());
                edit_password.setText(edit_username.getText().toString());
            }
            name = edit_username.getText().toString();
            pass = edit_password.getText().toString();
//            readDataAccount(name, pass);
            readDataListAccount(name,pass);
        });
    }

    public void readDataListAccount(String name, String pass){
        myRef=FirebaseDatabase.getInstance().getReference().child("list_account");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String username= Objects.requireNonNull(dataSnapshot.child("username").getValue()).toString();
                    String password= Objects.requireNonNull(dataSnapshot.child("password").getValue()).toString();
                    if (name.equals(username)&&pass.equals(password)){
                        Toast.makeText(LoginActivity.this, "Correct username and password"
                                , Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(login);
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Incorrect username and password"
                                , Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}