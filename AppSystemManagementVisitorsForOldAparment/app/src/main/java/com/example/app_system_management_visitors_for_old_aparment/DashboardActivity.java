package com.example.app_system_management_visitors_for_old_aparment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity  {

    ImageView imgVisitor,imgApartment,imgFeedbacks,imgLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        createView();
    }
    public void createView(){
        imgApartment=findViewById(R.id.image_apartment);
        imgApartment.setOnClickListener(view -> {
            Intent listApartment=new Intent(this,ListApartmentActivity.class);
            startActivity(listApartment);
        });
        imgFeedbacks=findViewById(R.id.image_feedbacks);
        imgFeedbacks.setOnClickListener(view -> {
            Intent listFeedbacks=new Intent(this,ListFeedbackActivity.class);
            startActivity(listFeedbacks);
        });
        imgLogout=findViewById(R.id.image_logout);
        imgLogout.setOnClickListener(view -> {
            Intent login=new Intent(this,LoginActivity.class);
            startActivity(login);
        });
        imgVisitor=findViewById(R.id.image_visitor);
        imgVisitor.setOnClickListener(view -> {
            Intent listVisitor=new Intent(this,ListVisitorActivity.class);
            startActivity(listVisitor);
        });

    }


}