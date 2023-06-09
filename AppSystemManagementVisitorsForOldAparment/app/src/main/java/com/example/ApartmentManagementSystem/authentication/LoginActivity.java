package com.example.ApartmentManagementSystem.authentication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ApartmentManagementSystem.ultils.LoadingDialog;
import com.example.ApartmentManagementSystem.main_activity.MainActivity;
import com.example.ApartmentManagementSystem.R;
import com.example.ApartmentManagementSystem.dashboard.DashboardAdminActivity;
import com.example.ApartmentManagementSystem.model.Account;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText edit_username, edit_password;
    Button btn_login;

    DatabaseReference myRef;

    String name, pass, username, password;

    ArrayList<Account> list;
    Account a;
    Boolean checkAcc=false;
    LoadingDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edit_username = findViewById(R.id.edit_name);
        edit_password = findViewById(R.id.edit_password);
        btn_login = findViewById(R.id.btn_login);
        list = new ArrayList<>();
        dialog =new LoadingDialog(this);
        btn_login.setOnClickListener(view -> {
            edit_username.setText(edit_username.getText().toString());
            edit_password.setText(edit_password.getText().toString());
            name = edit_username.getText().toString();
            pass = edit_password.getText().toString();
            dialog.show();
            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    dialog.cancel();
                }
            };
            handler.postDelayed(runnable,1000);
            //if it is type as username and password of admin
            if (name.equals("admin") && pass.equals("admin1234")) {
                showSuccessfulToast();
                Intent dashboardAdmin = new Intent(LoginActivity.this, DashboardAdminActivity.class);
                /*put data at data from db*/
                Bundle bundle = new Bundle();
                bundle.putString("username", name);
                bundle.putString("password", pass);
                dashboardAdmin.putExtras(bundle);
                LoginActivity.this.startActivity(dashboardAdmin);
            }
            //if username and password is not typed
            else if (name.isEmpty() && pass.isEmpty()) {
                showErrorEmptyToast();
            }
            else if (!name.isEmpty() && !pass.isEmpty()) {
                readDataListAccount(name, pass);
            }

        });
    }

    public void readDataListAccount(String name, String pass) {
        myRef = FirebaseDatabase.getInstance().getReference().child("list_account");
        /*get list username*/
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    int i = list.size();
                    username = Objects.requireNonNull(dataSnapshot.child("username")
                            .getValue()).toString();
                    password = Objects.requireNonNull(dataSnapshot.child("password")
                            .getValue()).toString();
                    a = new Account(username, password);
                    Log.d("account", String.valueOf(a));
                    list.add(a);
                    if (list.get(i).getUsername().equals(name)
                            && list.get(i).getPassword().equals(pass)) {
                        username =list.get(i).getUsername();
                        password =list.get(i).getPassword();
                        //check that username and password is matched
                        checkAcc = name.equals(username) && pass.equals(password);
                        Log.d("check acc is", String.valueOf(checkAcc));
                        Log.d("username", list.get(i).getUsername());
                        Log.d("password", list.get(i).getPassword());
                        showSuccessfulToast();
//                        showSuccessDialog();
                        Intent intent = new Intent(LoginActivity.this
                                , MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("username", name);
                        bundle.putString("password", pass);
                        intent.putExtras(bundle);
                        LoginActivity.this.startActivity(intent);
                    }

                }
                if (!checkAcc)showErrorIncorrectToast();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });

    }

    @SuppressLint("SetTextI18n")
    public void showSuccessfulToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_success,
                findViewById(R.id.toast_success));
        TextView text = layout.findViewById(R.id.toast_text_success);
        text.setText("Correct username and password");
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
        text.setText("You must fill username and password");
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
        text.setText("Incorrect username or password");
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
//    public void showSuccessDialog(){
//        ConstraintLayout successConstraintLayout = findViewById(R.id.successConstraintLayout);
//        View view = LayoutInflater.from(this).inflate(R.layout.success_dialog,successConstraintLayout);
//        Button successDone = view.findViewById(R.id.successDone);
//        TextView textView = view.findViewById(R.id.successDesc);
//        textView.setText("Correct username and password");
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setView(view);
//        final AlertDialog  alertDialog= builder.create();
//        successDone.setOnClickListener(view1 -> {
//            alertDialog.dismiss();
//        });
//        if (alertDialog.getWindow() !=null){
//            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//        }
//        alertDialog.show();
//    }
}