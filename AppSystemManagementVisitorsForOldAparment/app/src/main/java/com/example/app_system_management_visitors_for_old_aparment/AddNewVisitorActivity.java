package com.example.app_system_management_visitors_for_old_aparment;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddNewVisitorActivity extends AppCompatActivity {
    EditText edName, edIdCard, edRoomId, edTime;
    Button btnSave;
    DatabaseReference myRef;
    String name, idCard, roomId, time;
    Visitor v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_vistiors);
        edName = findViewById(R.id.edit_new_name);
        edIdCard = findViewById(R.id.edit_new_id_card);
        edRoomId = findViewById(R.id.edit_room_id);
        edTime = findViewById(R.id.edit_new_time);
        btnSave = findViewById(R.id.btn_save);
        myRef = FirebaseDatabase.getInstance().getReference().child("list_visitor");
        btnSave.setOnClickListener(view -> {
            edName.setText(edName.getText().toString());
            name = edName.getText().toString();
            edIdCard.setText(edIdCard.getText().toString());
            idCard = edIdCard.getText().toString();
            edRoomId.setText(edRoomId.getText().toString());
            roomId = edRoomId.getText().toString();
            edTime.setText(edTime.getText().toString());
            time = edTime.getText().toString();
            if (name.isEmpty() || idCard.isEmpty() || roomId.isEmpty() || time.isEmpty())
                showErrorEmptyToast();
            else addData(name, idCard, roomId, time);
        });
    }

    public void addData(String name, String idCard, String roomId, String time) {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                v = new Visitor();
                v.setName(name);
                v.setId_card(idCard);
                v.setRoom_id(roomId);
                v.setVisit_time(time);
                myRef.child(name).setValue(v);
                showAddSuccessfulToast();
                Intent intent = new Intent(AddNewVisitorActivity.this
                        , ManageListVisitorActivity.class);
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