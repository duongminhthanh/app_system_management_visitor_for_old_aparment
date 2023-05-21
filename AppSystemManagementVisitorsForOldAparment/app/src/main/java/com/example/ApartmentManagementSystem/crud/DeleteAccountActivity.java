package com.example.ApartmentManagementSystem.crud;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ApartmentManagementSystem.R;
import com.example.ApartmentManagementSystem.list.ManageListAccountActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class DeleteAccountActivity extends AppCompatActivity {
    Button btnYes, btnNo;
    Intent intent;
    String idAcc, username, password, pin_code;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);
        btnYes = findViewById(R.id.btn_yes);
        btnNo = findViewById(R.id.btn_no);
        myRef = FirebaseDatabase.getInstance().getReference().child("list_account");
        intent = getIntent();
        idAcc = intent.getStringExtra("acc_id");
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");
        pin_code = intent.getStringExtra("pin_code");
        Log.d("acc_id",idAcc);
        btnNo.setOnClickListener(view -> showErrorDeleteToast());
        btnYes.setOnClickListener(view -> deleteData());
    }
    public void deleteData() {
        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myRef.child(idAcc).child("acc_id").removeValue();
                myRef.child(idAcc).child("username").removeValue();
                myRef.child(idAcc).child("password").removeValue();
                myRef.child(idAcc).child("pin_code").removeValue();
                showDeleteSuccessfulToast();
                Intent intentManagement = new Intent(DeleteAccountActivity.this
                        , ManageListAccountActivity.class);
                startActivity(intentManagement);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @SuppressLint("SetTextI18n")
    public void showDeleteSuccessfulToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_success,
                findViewById(R.id.toast_success));
        TextView text = layout.findViewById(R.id.toast_text_success);
        text.setText("Delete apartment successfully");
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    @SuppressLint("SetTextI18n")
    public void showErrorDeleteToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_error, findViewById(R.id.toast_error));
        TextView text = layout.findViewById(R.id.toast_text_error);
        text.setText("Delete apartment failed");
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}