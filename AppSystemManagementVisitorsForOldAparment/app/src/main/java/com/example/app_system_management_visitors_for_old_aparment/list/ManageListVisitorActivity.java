package com.example.app_system_management_visitors_for_old_aparment.list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.example.app_system_management_visitors_for_old_aparment.R;
import com.example.app_system_management_visitors_for_old_aparment.adapter.VisitorManagementAdapter;
import com.example.app_system_management_visitors_for_old_aparment.dashboard.DashboardAdminActivity;
import com.example.app_system_management_visitors_for_old_aparment.model.Visitor;
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
    Button btnDashboard, btnSearch, btnRefresh;
    EditText edFrom, edTo;
    String from, to, name, roomId, visitTime, idCard, date;
    Date d1=null,d2=null;
    Timestamp t1,t2;
    String username,password;
    Intent intent;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_list_visitor_acitvity);
        recyclerView = findViewById(R.id.list_visitor);
        btnDashboard = findViewById(R.id.button_dashboard);
        btnSearch = findViewById(R.id.button_search);
        btnRefresh = findViewById(R.id.button_refresh);
        myRef = FirebaseDatabase.getInstance().getReference().child("list_visitor");
        recyclerView.setHasFixedSize(true);
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
        btnDashboard.setOnClickListener(view -> {
            Intent intentDashboard = new Intent(this, DashboardAdminActivity.class);
            bundle =new Bundle();
            bundle.putString("username",username);
            bundle.putString("password",password);
            intentDashboard.putExtras(bundle);
            startActivity(intentDashboard);
        });
        btnSearch.setOnClickListener(view -> {
            edFrom.setText(edFrom.getText().toString());
            edTo.setText(edTo.getText().toString());
            from = edFrom.getText().toString();
            to = edTo.getText().toString();
            if (from.isEmpty() || to.isEmpty()) showSearchErrorEmptyToast();
            else{
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
                searchData(t1.toString(), t2.toString());

            }
        });
        btnRefresh.setOnClickListener(view -> {
            visitors.clear();
            edFrom.setText("");
            edTo.setText("");
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