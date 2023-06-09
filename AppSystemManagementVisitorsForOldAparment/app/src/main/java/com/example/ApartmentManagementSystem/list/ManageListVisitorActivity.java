package com.example.ApartmentManagementSystem.list;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ApartmentManagementSystem.R;
import com.example.ApartmentManagementSystem.adapter.VisitorManagementAdapter;
import com.example.ApartmentManagementSystem.chart.ChartAdminActivity;
import com.example.ApartmentManagementSystem.model.Visitor;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
public class ManageListVisitorActivity extends AppCompatActivity {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    RecyclerView recyclerView;
    DatabaseReference myRef;
    VisitorManagementAdapter visitorManagementAdapter;
    ArrayList<Visitor> list, visitors;
    ArrayList<String> dateList;
    FloatingActionButton btnSearch, btnFromDate, btnToDate,btnMenu;
    ImageView refresh;
    TextView txtFrom, txtTo;
    String from, to, name, roomId, visitTime, idCard, date, d, m, y;
    Date d1 = null, d2 = null;
    Timestamp t1, t2;
    String username, password;
    Intent intent;
    Bundle bundle;
    NestedScrollView scrollView;
    int count = 0;
    ProgressBar progressBar;
    Context context;
    boolean clickMenu=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_list_visitor);
        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        recyclerView = findViewById(R.id.list_visitor);
//        btnDashboard = findViewById(R.id.button_dashboard);
        btnSearch = findViewById(R.id.button_search);
        refresh = findViewById(R.id.image_refresh);
        btnFromDate = findViewById(R.id.calendar1);
        btnFromDate.hide();
        btnToDate = findViewById(R.id.calendar2);
        btnToDate.hide();
        btnMenu = findViewById(R.id.button_menu);
        context = this;
        myRef = FirebaseDatabase.getInstance().getReference().child("list_visitor");
        recyclerView.setHasFixedSize(true);
        scrollView = findViewById(R.id.scroll);
        progressBar = findViewById(R.id.loading);
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
        txtFrom = findViewById(R.id.text_from);
        txtTo = findViewById(R.id.text_to);
        list = new ArrayList<>();
        dateList = new ArrayList<>();
        visitorManagementAdapter = new VisitorManagementAdapter(this, list);
        recyclerView.setAdapter(visitorManagementAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//        recyclerView.addItemDecoration(itemDecoration);

        intent = getIntent();
        bundle = intent.getExtras();
        /*get data form dashboard admin*/
        if (bundle != null) {
            username = bundle.getString("username");
            password = bundle.getString("password");
            Log.d("username", username);
            Log.d("password", password);
        }
        getData();
        btnFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        txtFrom.setText(simpleDateFormat.format(calendar.getTime()));
                        d1 = calendar.getTime();
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        btnToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        txtTo.setText(simpleDateFormat.format(calendar.getTime()));
                        d2 = calendar.getTime();
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        btnMenu.setOnClickListener(view -> {
            if (clickMenu){
                btnFromDate.show();
                btnToDate.show();
                clickMenu=false;
            }else {
                btnFromDate.hide();
                btnToDate.hide();
                clickMenu=true;
            }
        });
        btnSearch.setOnClickListener(view -> {
            from = txtFrom.getText().toString().trim();
            to = txtTo.getText().toString().trim();
            Log.d("from date", from);
            Log.d("to date", to);
            if (from.equals("From date") || to.equals("To date")) {
                showSearchErrorEmptyToast();
            }
            else {
                searchData();
            }
        });
        refresh.setOnClickListener(view -> {
            visitors.clear();
            txtFrom.setText("From date");
            txtTo.setText("To date");
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
//                    dateList.add(date);

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
    public void searchData() {
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
                            visitors.add(new Visitor(name, roomId, visitTime, idCard, date));

                        }
                        visitorManagementAdapter.setVisitors(visitors);
                        Log.d("visitor", String.valueOf(visitors));
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
        toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
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
        toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}