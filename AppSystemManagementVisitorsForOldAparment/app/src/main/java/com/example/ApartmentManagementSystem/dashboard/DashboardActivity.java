package com.example.ApartmentManagementSystem.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ApartmentManagementSystem.list.ListApartmentActivity;
import com.example.ApartmentManagementSystem.list.ListFeedbackActivity;
import com.example.ApartmentManagementSystem.list.ListVisitorActivity;
import com.example.ApartmentManagementSystem.authentication.LoginActivity;
import com.example.ApartmentManagementSystem.main_activity.MainActivity;
import com.example.ApartmentManagementSystem.R;

public class DashboardActivity extends AppCompatActivity {

    ImageView imgVisitor, imgApartment, imgFeedbacks, imgLogout, imgHome;

    Intent intent;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        intent = getIntent();
        bundle = intent.getExtras();
        String username = bundle.getString("username");
        String password = bundle.getString("password");
        Log.d("username", username);
        Log.d("password", password);
        createView(username,password);
    }

    public void createView(String username,String password) {
        imgApartment = findViewById(R.id.image_apartment);
        imgApartment.setOnClickListener(view -> {
            Intent listApartment = new Intent(DashboardActivity.this, ListApartmentActivity.class);
            listApartment.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            if (bundle != null) {
                bundle.putString("username", username);
                bundle.putString("password", password);
            }
            listApartment.putExtras(bundle);
            startActivity(listApartment);
        });
        imgFeedbacks = findViewById(R.id.image_feedbacks);
        imgFeedbacks.setOnClickListener(view -> {
            Intent listFeedbacks = new Intent(DashboardActivity.this, ListFeedbackActivity.class);
            listFeedbacks.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            if (bundle != null) {
                bundle.putString("username", username);
                bundle.putString("password", password);
            }
            listFeedbacks.putExtras(bundle);
            startActivity(listFeedbacks);
        });
        imgLogout = findViewById(R.id.image_logout);
        imgLogout.setOnClickListener(view -> {
            Intent login = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(login);
        });
        imgVisitor = findViewById(R.id.image_visitor);
        imgVisitor.setOnClickListener(view -> {
            Intent listVisitor = new Intent(DashboardActivity.this, ListVisitorActivity.class);
            listVisitor.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            if (bundle != null) {
                bundle.putString("username", username);
                bundle.putString("password", password);
            }
            listVisitor.putExtras(bundle);
            startActivity(listVisitor);
        });
        imgHome = findViewById(R.id.image_home);
        imgHome.setOnClickListener(view -> {
            Intent home = new Intent(DashboardActivity.this, MainActivity.class);
            home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            bundle = new Bundle();
            bundle.putString("username", username);
            bundle.putString("password", password);
            home.putExtras(bundle);
            startActivity(home);
        });
    }


}