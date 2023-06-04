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
import android.widget.TextView;
import android.widget.Toast;

import com.example.ApartmentManagementSystem.authentication.LoginActivity;
import com.example.ApartmentManagementSystem.list.ManageListApartmentActivity;
import com.example.ApartmentManagementSystem.R;
import com.example.ApartmentManagementSystem.main_activity.CustomProgressDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeleteApartmentActivity extends AppCompatActivity {

    Button btnYes, btnNo;
    Intent intent;
    String ownerName, ownerPhone, roomId;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_apartment);
        CustomProgressDialog dialog = new CustomProgressDialog(DeleteApartmentActivity.this);
        btnYes = findViewById(R.id.btn_yes);
        btnNo = findViewById(R.id.btn_no);
        myRef = FirebaseDatabase.getInstance().getReference().child("list_apartment");
        intent = getIntent();
        ownerName = intent.getStringExtra("owner name");
        ownerPhone = intent.getStringExtra("owner phone");
        roomId = intent.getStringExtra("room id");
        btnNo.setOnClickListener(view -> {
            dialog.show();
            showErrorDeleteToast();
        });
        btnYes.setOnClickListener(view -> {
            dialog.show();
            deleteData();
        });
    }
    public void deleteData(){
        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myRef.child(roomId).child("owner_name").removeValue();
                myRef.child(roomId).child("owner_phone").removeValue();
                myRef.child(roomId).child("room_id").removeValue();
                showDeleteSuccessfulToast();
                Intent intentManagement = new Intent(DeleteApartmentActivity.this
                        , ManageListApartmentActivity.class);
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