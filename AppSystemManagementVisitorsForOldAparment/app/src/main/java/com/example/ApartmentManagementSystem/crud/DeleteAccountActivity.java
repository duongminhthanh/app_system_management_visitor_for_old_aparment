package com.example.ApartmentManagementSystem.crud;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ApartmentManagementSystem.R;
import com.example.ApartmentManagementSystem.list.ManageListAccountActivity;
import com.example.ApartmentManagementSystem.list.ManageListApartmentActivity;
import com.example.ApartmentManagementSystem.ultils.LoadingDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
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
    LoadingDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);
        btnYes = findViewById(R.id.btn_yes);
        btnNo = findViewById(R.id.btn_no);
        dialog =new LoadingDialog(this);
        intent = getIntent();
        idAcc = intent.getStringExtra("acc_id");
        myRef = FirebaseDatabase.getInstance().getReference().child("list_account");
        Log.d("acc_id",idAcc);
        btnNo.setOnClickListener(view -> {
            dialog.show();
            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    dialog.cancel();
                }
            };
            handler.postDelayed(runnable,1000);
            showErrorDeleteToast();
        });
        btnYes.setOnClickListener(view -> {
            dialog.show();
            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    dialog.cancel();
                }
            };
            handler.postDelayed(runnable,1000);
            deleteData();
        });
    }
    public void deleteData() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myRef.child(idAcc).removeValue();
                showDeleteSuccessfulToast();
                Intent intent=new Intent(DeleteAccountActivity.this,ManageListAccountActivity.class);
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
        text.setText("Delete account successfully");
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
        text.setText("Delete account failed");
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}