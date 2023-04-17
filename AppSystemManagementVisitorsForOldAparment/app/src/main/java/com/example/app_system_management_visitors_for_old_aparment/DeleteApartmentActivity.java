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

public class DeleteApartmentActivity extends AppCompatActivity {

    Button btnYes,btnNo;
    EditText editOwnerName,editOwnerPhone,editRoomId;
    Intent intent;
    String ownerName,ownerPhone,roomId;
    DatabaseReference myRef;
    Apartment a;
    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_apartment);
        btnYes=findViewById(R.id.button_yes);
        btnNo=findViewById(R.id.button_no);
        editOwnerName=findViewById(R.id.edit_new_name);
        editOwnerPhone=findViewById(R.id.edit_phone);
        editRoomId=findViewById(R.id.edit_room_id);
        myRef= FirebaseDatabase.getInstance().getReference().child("list_apartment");
        intent=getIntent();
        ownerName=intent.getStringExtra("owner name");
        ownerPhone=intent.getStringExtra("owner phone");
        roomId=intent.getStringExtra("room id");
        editOwnerName.setText(ownerName);
        editOwnerPhone.setText(ownerPhone);
        editRoomId.setText(roomId);
        btnNo.setOnClickListener(view -> showErrorDeleteToast());
        btnYes.setOnClickListener(view -> myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                a=new Apartment();
                myRef.child(roomId).removeValue();
                showDeleteSuccessfulToast();
                Intent intentManagement=new Intent(DeleteApartmentActivity.this
                        ,ManageListApartmentActivity.class);
                startActivity(intentManagement);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }));
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