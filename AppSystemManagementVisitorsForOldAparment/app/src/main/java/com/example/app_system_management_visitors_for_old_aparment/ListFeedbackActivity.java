package com.example.app_system_management_visitors_for_old_aparment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

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

public class ListFeedbackActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference myRef;
    FeedbackAdapter feedbackAdapter;
    ArrayList<Feedback> list;
    Button btnDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_feedback);
        btnDashboard = findViewById(R.id.button_dashboard);
        recyclerView = findViewById(R.id.list_feedbacks);
        myRef = FirebaseDatabase.getInstance().getReference().child("feedbacks");
        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
        feedbackAdapter = new FeedbackAdapter(this, list);
        recyclerView.setAdapter(feedbackAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnDashboard.setOnClickListener(view -> {
            Intent intent = getIntent();
            /*get data form dashboard admin*/
            String username = intent.getStringExtra("username");
            String password = intent.getStringExtra("password");
            Intent intentDashboard;
            if (username.equals("admin") && password.equals("admin1234")) {
                intentDashboard = new Intent(ListFeedbackActivity.this
                        , DashboardAdminActivity.class);
                startActivity(intentDashboard);
            } else {
                intentDashboard = new Intent(ListFeedbackActivity.this
                        , DashboardActivity.class);
                startActivity(intentDashboard);
            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Feedback f = dataSnapshot.getValue(Feedback.class);
                    Log.d("feedback", String.valueOf(f));
                    list.add(f);
                }
                feedbackAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}