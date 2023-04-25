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

public class ListFeedbackActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference myRef;
    FeedbackAdapter feedbackAdapter;
    ArrayList<Feedback> list,feedbacks;
    Button btnDashboard,btnSearch,btnRefresh;
    String name,feedback,searchValue;
    EditText edSearch;
    Boolean checkSearch=false;
    String username,password;
    Intent intent;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_feedback);
        btnDashboard = findViewById(R.id.button_dashboard);
        btnSearch=findViewById(R.id.button_search);
        btnRefresh=findViewById(R.id.button_refresh);
        edSearch = findViewById(R.id.edit_search);
        recyclerView = findViewById(R.id.list_feedbacks);
        myRef = FirebaseDatabase.getInstance().getReference().child("feedbacks");
        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
        feedbackAdapter = new FeedbackAdapter(this, list);
        recyclerView.setAdapter(feedbackAdapter);
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


        btnDashboard.setOnClickListener(view -> {
            Intent intentDashboard;
            if (username.equals("admin") && password.equals("admin1234")) {
                intentDashboard = new Intent(ListFeedbackActivity.this
                        , DashboardAdminActivity.class);
            } else {
                intentDashboard = new Intent(ListFeedbackActivity.this
                        , DashboardActivity.class);
            }
            bundle =new Bundle();
            bundle.putString("username",username);
            bundle.putString("password",password);
            intentDashboard.putExtras(bundle);
            ListFeedbackActivity.this.startActivity(intentDashboard);
        });
        btnSearch.setOnClickListener(view -> {
            edSearch.setText(edSearch.getText().toString());
            searchValue=edSearch.getText().toString();
            if (searchValue.isEmpty()) showSearchErrorEmptyToast();
            searchData(searchValue);
        });
        btnRefresh.setOnClickListener(view -> {
            feedbacks.clear();
            edSearch.setText("");
            refresh();
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

    public void searchData(String searchValue) {
        feedbacks=new ArrayList<>();
        for (int i=0;i<list.size();i++){
            name=list.get(i).getName();
            feedback=list.get(i).getFeedback();
            if (searchValue.contains(name)||searchValue.contains(feedback)){
                Feedback feedback1=new Feedback(name,feedback);
                feedbacks.add(feedback1);
                checkSearch=searchValue.contains(name)||searchValue.contains(feedback);
                showSearchSuccessfulToast();
                feedbackAdapter.setFeedbacks(feedbacks);
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
                    Feedback f = dataSnapshot.getValue(Feedback.class);
                    Log.d("feedback", String.valueOf(f));
                    list.add(f);
                }
                feedbackAdapter.setFeedbacks(list);
                feedbackAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}