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
import java.util.Objects;


public class ListVisitorActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference myRef;
    VisitorAdapter visitorAdapter;
    ArrayList<Visitor> list, visitors;
    Button btnDashboard, btnSearch, btnRefresh;
    EditText edSearch;
    String searchValue, name, roomId, visitTime, idCard;
    Boolean checkSearch = false;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_visitor);
        recyclerView=findViewById(R.id.list_visitor);
        btnDashboard=findViewById(R.id.button_dashboard);
        btnSearch = findViewById(R.id.button_search);
        btnRefresh = findViewById(R.id.button_refresh);
        myRef= FirebaseDatabase.getInstance().getReference().child("list_visitor");
        recyclerView.setHasFixedSize(true);
        edSearch = findViewById(R.id.edit_search);
        list=new ArrayList<>();
        visitorAdapter=new VisitorAdapter(this,list);
        recyclerView.setAdapter(visitorAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRef.orderByChild("date").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Visitor v=dataSnapshot.getValue(Visitor.class);
                    String name= Objects.requireNonNull(dataSnapshot.child("name")
                            .getValue()).toString();
                    String roomId= Objects.requireNonNull(dataSnapshot.child("room_id")
                            .getValue()).toString();
                    String visitTime= Objects.requireNonNull(dataSnapshot.child("visit_time")
                            .getValue()).toString();
                    String idCard= Objects.requireNonNull(dataSnapshot.child("id_card")
                            .getValue()).toString();
                    String date= Objects.requireNonNull(dataSnapshot.child("date")
                            .getValue()).toString();
                    Log.d("visitor",String.valueOf(v));
                    list.add(new Visitor(name,roomId,visitTime,idCard,date));

                }
                visitorAdapter.setVisitors(list);
                visitorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnDashboard.setOnClickListener(view -> {
            Intent intentDashboard=new Intent(this,DashboardActivity.class);
            startActivity(intentDashboard);
        });
        btnSearch.setOnClickListener(view -> {
            edSearch.setText(edSearch.getText().toString());
            searchValue = edSearch.getText().toString();
            if (searchValue.isEmpty()) showSearchErrorEmptyToast();
            searchData(searchValue);
        });
        btnRefresh.setOnClickListener(view -> {
            visitors.clear();
            edSearch.setText("");
            refresh();
        });
    }
    public void refresh() {
        list.clear();
        myRef.orderByChild("date").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Visitor v = dataSnapshot.getValue(Visitor.class);
                    Log.d("visitor", String.valueOf(v));
                    list.add(v);
                }
                visitorAdapter.setVisitors(list);
                visitorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void searchData(String searchValue) {
        visitors = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            name = list.get(i).getName();
            roomId = list.get(i).getRoom_id();
            visitTime = list.get(i).getVisit_time();
            idCard = list.get(i).getId_card();
            if (searchValue.contains(name) || searchValue.contains(roomId)
                    || searchValue.contains(visitTime) || searchValue.contains(idCard)) {
                Visitor v = new Visitor(name, roomId, visitTime, idCard);
                visitors.add(v);
                checkSearch = searchValue.contains(name) || searchValue.contains(roomId)
                        || searchValue.contains(visitTime) || searchValue.contains(idCard);
                showSearchSuccessfulToast();
                visitorAdapter.setVisitors(visitors);
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
}