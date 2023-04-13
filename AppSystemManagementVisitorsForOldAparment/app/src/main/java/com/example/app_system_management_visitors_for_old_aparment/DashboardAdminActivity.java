package com.example.app_system_management_visitors_for_old_aparment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardAdminActivity extends AppCompatActivity {

    ImageView imgVisitor, imgApartment, imgFeedback, imgLogOut, imgAccount;

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
        /*get data from LoginActivity*/
        String username = intent.getStringExtra("username");

        String password = intent.getStringExtra("password");
        imgAccount.setOnClickListener(view -> {
            Intent intentAcc = new Intent(DashboardAdminActivity.this, ManageListAccountActivity.class);
            intentAcc.putExtra("username", username);
            intentAcc.putExtra("password", password);
            startActivity(intentAcc);
        });
        imgFeedback.setOnClickListener(view -> {
            Intent intentFeedbacks = new Intent(DashboardAdminActivity.this, ListFeedbackActivity.class);
            /*set data*/
            intentFeedbacks.putExtra("username", username);
            intentFeedbacks.putExtra("password", password);

            startActivity(intentFeedbacks);
        });
        imgLogOut.setOnClickListener(view -> {
            Intent intentLogin=new Intent(DashboardAdminActivity.this,LoginActivity.class);
            startActivity(intentLogin);
        });
        imgVisitor.setOnClickListener(view -> {
            Intent intentVisitor = new Intent(DashboardAdminActivity.this,ListVisitorActivity.class);
            intentVisitor.putExtra("username", username);
            intentVisitor.putExtra("password", password);
            startActivity(intentVisitor);
        });
        imgApartment.setOnClickListener(view -> {
            Intent intentApartment = new Intent(DashboardAdminActivity.this,ListApartmentActivity.class);
            intentApartment.putExtra("username", username);
            intentApartment.putExtra("password", password);
            startActivity(intentApartment);
        });
    }
}