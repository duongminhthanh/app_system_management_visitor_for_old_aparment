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
            if (readDataUsername() && readDataPassword()) {
                Toast.makeText(this, "Correct username and password"
                        , Toast.LENGTH_LONG).show();
                Intent mainIntent = new Intent(this, MainActivity.class);
                startActivity(mainIntent);
            } else Toast.makeText(this, "Incorrect username and password"
                    , Toast.LENGTH_LONG).show();
        });
    }

    public boolean readDataUsername() {
        myRef = FirebaseDatabase.getInstance().getReference().child("account");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String acc = Objects.requireNonNull(snapshot.child("username")
                        .getValue()).toString();
                if (acc.equals(edit_username.getText().toString().trim()))
                    result = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return result;
    }

    public boolean readDataPassword() {
        myRef = FirebaseDatabase.getInstance().getReference().child("account");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String pass = Objects.requireNonNull(snapshot.child("password")
                        .getValue()).toString();
                if (pass.equals(edit_password.getText().toString().trim()))
                    result = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return result;
    }
}