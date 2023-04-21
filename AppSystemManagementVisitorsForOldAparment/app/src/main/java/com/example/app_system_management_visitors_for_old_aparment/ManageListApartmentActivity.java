package com.example.app_system_management_visitors_for_old_aparment;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManageListApartmentActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference myRef;
    ApartmentManagementAdapter apartmentManagementAdapter;
    ArrayList<Apartment> list;
    Button btnDashboard, btnCreate, btnSearch;

    String searchValue, ownerName, ownerPhone, roomId;
    EditText edSearch;

    Boolean checkSearch=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_list_apartment);
        btnDashboard = findViewById(R.id.button_dashboard);
        recyclerView = findViewById(R.id.list_apartment);
        edSearch = findViewById(R.id.edit_search);
        myRef = FirebaseDatabase.getInstance().getReference().child("list_apartment");
        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
        btnCreate = findViewById(R.id.button_create);
        btnSearch = findViewById(R.id.button_search);
        apartmentManagementAdapter = new ApartmentManagementAdapter(this, list);
        recyclerView.setAdapter(apartmentManagementAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnDashboard.setOnClickListener(view -> {
            Intent intentDashboard = new Intent(this, DashboardAdminActivity.class);
            startActivity(intentDashboard);
        });
        btnCreate.setOnClickListener(view -> {
            Intent intentCreate = new Intent(this, AddNewApartmentActivity.class);
            startActivity(intentCreate);
        });
        btnSearch.setOnClickListener(view -> {
            edSearch.setText(edSearch.getText().toString());
            searchValue = edSearch.getText().toString();
            searchData(searchValue);
        });
        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Apartment a = dataSnapshot.getValue(Apartment.class);
                    Log.d("apartment", String.valueOf(a));
                    list.add(a);
                }
                apartmentManagementAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @SuppressLint("NotifyDataSetChanged")
    public void searchData(String searchValue) {
        ArrayList<Apartment> apartments=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ownerName=list.get(i).getOwner_name();
            ownerPhone=list.get(i).getOwner_phone();
            roomId=list.get(i).getRoom_id();
            if (searchValue.contains(ownerName)||searchValue.contains(ownerPhone)
                    ||searchValue.contains(roomId)){
                Apartment a=new Apartment(ownerName,ownerPhone,roomId);
                apartments.add(a);
                checkSearch=searchValue.contains(ownerName)||searchValue.contains(ownerPhone)
                        ||searchValue.contains(roomId);
            }
        }
        if (searchValue.isEmpty())showSearchErrorEmptyToast();
        else if (!checkSearch)showSearchFailToast();
        else{
            apartmentManagementAdapter.setApartments(apartments);
            showSearchSuccessfulToast();
        }


    }

    @SuppressLint("SetTextI18n")
    public void showSearchSuccessfulToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_success,
                findViewById(R.id.toast_success));
        TextView text = layout.findViewById(R.id.toast_text_success);
        text.setText("Search data successfully");
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
    @SuppressLint("SetTextI18n")
    public void showSearchFailToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_error,
                findViewById(R.id.toast_success));
        TextView text = layout.findViewById(R.id.toast_text_error);
        text.setText("Search data fail");
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
    @SuppressLint("SetTextI18n")
    public void showSearchErrorEmptyToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_error,
                findViewById(R.id.toast_success));
        TextView text = layout.findViewById(R.id.toast_text_error);
        text.setText("You must enter data");
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}

