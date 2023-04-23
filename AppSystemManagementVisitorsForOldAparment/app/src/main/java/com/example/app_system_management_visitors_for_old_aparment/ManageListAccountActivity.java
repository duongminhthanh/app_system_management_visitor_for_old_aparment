package com.example.app_system_management_visitors_for_old_aparment;

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
    ArrayList<Account> list, accounts;
    Button btnCreate, btnDashboard, btnSearch, btnRefresh;
    String searchValue, accId, username, password, pinCode;
    EditText edSearch;

    Boolean checkSearch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_list_account);
        recyclerView = findViewById(R.id.list_manage_account);
        myRef = FirebaseDatabase.getInstance().getReference().child("list_account");
        list = new ArrayList<>();
        accountAdapter = new AccountAdapter(this, list);
        recyclerView.setAdapter(accountAdapter);
        btnCreate = findViewById(R.id.button_create);
        btnDashboard = findViewById(R.id.button_dashboard);
        btnSearch = findViewById(R.id.button_search);
        btnRefresh = findViewById(R.id.button_refresh);
        edSearch = findViewById(R.id.edit_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnDashboard.setOnClickListener(view -> {
            Intent intentDashboard = new Intent(this, DashboardAdminActivity.class);
            startActivity(intentDashboard);
        });
        btnCreate.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddNewAccountActivity.class);
            startActivity(intent);
        });
        btnSearch.setOnClickListener(view -> {
            edSearch.setText(edSearch.getText().toString());
            searchValue = edSearch.getText().toString();
            if (searchValue.isEmpty()) showSearchErrorEmptyToast();
            searchData(searchValue);
        });
        btnRefresh.setOnClickListener(view -> {
            accounts.clear();
            edSearch.setText("");
            refresh();
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

    public void refresh() {
        list.clear();
        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Account a = dataSnapshot.getValue(Account.class);
                    Log.d("account", String.valueOf(a));
                    list.add(a);
                }
                accountAdapter.setAccounts(list);
                accountAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void searchData(String searchValue) {
        accounts = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            accId = list.get(i).getAcc_id();
            username = list.get(i).getUsername();
            password = list.get(i).getPassword();
            pinCode = list.get(i).getPin_code();
            if (searchValue.contains(accId) || searchValue.contains(username) ||
                    searchValue.contains(password) || searchValue.contains(pinCode)) {
                Account a = new Account(accId, username, password, pinCode);
                accounts.add(a);
                checkSearch = searchValue.contains(accId) || searchValue.contains(username) ||
                        searchValue.contains(password) || searchValue.contains(pinCode);
                showSearchSuccessfulToast();
                accountAdapter.setAccounts(accounts);
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