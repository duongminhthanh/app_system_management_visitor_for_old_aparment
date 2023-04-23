package com.example.app_system_management_visitors_for_old_aparment;

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
        ownerName = findViewById(R.id.edit_new_name);
        ownerPhone = findViewById(R.id.edit_phone);
        roomId = findViewById(R.id.edit_room_id);
        myRef = FirebaseDatabase.getInstance().getReference().child("list_apartment");
        btnSave = findViewById(R.id.btn_save);
        intent = getIntent();
        dataName = intent.getStringExtra("owner name");
        dataPhone = intent.getStringExtra("owner phone");
        dataId = intent.getStringExtra("room id");
        Log.d("room id", dataId);
        roomId.setText(dataId);
        btnSave.setOnClickListener(view -> {
            //get data from management screen

            Log.d("room id", dataId);
            ownerName.setText(ownerName.getText().toString());
            ownerPhone.setText(ownerPhone.getText().toString());

            name = ownerName.getText().toString();
            phone = ownerPhone.getText().toString();
            id = dataId;

            if (name.isEmpty() || phone.isEmpty()) showErrorEmptyToast();
            else if (name.equals(dataName) && phone.equals(dataPhone))
                showErrorSameDataToast();
            else
                editData(id, name, phone);
        });
    }

    public void editData(String id, String name, String phone) {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                a = new Apartment();
                a.setRoom_id(id);
                a.setOwner_name(name);
                a.setOwner_phone(phone);
                myRef.child(id).setValue(a);
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