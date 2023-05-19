package com.example.ApartmentManagementSystem.list;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ApartmentManagementSystem.R;
import com.example.ApartmentManagementSystem.adapter.ApartmentManagementAdapter;
import com.example.ApartmentManagementSystem.crud.AddNewApartmentActivity;
import com.example.ApartmentManagementSystem.dashboard.DashboardAdminActivity;
import com.example.ApartmentManagementSystem.model.Apartment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    ArrayList<Apartment> list, apartments;
    FloatingActionButton btnCreate,btnDashboard,btnSearch,btnRefresh;
    String searchValue, ownerName, ownerPhone, roomId;
    EditText edSearch;

    Boolean checkSearch = false;
    String username,password;
    Intent intent;
    Bundle bundle;
    NestedScrollView scrollView;
    int count=0;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_list_apartment);
        recyclerView = findViewById(R.id.list_apartment);
        edSearch = findViewById(R.id.edit_search);
        myRef = FirebaseDatabase.getInstance().getReference().child("list_apartment");
        recyclerView.setHasFixedSize(true);
        scrollView=findViewById(R.id.scroll);
        progressBar=findViewById(R.id.loading);
        list = new ArrayList<>();
        btnCreate = findViewById(R.id.button_create);
        btnDashboard=findViewById(R.id.button_dashboard);
        btnSearch = findViewById(R.id.button_search);
        btnRefresh = findViewById(R.id.button_refresh);
        apartmentManagementAdapter = new ApartmentManagementAdapter(this, list);
        recyclerView.setAdapter(apartmentManagementAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        intent = getIntent();
        bundle = intent.getExtras();
        /*get data form dashboard admin*/
        if (bundle!=null){
            username = bundle.getString("username");
            password = bundle.getString("password");
            Log.d("username",username);
            Log.d("password",password);
        }
        getData();
        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener)
                (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                        // in this method we are incrementing page number,
                        // making progress bar visible and calling get data method.
                        count++;
                        // on below line we are making our progress bar visible.
                        progressBar.setVisibility(View.VISIBLE);
                        if (count < 20) {
                            // on below line we are again calling
                            // a method to load data in our array list.
                            getData();
                        }
                    }
                });

        btnDashboard.setOnClickListener(view -> {
            Intent intentDashboard = new Intent(this, DashboardAdminActivity.class);
            bundle =new Bundle();
            bundle.putString("username",username);
            bundle.putString("password",password);
            intentDashboard.putExtras(bundle);
            startActivity(intentDashboard);
        });
        btnCreate.setOnClickListener(view -> {
            Intent intentCreate = new Intent(this, AddNewApartmentActivity.class);
            startActivity(intentCreate);
        });
        btnSearch.setOnClickListener(view -> {
            edSearch.setText(edSearch.getText().toString());
            searchValue = edSearch.getText().toString();
            if (searchValue.isEmpty()) showSearchErrorEmptyToast();
            searchData(searchValue);
        });
        btnRefresh.setOnClickListener(view -> {
            apartments.clear();
            edSearch.setText("");
            refresh();
        });

    }

    public void getData() {
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
        apartments = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ownerName = list.get(i).getOwner_name();
            ownerPhone = list.get(i).getOwner_phone();
            roomId = list.get(i).getRoom_id();
            if (searchValue.contains(ownerName) || searchValue.contains(ownerPhone)
                    || searchValue.contains(roomId)) {
                Apartment a = new Apartment(ownerName, ownerPhone, roomId);
                apartments.add(a);
                checkSearch = searchValue.contains(ownerName) || searchValue.contains(ownerPhone)
                        || searchValue.contains(roomId);

                showSearchSuccessfulToast();
                apartmentManagementAdapter.setApartments(apartments);
                break;
            }

        }

        if (!checkSearch) showSearchFailToast();


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

    public void refresh() {
        list.clear();
        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Apartment a = dataSnapshot.getValue(Apartment.class);
                    Log.d("apartment", String.valueOf(a));
                    list.add(a);
                }
                apartmentManagementAdapter.setApartments(list);
                apartmentManagementAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}

