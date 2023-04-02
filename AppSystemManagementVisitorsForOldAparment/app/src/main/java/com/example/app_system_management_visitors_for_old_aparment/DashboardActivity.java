package com.example.app_system_management_visitors_for_old_aparment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imgVisitor,imgApartment,imgFeedbacks,imgLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        createView();
    }
    public void createView(){
        imgApartment=findViewById(R.id.image_apartment);
        imgApartment.setOnClickListener(this);
        imgFeedbacks=findViewById(R.id.image_feedbacks);
        imgFeedbacks.setOnClickListener(this);
        imgLogout=findViewById(R.id.image_logout);
        imgLogout.setOnClickListener(this);
        imgVisitor=findViewById(R.id.image_visitor);
        imgVisitor.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.image_visitor:
                Intent listVisitor=new Intent(this,ListVisitorActivity.class);
                startActivity(listVisitor);
            case R.id.image_apartment:
                Intent listApartment=new Intent(this,ListApartmentActivity.class);
                startActivity(listApartment);
            case R.id.image_feedbacks:
                Intent listFeedbacks=new Intent(this,ListFeedbackActivity.class);
                startActivity(listFeedbacks);
            case R.id.image_logout:
                Intent login=new Intent(this,LoginActivity.class);
                startActivity(login);
        }
    }
}