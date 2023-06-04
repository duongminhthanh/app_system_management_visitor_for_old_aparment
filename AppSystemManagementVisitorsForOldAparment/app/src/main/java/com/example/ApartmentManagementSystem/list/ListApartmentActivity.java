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
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ApartmentManagementSystem.R;
import com.example.ApartmentManagementSystem.adapter.ApartmentAdapter;
import com.example.ApartmentManagementSystem.dashboard.DashboardActivity;
import com.example.ApartmentManagementSystem.model.Apartment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    ArrayList<Apartment> list,apartments;
    FloatingActionButton btnSearch;
    ImageView refresh;
    EditText edSearch;
    String searchValue, ownerName, ownerPhone, roomId;
    Boolean checkSearch = false;
    String username,password;
    Intent intent;
    Bundle bundle;
    ProgressBar progressBar;
    NestedScrollView scrollView;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_apartment);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
//        btnDashboard=findViewById(R.id.button_dashboard);
        btnSearch = findViewById(R.id.button_search);
        refresh = findViewById(R.id.image_refresh);
        edSearch = findViewById(R.id.edit_search);
        recyclerView=findViewById(R.id.list_apartment);
        progressBar=findViewById(R.id.loading);
        scrollView=findViewById(R.id.scroll);
        myRef= FirebaseDatabase.getInstance().getReference().child("list_apartment");
        recyclerView.setHasFixedSize(true);
        list=new ArrayList<>();
        apartmentAdapter=new ApartmentAdapter(this,list);
        recyclerView.setAdapter(apartmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
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
//        btnDashboard.setOnClickListener(view -> {
//            Intent intentDashboard=new Intent(this, DashboardActivity.class);
//            bundle =new Bundle();
//            bundle.putString("username",username);
//            bundle.putString("password",password);
//            intentDashboard.putExtras(bundle);
//            startActivity(intentDashboard);
//        });
        btnSearch.setOnClickListener(view -> {
            edSearch.setText(edSearch.getText().toString());
            searchValue = edSearch.getText().toString();
            if (searchValue.isEmpty()) showSearchErrorEmptyToast();
            searchData(searchValue);
        });
        refresh.setOnClickListener(view -> {
            apartments.clear();
            edSearch.setText("");
            refresh();
        });
        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
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
    }

    public void getData() {
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
                apartmentAdapter.setApartments(apartments);
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
                apartmentAdapter.setApartments(list);
                apartmentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}