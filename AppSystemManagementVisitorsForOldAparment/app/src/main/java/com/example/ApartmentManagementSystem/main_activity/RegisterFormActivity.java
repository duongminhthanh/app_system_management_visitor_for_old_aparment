package com.example.ApartmentManagementSystem.main_activity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ApartmentManagementSystem.R;
import com.example.ApartmentManagementSystem.authentication.PinCodeActivity;
import com.example.ApartmentManagementSystem.model.Apartment;
import com.example.ApartmentManagementSystem.model.Visitor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
public class RegisterFormActivity extends AppCompatActivity {
    EditText edName, edIdCard, edTime;
    Spinner spinner;
    ArrayList<Apartment> list;
    ArrayList<String> apartmentId;
    ArrayAdapter<String> roomIdAdapter;
    DatabaseReference apartmentRef, visitorRef;
    String name, idCard, time, roomId;
    Visitor v;
    Button btnSend;
    Intent intent;
    Bundle bundle;
    String username, password;
    Date d = null;
    Timestamp t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        edName = findViewById(R.id.edit_name_visitor);
        edIdCard = findViewById(R.id.edit_id_card);
        edTime = findViewById(R.id.edit_time);
        spinner = findViewById(R.id.spinner_apartment_id);
        btnSend = findViewById(R.id.button);
//        intent = getIntent();
//        bundle = intent.getExtras();
//        username = bundle.getString("username");
//        password = bundle.getString("password");
//        Log.d("username", username);
//        Log.d("password", password);
        list = new ArrayList<>();
        apartmentId = new ArrayList<>();
        apartmentRef = FirebaseDatabase.getInstance().getReference().
                child("list_apartment");
        visitorRef = FirebaseDatabase.getInstance().getReference().child("list_visitor");
        apartmentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Apartment a = dataSnapshot.getValue(Apartment.class);
                    String roomID = Objects.requireNonNull(dataSnapshot.child("room_id")
                            .getValue()).toString();
                    Log.d("apartment", String.valueOf(a));
                    Log.d("room_id", roomID);
                    list.add(a);

                    apartmentId.add(roomID);
                    roomIdAdapter = new ArrayAdapter<>
                            (RegisterFormActivity.this, R.layout.style_spinner, apartmentId);
                    spinner.setAdapter(roomIdAdapter);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            roomId = String.valueOf(adapterView.getSelectedItem());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    roomIdAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnSend.setOnClickListener(view -> {
            edName.setText(edName.getText().toString());
            edTime.setText(edTime.getText().toString());
            edIdCard.setText(edIdCard.getText().toString());
            name = edName.getText().toString();
            idCard = edIdCard.getText().toString();
            time = edTime.getText().toString();
            if (name.isEmpty() || idCard.isEmpty() || time.isEmpty()) showErrorEmptyToast();
            else readData(name, idCard, time, roomId);
        });

    }
    public void readData(String name, String idCard, String time, String roomId) {
        visitorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                v = new Visitor();
                v.setName(name);
                v.setId_card(idCard);
                v.setRoom_id(roomId);
                v.setVisit_time(time);
                v.setDate(v.getDate());
                visitorRef.child(name).setValue(v);
                showAddSuccessfulToast();
                Intent intent = new Intent(RegisterFormActivity.this, MainActivity.class);
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
        text.setText("Register successfully");
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
}