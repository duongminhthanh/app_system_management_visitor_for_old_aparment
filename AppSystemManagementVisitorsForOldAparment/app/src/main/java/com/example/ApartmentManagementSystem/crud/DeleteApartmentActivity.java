package com.example.ApartmentManagementSystem.crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.ApartmentManagementSystem.list.ManageListApartmentActivity;
import com.example.ApartmentManagementSystem.R;
import com.example.ApartmentManagementSystem.model.Apartment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeleteApartmentActivity extends AppCompatActivity {

    Button btnYes, btnNo;
    Intent intent;
    String id,ownerName, ownerPhone, roomId;
    DatabaseReference myRef;
    ArrayList<Apartment> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_apartment);
        list=new ArrayList<>();
        btnYes = findViewById(R.id.btn_yes);
        btnNo = findViewById(R.id.btn_no);
        intent = getIntent();
//        ownerName = intent.getStringExtra("owner_name");
//        ownerPhone = intent.getStringExtra("owner_phone");
        roomId = intent.getStringExtra("room_id");
        myRef = FirebaseDatabase.getInstance().getReference().child("list_apartment");
        btnNo.setOnClickListener(view -> {
            showErrorDeleteToast();
        });
        btnYes.setOnClickListener(view -> {
            deleteData();
        });
    }
    public void deleteData(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myRef.child(roomId).removeValue();
                showDeleteSuccessfulToast();
                Intent intent = new Intent(DeleteApartmentActivity.this,ManageListApartmentActivity.class);
                startActivity(intent);
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
        toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
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
        toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}