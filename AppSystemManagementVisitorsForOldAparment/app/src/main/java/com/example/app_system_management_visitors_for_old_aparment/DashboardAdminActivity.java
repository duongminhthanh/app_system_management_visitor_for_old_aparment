package com.example.app_system_management_visitors_for_old_aparment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardAdminActivity extends AppCompatActivity {

    ImageView imgVisitor,imgApartment,imgFeedback,imgLogOut,imgAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);
        imgAccount=findViewById(R.id.image_account);
        imgApartment=findViewById(R.id.image_apartment);
        imgFeedback=findViewById(R.id.image_feedbacks);
        imgVisitor=findViewById(R.id.image_visitor);
        imgLogOut=findViewById(R.id.image_logout);
        imgAccount.setOnClickListener(view -> {
            Intent intent=new Intent(this,ManageListAccountActivity.class);
            startActivity(intent);
        });
    }
}