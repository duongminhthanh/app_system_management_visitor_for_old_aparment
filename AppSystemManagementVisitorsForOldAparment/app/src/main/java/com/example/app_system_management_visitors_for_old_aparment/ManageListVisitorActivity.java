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

public class ManageListVisitorActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference myRef;
    VisitorManagementAdapter visitorManagementAdapter;
    ArrayList<Visitor> list;
    Button btnDashboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_list_visitor_acitvity);
        recyclerView=findViewById(R.id.list_visitor);
        btnDashboard=findViewById(R.id.button_dashboard);
        myRef= FirebaseDatabase.getInstance().getReference().child("list_visitor");
        recyclerView.setHasFixedSize(true);
        list=new ArrayList<>();
        visitorManagementAdapter=new VisitorManagementAdapter(this,list);
        recyclerView.setAdapter(visitorManagementAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Visitor v=dataSnapshot.getValue(Visitor.class);
                    Log.d("visitor",String.valueOf(v));
                    list.add(v);
                }
                visitorManagementAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnDashboard.setOnClickListener(view -> {
            Intent intentDashboard=new Intent(this,DashboardAdminActivity.class);
            startActivity(intentDashboard);
        });
    }
}