package com.example.app_system_management_visitors_for_old_aparment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
            readDataListAccount(name, pass);
        });
    }

    public void readDataListAccount(String name, String pass) {
        myRef = FirebaseDatabase.getInstance().getReference().child("list_account");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String username = Objects.requireNonNull(dataSnapshot.child("username").getValue()).toString();
                    String password = Objects.requireNonNull(dataSnapshot.child("password").getValue()).toString();
                    if (name.equals("admin") && username.compareTo(name) == 0
                            && pass.equals("admin1234") && password.compareTo(pass) == 0) {
                        showSuccessfulToast();
                        Intent dashboardAdmin = new Intent(LoginActivity.this, DashboardAdminActivity.class);
                        startActivity(dashboardAdmin);
                    } else if (name.equals(username) && pass.equals(password)) {
                        showSuccessfulToast();
                        Intent login = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(login);
                    } else {
                        showErrorToast();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void showSuccessfulToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_success, findViewById(R.id.toast_success));
        TextView text = layout.findViewById(R.id.toast_text_success);
        text.setText("Correct username and password");
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    @SuppressLint("SetTextI18n")
    public void showErrorToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_error, findViewById(R.id.toast_error));
        TextView text = layout.findViewById(R.id.toast_text_error);
        text.setText("Incorrect username and password");
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}