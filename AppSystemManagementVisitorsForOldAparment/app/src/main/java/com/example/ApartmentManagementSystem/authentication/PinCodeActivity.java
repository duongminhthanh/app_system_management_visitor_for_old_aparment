package com.example.ApartmentManagementSystem.authentication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.chaos.view.PinView;
import com.example.ApartmentManagementSystem.ultils.LoadingDialog;
import com.example.ApartmentManagementSystem.R;
import com.example.ApartmentManagementSystem.dashboard.DashboardActivity;
import com.example.ApartmentManagementSystem.model.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class PinCodeActivity extends AppCompatActivity{

    PinView pinView;
    Button btnEnter;
    String pin_code = "", code;
    DatabaseReference myRef;
    ArrayList<Account> list;
    Boolean checkPinCode = false;
    String username = "", password = "";
    LoadingDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_code);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        list = new ArrayList<>();
        pinView = findViewById(R.id.pin_view);
        btnEnter= findViewById(R.id.button_enter);
        dialog =new LoadingDialog(this);
////        Intent intent = getIntent();
////        Bundle bundle = intent.getExtras();
////        username = bundle.getString("username");
////        password = bundle.getString("password");
////        Log.d("username",username);
//        Log.d("password",password);
        pinView.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btnEnter.setOnClickListener(view -> {
//            Log.d("pin_code value is: ",pin_code);
            dialog.show();
            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    dialog.cancel();
                }
            };
            handler.postDelayed(runnable,1000);
            pin_code =pinView.getText().toString().trim();
            if (pin_code.isEmpty()){
                showErrorEmptyToast();
            }
            else {
                readDataPinCode(pin_code);
            }
        });
    }


    public void readDataPinCode(String pin_code) {
        myRef = FirebaseDatabase.getInstance().getReference().child("list_account");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    int i = list.size();
                    code = Objects.requireNonNull(dataSnapshot.
                            child("pin_code").getValue()).toString();
                    Account a = new Account(code);
                    list.add(a);
                    if (list.get(i).getPin_code().equals(pin_code)) {
                        code = list.get(i).getPin_code();
                        //check pin code is matched
                        checkPinCode = pin_code.equals(code);
                        showSuccessfulToast();
                        Intent intent = new Intent(PinCodeActivity.this
                                , DashboardActivity.class);
                        startActivity(intent);
                    }
                }
                if (!checkPinCode) {
                    showErrorIncorrectToast();
                    //clear pin code to staff can type it again
                    pinView.setText("");
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
        TextView text = layout.findViewById(R.id.toast_text_success);
        text.setText("Correct pin code");
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
        text.setText("You must fill pin code");
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    @SuppressLint("SetTextI18n")
    public void showErrorIncorrectToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_error, findViewById(R.id.toast_error));
        TextView text = layout.findViewById(R.id.toast_text_error);
        text.setText("Incorrect pin code");
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}