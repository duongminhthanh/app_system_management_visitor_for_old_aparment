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
    boolean checkUsername, checkPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edit_username = findViewById(R.id.edit_name);
        edit_password = findViewById(R.id.edit_password);
        btn_login = findViewById(R.id.btn_login);
        database = FirebaseDatabase.getInstance();

        edit_username.setOnClickListener(view -> edit_username.setText(edit_username.getText()
                .toString().trim()));
        edit_password.setOnClickListener(view -> edit_password.setText(edit_password.getText()
                .toString().trim()));
        btn_login.setOnClickListener(view -> {
            if (readDataUsername() && readDataPassword()) {
                Toast.makeText(LoginActivity.this, "Correct username and password"
                        , Toast.LENGTH_SHORT).show();
                Intent login = new Intent(LoginActivity.this,LoginActivity.class);
                startActivity(login);
            }
            else if (!readDataUsername() || !readDataPassword()) {
                Toast.makeText(LoginActivity.this, "Incorrect username or password"
                        , Toast.LENGTH_SHORT).show();
                Intent login = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(login);
            }
        });
    }

    public boolean readDataUsername() {
        String name = edit_username.getText().toString();
        myRef = FirebaseDatabase.getInstance().getReference().child("account");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String username = Objects.requireNonNull(snapshot.child("username")
                        .getValue()).toString();
               if (name.compareTo(username)==0)checkUsername=true;
               else if (name.compareTo(username)!=0) checkUsername=false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return checkUsername;
    }

    public boolean readDataPassword() {
        String pass = edit_password.getText().toString();
        myRef = FirebaseDatabase.getInstance().getReference().child("account");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String password = (Objects.requireNonNull(snapshot.child("password")
                        .getValue())).toString();
                if (pass.compareTo(password)==0)checkPassword=true;
                else if (pass.compareTo(password)!=0) checkUsername=false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return checkPassword;
    }
}