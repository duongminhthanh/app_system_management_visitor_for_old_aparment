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

public class EditApartmentActivity extends AppCompatActivity {
    EditText ownerName, ownerPhone, roomId;
    DatabaseReference myRef;
    String name, phone, id, dataName, dataPhone, dataId;
    Button btnSave;
    Intent intent;
    Apartment a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_apartment);
        ownerName = findViewById(R.id.edit_owner_name);
        ownerPhone = findViewById(R.id.edit_phone);
        roomId = findViewById(R.id.edit_room_id);
        btnSave = findViewById(R.id.btn_save);
        intent = getIntent();
        dataName = intent.getStringExtra("owner_name");
        dataPhone = intent.getStringExtra("owner_phone");
        dataId = intent.getStringExtra("room_id");
        myRef = FirebaseDatabase.getInstance().getReference().child("list_apartment").child(dataId);
        Log.d("room id", dataId);
        ownerName.setText(dataName);
        ownerPhone.setText(dataPhone);
        roomId.setText(dataId);
        btnSave.setOnClickListener(view -> {
            //get data from management screen
            Log.d("room id", dataId);
            name = ownerName.getText().toString();
            phone = ownerPhone.getText().toString();
            id = dataId;
            if (name.isEmpty() || phone.isEmpty()) showErrorEmptyToast();
            else if (name.equals(dataName) && phone.equals(dataPhone))
                showErrorSameDataToast();
            else
                editData(name, phone);
        });
    }
    public void editData(String name, String phone) {
        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myRef.child("owner_name").setValue(name);
                myRef.child("owner_phone").setValue(phone);
                showUpdateSuccessfulToast();
                Intent intent = new Intent(EditApartmentActivity.this
                        , ManageListApartmentActivity.class);
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
        text.setText("Update apartment successfully");
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
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
        toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
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
        toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}