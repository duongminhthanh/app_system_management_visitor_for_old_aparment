package com.example.ApartmentManagementSystem.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ApartmentManagementSystem.list.ListFeedbackActivity;
import com.example.ApartmentManagementSystem.authentication.LoginActivity;
import com.example.ApartmentManagementSystem.list.ManageListAccountActivity;
import com.example.ApartmentManagementSystem.list.ManageListApartmentActivity;
import com.example.ApartmentManagementSystem.list.ManageListVisitorActivity;
import com.example.ApartmentManagementSystem.R;

public class DashboardAdminActivity extends AppCompatActivity {

    ImageView imgVisitor, imgApartment, imgFeedback, imgLogOut, imgAccount;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);
        imgAccount = findViewById(R.id.image_account);
        imgApartment = findViewById(R.id.image_apartment);
        imgFeedback = findViewById(R.id.image_feedbacks);
        imgVisitor = findViewById(R.id.image_visitor);
        imgLogOut = findViewById(R.id.image_logout);
        Intent intent = getIntent();
        bundle = intent.getExtras();

        /*get data from LoginActivity*/
        String username = bundle.getString("username");
        String password = bundle.getString("password");

        imgAccount.setOnClickListener(view -> {
            Intent intentAcc = new Intent(DashboardAdminActivity.this, ManageListAccountActivity.class);
            intentAcc.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            if (bundle != null) {
                bundle.putString("username", username);
                bundle.putString("password", password);
            }
            intentAcc.putExtras(bundle);
            startActivity(intentAcc);
        });
        imgFeedback.setOnClickListener(view -> {
            Intent intentFeedbacks = new Intent(DashboardAdminActivity.this, ListFeedbackActivity.class);
            intentFeedbacks.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            /*set data*/
            if (bundle != null) {
                bundle.putString("username", username);
                bundle.putString("password", password);
            }
            intentFeedbacks.putExtras(bundle);
            DashboardAdminActivity.this.startActivity(intentFeedbacks);
        });
        imgLogOut.setOnClickListener(view -> {
            Intent intentLogin = new Intent(DashboardAdminActivity.this, LoginActivity.class);
            startActivity(intentLogin);
        });
        imgVisitor.setOnClickListener(view -> {
            Intent intentVisitor = new Intent(DashboardAdminActivity.this, ManageListVisitorActivity.class);
            intentVisitor.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            if (bundle != null) {
                bundle.putString("username", username);
                bundle.putString("password", password);
            }
            intentVisitor.putExtras(bundle);
            startActivity(intentVisitor);
        });
        imgApartment.setOnClickListener(view -> {
            Intent intentApartment = new Intent(DashboardAdminActivity.this, ManageListApartmentActivity.class);
            intentApartment.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            if (bundle != null) {
                bundle.putString("username", username);
                bundle.putString("password", password);
            }
            intentApartment.putExtras(bundle);
            startActivity(intentApartment);
        });
    }
}