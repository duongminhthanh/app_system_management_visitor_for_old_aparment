package com.example.app_system_management_visitors_for_old_aparment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListApartmentActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference myRef;
    ApartmentAdapter apartmentAdapter;
    ArrayList<Apartment> list;
    Button btnDashboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_apartment);
        btnDashboard=findViewById(R.id.button_dashboard);
        recyclerView=findViewById(R.id.list_apartment);
        myRef= FirebaseDatabase.getInstance().getReference().child("list_apartment");
        recyclerView.setHasFixedSize(true);
        list=new ArrayList<>();
        apartmentAdapter=new ApartmentAdapter(this,list);
        recyclerView.setAdapter(apartmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnDashboard.setOnClickListener(view -> {
            Intent intentDashboard=new Intent(this,DashboardActivity.class);
            startActivity(intentDashboard);
        });
        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Apartment a=dataSnapshot.getValue(Apartment.class);
                    Log.d("apartment",String.valueOf(a));
                    list.add(a);
                }
                apartmentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}