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

public class ManageListAccountActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference myRef;
    AccountAdapter accountAdapter;
    ArrayList<Account> list;
    Button btnDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_list_account);
        recyclerView = findViewById(R.id.list_manage_account);
        myRef = FirebaseDatabase.getInstance().getReference().child("list_account");
        list = new ArrayList<>();
        accountAdapter = new AccountAdapter(this, list);
        recyclerView.setAdapter(accountAdapter);
        btnDashboard = findViewById(R.id.button_dashboard);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnDashboard.setOnClickListener(view -> {
            Intent intentDashboard = new Intent(this, DashboardAdminActivity.class);
            startActivity(intentDashboard);
        });
        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Account a = dataSnapshot.getValue(Account.class);
                    Log.d("account", String.valueOf(a));
                    list.add(a);
                }
                accountAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}