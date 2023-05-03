package com.example.app_system_management_visitors_for_old_aparment.main_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_system_management_visitors_for_old_aparment.R;

public class RatingActivity extends AppCompatActivity {
    Button btnSubmit;
    RatingBar ratingBar;
    Intent intent;
    Bundle bundle;
    String username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        btnSubmit=findViewById(R.id.btn_submit);
        ratingBar=findViewById(R.id.rating_start);
        ratingBar.setOnClickListener(view -> ratingBar.setRating(3.5f));
        intent = getIntent();
        bundle = intent.getExtras();
        username = bundle.getString("username");
        password = bundle.getString("password");
        Log.d("username",username);
        Log.d("password",password);
        btnSubmit.setOnClickListener(view -> {
            ratingBar.getNumStars();
            ratingBar.getRating();
            showRatingSuccessfulToast();
            Intent intent=new Intent(RatingActivity.this, MainActivity.class);
            bundle.putString("username", username);
            bundle.putString("password", password);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }
    @SuppressLint("SetTextI18n")
    public void showRatingSuccessfulToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_success,
                findViewById(R.id.toast_success));
        TextView text = layout.findViewById(R.id.toast_text_success);
        text.setText("Thank you for your rating");
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}