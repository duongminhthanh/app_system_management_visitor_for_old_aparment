package com.example.app_system_management_visitors_for_old_aparment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity  {

    ImageView imgVisitor,imgApartment,imgFeedbacks,imgLogout,imgHome;
    String username,password;
    Intent intent;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        intent = getIntent();
        bundle = intent.getExtras();
        if (bundle!=null){
            username=bundle.getString("username");
            password=bundle.getString("password");
            Log.d("username",username);
            Log.d("password",password);
        }

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
            listFeedbacks.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            bundle.putString("username",username);
            bundle.putString("password",password);
            listFeedbacks.putExtras(bundle);
            DashboardActivity.this.startActivity(listFeedbacks);
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
        imgHome=findViewById(R.id.image_home);
        imgHome.setOnClickListener(view -> {
            Intent home=new Intent(this,MainActivity.class);
            startActivity(home);
        });
    }


}