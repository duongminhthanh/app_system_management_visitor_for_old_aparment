package com.example.ApartmentManagementSystem.list;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ApartmentManagementSystem.R;
import com.example.ApartmentManagementSystem.adapter.VisitorManagementAdapter;
import com.example.ApartmentManagementSystem.dashboard.DashboardActivity;
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

public class ManageListVisitorActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference myRef;
    VisitorManagementAdapter visitorManagementAdapter;
    ArrayList<Visitor> list, visitors;
    ImageView imgDashboard, imgSearch, imgRefresh;
    EditText edFrom, edTo;
    String from, to, name, roomId, visitTime, idCard, date;
    Date d1=null,d2=null;
    Timestamp t1,t2;
    String username,password;
    Intent intent;
    Bundle bundle;
    NestedScrollView scrollView;
    int count=0;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_list_visitor_acitvity);
        recyclerView = findViewById(R.id.list_visitor);
        imgDashboard = findViewById(R.id.img_dashboard);
        imgSearch = findViewById(R.id.img_search);
        imgRefresh = findViewById(R.id.img_refresh);
        myRef = FirebaseDatabase.getInstance().getReference().child("list_visitor");
        recyclerView.setHasFixedSize(true);
        scrollView=findViewById(R.id.scroll);
        progressBar=findViewById(R.id.loading);
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
        edFrom = findViewById(R.id.edit_from);
        edTo = findViewById(R.id.edit_to);
        list = new ArrayList<>();
        visitorManagementAdapter = new VisitorManagementAdapter(this, list);
        recyclerView.setAdapter(visitorManagementAdapter);
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

        imgDashboard.setOnClickListener(view -> {
            Intent intentDashboard = new Intent(this, DashboardActivity.class);
            bundle =new Bundle();
            bundle.putString("username",username);
            bundle.putString("password",password);
            intentDashboard.putExtras(bundle);
            startActivity(intentDashboard);
        });
        imgSearch.setOnClickListener(view -> {
            edFrom.setText(edFrom.getText().toString());
            edTo.setText(edTo.getText().toString());
            from = edFrom.getText().toString();
            to = edTo.getText().toString();
            if (from.isEmpty() || to.isEmpty()) showSearchErrorEmptyToast();
            else{
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
                try {
                    d1= dateFormat.parse(from);
                    d2= dateFormat.parse(to);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                t1=new Timestamp(d1.getTime());
                t2=new Timestamp(d2.getTime());
                Log.d("t1",t1.toString());
                Log.d("t2",t2.toString());
                searchData(from,to);

            }
        });
        imgRefresh.setOnClickListener(view -> {
            visitors.clear();
            edFrom.setText("");
            edTo.setText("");
            refresh();
        });
    }

    public void getData() {
        myRef.orderByChild("date").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Visitor v = dataSnapshot.getValue(Visitor.class);
                    name = Objects.requireNonNull(dataSnapshot.child("name")
                            .getValue()).toString();
                    roomId = Objects.requireNonNull(dataSnapshot.child("room_id")
                            .getValue()).toString();
                    visitTime = Objects.requireNonNull(dataSnapshot.child("visit_time")
                            .getValue()).toString();
                    idCard = Objects.requireNonNull(dataSnapshot.child("id_card")
                            .getValue()).toString();
                    date = Objects.requireNonNull(dataSnapshot.child("date")
                            .getValue()).toString();
                    Log.d("visitor", String.valueOf(v));
                    list.add(new Visitor(name, roomId, visitTime, idCard, date));

                }
                visitorManagementAdapter.setVisitors(list);
                visitorManagementAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
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
                visitorManagementAdapter.setVisitors(list);
                visitorManagementAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void searchData(String from, String to) {
        visitors = new ArrayList<>();
        myRef.orderByChild("date").startAt(from).endAt(to)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Visitor v = dataSnapshot.getValue(Visitor.class);
                            name = Objects.requireNonNull(dataSnapshot.child("name")
                                    .getValue()).toString();
                            roomId = Objects.requireNonNull(dataSnapshot.child("room_id")
                                    .getValue()).toString();
                            visitTime = Objects.requireNonNull(dataSnapshot.child("visit_time")
                                    .getValue()).toString();
                            idCard = Objects.requireNonNull(dataSnapshot.child("id_card")
                                    .getValue()).toString();
                            date = Objects.requireNonNull(dataSnapshot.child("date")
                                    .getValue()).toString();
                            Log.d("visitor", String.valueOf(v));
                            visitors.add(new Visitor(name, roomId, visitTime, idCard, date));
                        }
                        visitorManagementAdapter.setVisitors(visitors);
                        visitorManagementAdapter.notifyDataSetChanged();
                        showSearchSuccessfulToast();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


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