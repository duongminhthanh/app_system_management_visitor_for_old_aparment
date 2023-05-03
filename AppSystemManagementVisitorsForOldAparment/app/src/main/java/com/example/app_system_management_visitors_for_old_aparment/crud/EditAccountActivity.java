package com.example.app_system_management_visitors_for_old_aparment.crud;

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

import com.example.app_system_management_visitors_for_old_aparment.list.ManageListAccountActivity;
import com.example.app_system_management_visitors_for_old_aparment.R;
import com.example.app_system_management_visitors_for_old_aparment.model.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditAccountActivity extends AppCompatActivity {

    EditText edIdAcc, edUsername, edPassword, edPinCode;
    DatabaseReference myRef;
    Button btnSave;
    Intent intent;
    String idAcc, username, password, pin_code, dataIdAcc, dataUsername, dataPassword, dataPinCode;
    Account a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        edIdAcc = findViewById(R.id.edit_acc_id);
        edUsername = findViewById(R.id.edit_username);
        edPassword = findViewById(R.id.edit_password);
        edPinCode = findViewById(R.id.edit_pin_code);
        myRef = FirebaseDatabase.getInstance().getReference().child("list_account");
        intent = getIntent();
        dataIdAcc = intent.getStringExtra("acc_id");
        dataUsername = intent.getStringExtra("username");
        dataPassword = intent.getStringExtra("password");
        dataPinCode = intent.getStringExtra("pin_code");
        edIdAcc.setText(dataIdAcc);

        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(view -> {
            edUsername.setText(edUsername.getText().toString());
            edPassword.setText(edPassword.getText().toString());
            edPinCode.setText(edPinCode.getText().toString());
            idAcc = dataIdAcc;
            username = edUsername.getText().toString();
            password = edPassword.getText().toString();
            pin_code = edPinCode.getText().toString();
            if (username.isEmpty() || password.isEmpty() || pin_code.isEmpty())
                showErrorEmptyToast();
            else if (username.equals(dataUsername) && password.equals(dataPassword) &&
                    pin_code.equals(dataPinCode))
                showErrorSameDataToast();
            else {
                editData(idAcc, username, password, pin_code);
            }
        });
    }

    public void editData(String idAcc, String username, String password, String pin_code) {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                a = new Account();
                a.setAcc_id(idAcc);
                a.setUsername(username);
                a.setPassword(password);
                a.setPin_code(pin_code);
                myRef.child(idAcc).setValue(a);
                showUpdateSuccessfulToast();
                Intent intent = new Intent(EditAccountActivity.this,
                        ManageListAccountActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void showUpdateSuccessfulToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_success,
                findViewById(R.id.toast_success));
        TextView text = layout.findViewById(R.id.toast_text_success);
        text.setText("Update account successfully");
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

    @SuppressLint("SetTextI18n")
    public void showErrorSameDataToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_error, findViewById(R.id.toast_error));
        TextView text = layout.findViewById(R.id.toast_text_error);
        text.setText("Data is not changed");
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}