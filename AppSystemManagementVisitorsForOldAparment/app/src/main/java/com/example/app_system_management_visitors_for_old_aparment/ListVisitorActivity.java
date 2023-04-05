package com.example.app_system_management_visitors_for_old_aparment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

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


public class ListVisitorActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference myRef;
    VisitorAdapter visitorAdapter;
    ArrayList<Visitor> list;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_visitor);
        recyclerView=findViewById(R.id.list_visitor);
        myRef= FirebaseDatabase.getInstance().getReference().child("list_visitor");
        recyclerView.setHasFixedSize(true);
        list=new ArrayList<>();
        visitorAdapter=new VisitorAdapter(this,list);
        recyclerView.setAdapter(visitorAdapter);
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
                visitorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}