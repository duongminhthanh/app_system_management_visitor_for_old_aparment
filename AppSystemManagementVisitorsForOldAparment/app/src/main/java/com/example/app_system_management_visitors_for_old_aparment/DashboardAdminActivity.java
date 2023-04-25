package com.example.app_system_management_visitors_for_old_aparment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardAdminActivity extends AppCompatActivity {

    ImageView imgVisitor, imgApartment, imgFeedback, imgLogOut, imgAccount;
    Bundle bundle;
    String username,password;
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
        if (bundle!=null){
            /*get data from LoginActivity*/
            username = bundle.getString("username");
            password = bundle.getString("password");
            Log.d("username",username);
            Log.d("password",password);
        }

        imgAccount.setOnClickListener(view -> {
            Intent intentAcc = new Intent(DashboardAdminActivity.this, ManageListAccountActivity.class);
            startActivity(intentAcc);
        });
        imgFeedback.setOnClickListener(view -> {
            Intent intentFeedbacks = new Intent(DashboardAdminActivity.this, ListFeedbackActivity.class);
            intentFeedbacks.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            /*set data*/
            if (bundle!=null){
                bundle.putString("username", username);
                bundle.putString("password", password);
            }
            intentFeedbacks.putExtras(bundle);
            DashboardAdminActivity.this.startActivity(intentFeedbacks);
        });
        imgLogOut.setOnClickListener(view -> {
            Intent intentLogin=new Intent(DashboardAdminActivity.this,LoginActivity.class);
            startActivity(intentLogin);
        });
        imgVisitor.setOnClickListener(view -> {
            Intent intentVisitor = new Intent(DashboardAdminActivity.this,ManageListVisitorActivity.class);
            intentVisitor.putExtra("username", username);
            intentVisitor.putExtra("password", password);
            startActivity(intentVisitor);
        });
        imgApartment.setOnClickListener(view -> {
            Intent intentApartment = new Intent(DashboardAdminActivity.this,ManageListApartmentActivity.class);
            intentApartment.putExtra("username", username);
            intentApartment.putExtra("password", password);
            startActivity(intentApartment);
        });
    }
}