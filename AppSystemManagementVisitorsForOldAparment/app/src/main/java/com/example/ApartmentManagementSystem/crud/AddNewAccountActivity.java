package com.example.ApartmentManagementSystem.crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.ApartmentManagementSystem.list.ManageListAccountActivity;
import com.example.ApartmentManagementSystem.R;
import com.example.ApartmentManagementSystem.model.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddNewAccountActivity extends AppCompatActivity {
    EditText edIdAcc, edUsername, edPassword, edPinCode;
    Button btnSave;
    DatabaseReference myRef;
    String idAcc, username, password, pin_code;
    Account a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_account);
        edIdAcc = findViewById(R.id.edit_acc_id);
        edUsername = findViewById(R.id.edit_username);
        edPassword = findViewById(R.id.edit_password);
        edPinCode = findViewById(R.id.edit_pin_code);
        btnSave = findViewById(R.id.btn_save);
        myRef = FirebaseDatabase.getInstance().getReference().child("list_account");
        btnSave.setOnClickListener(view -> {
            edIdAcc.setText(edIdAcc.getText().toString());
            idAcc = edIdAcc.getText().toString();
            edUsername.setText(edUsername.getText().toString());
            username = edUsername.getText().toString();
            edPassword.setText(edPassword.getText().toString());
            password = edPassword.getText().toString();
            edPinCode.setText(edPinCode.getText().toString());
            pin_code = edPinCode.getText().toString();
            if (idAcc.isEmpty() || username.isEmpty() || password.isEmpty() || pin_code.isEmpty())
                showErrorEmptyToast();
            else
                addData(idAcc, username, password, pin_code);
        });
    }

    public void addData(String idAcc, String username, String password, String pin_code) {
        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                a = new Account(idAcc,username,password,pin_code);
                myRef.child(idAcc).setValue(a);
                showAddSuccessfulToast();
                Intent intent = new Intent(getApplicationContext()
                        , ManageListAccountActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void showAddSuccessfulToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_success,
                findViewById(R.id.toast_success));
        TextView text = layout.findViewById(R.id.toast_text_success);
        text.setText("Add new apartment successfully");
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    @SuppressLint("SetTextI18n")
    public void showErrorEmptyToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_error, findViewById(R.id.toast_error));
        TextView text = layout.findViewById(R.id.toast_text_error);
        text.setText("You must fill all data");
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}