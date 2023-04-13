package com.example.app_system_management_visitors_for_old_aparment;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class PinCodeActivity extends AppCompatActivity implements View.OnClickListener {

    EditText ed1, ed2, ed3, ed4;
    Button btnEnter;
    String pin_code = "",code;
    DatabaseReference myRef;
    ArrayList<Account> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_code);
        list=new ArrayList<>();
        initView();
        ed1.setOnClickListener(this);
        ed2.setOnClickListener(this);
        ed3.setOnClickListener(this);
        ed4.setOnClickListener(this);
        btnEnter.setOnClickListener(view -> {
//            Log.d("pin_code value is: ",pin_code);
            pin_code = ed1.getText().toString().trim() + ed2.getText().toString().trim()
                    + ed3.getText().toString().trim() + ed4.getText().toString().trim();
            if (pin_code.isEmpty())showErrorEmptyToast();
            else {
                readDataPinCode(pin_code);
            }
        });
    }

    public void initView() {
        ed1 = findViewById(R.id.ed_1);
        ed2 = findViewById(R.id.ed_2);
        ed3 = findViewById(R.id.ed_3);
        ed4 = findViewById(R.id.ed_4);
        btnEnter = findViewById(R.id.button_enter);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ed_1:
                ed1.setText(ed1.getText().toString());
            case R.id.ed_2:
                ed2.setText(ed2.getText().toString());
            case R.id.ed_3:
                ed3.setText(ed3.getText().toString());
            case R.id.ed_4:
                ed4.setText(ed4.getText().toString());
        }
    }

    public void readDataPinCode(String pin_code) {

        myRef = FirebaseDatabase.getInstance().getReference().child("list_account");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    int i=list.size();
                    code = Objects.requireNonNull(dataSnapshot.
                            child("pin_code").getValue()).toString();
                    Account a=new Account(code);
                    list.add(a);
                    if (list.get(i).getPin_code().equals(pin_code)) {
                        code=list.get(i).getPin_code();
                        showSuccessfulToast();
                        Intent intent = new Intent(PinCodeActivity.this
                                , DashboardActivity.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @SuppressLint("SetTextI18n")
    public void showSuccessfulToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_success, findViewById(R.id.toast_success));
        TextView text=layout.findViewById(R.id.toast_text_success);
        text.setText("Correct pin code");
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
        TextView text=layout.findViewById(R.id.toast_text_error);
        text.setText("You must fill pin code");
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}