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

import com.example.ApartmentManagementSystem.list.ManageListApartmentActivity;
import com.example.ApartmentManagementSystem.R;
import com.example.ApartmentManagementSystem.model.Apartment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddNewApartmentActivity extends AppCompatActivity {
    EditText ownerName,ownerPhone,roomId;
    Button btnSave;
    DatabaseReference myRef;
    String name,phone,id;

    Apartment a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_apartment);
        ownerName=findViewById(R.id.edit_owner_name);
        ownerPhone=findViewById(R.id.edit_phone);
        roomId=findViewById(R.id.edit_room_id);
        btnSave=findViewById(R.id.btn_save);
        myRef= FirebaseDatabase.getInstance().getReference().child("list_apartment");
        btnSave.setOnClickListener(view -> {
            ownerName.setText(ownerName.getText().toString());
            name=ownerName.getText().toString();
            ownerPhone.setText(ownerPhone.getText().toString());
            phone=ownerPhone.getText().toString();
            roomId.setText(roomId.getText().toString());
            id=roomId.getText().toString();
            if (name.isEmpty()||phone.isEmpty()||id.isEmpty())showErrorEmptyToast();
            else{
                addData(id,name,phone);
            }
        });

    }
    public void addData(String id,String name,String phone){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                a=new Apartment();
                a.setOwner_name(name);
                a.setOwner_phone(phone);
                a.setRoom_id(id);
                myRef.child(id).setValue(a);
                showAddSuccessfulToast();
                Intent intent=new Intent(getApplicationContext()
                        , ManageListApartmentActivity.class);
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