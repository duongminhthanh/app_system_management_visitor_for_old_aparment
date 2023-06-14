package com.example.ApartmentManagementSystem.main_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import com.example.ApartmentManagementSystem.R;
import com.example.ApartmentManagementSystem.authentication.PinCodeActivity;

public class RatingActivity extends AppCompatActivity {
    Button btnSubmit;
    RatingBar ratingBar;
    String username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnSubmit=findViewById(R.id.btn_submit);
        ratingBar=findViewById(R.id.rating_start);
        ratingBar.setOnClickListener(view -> ratingBar.setRating(3.0f));
        btnSubmit.setOnClickListener(view -> {
            ratingBar.getNumStars();
            ratingBar.getRating();
            showRatingSuccessfulToast();
            Intent intent=new Intent(RatingActivity.this, MainActivity.class);
//            bundle.putString("username", username);
//            bundle.putString("password", password);
//            intent.putExtras(bundle);
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
        toast.setGravity(Gravity.TOP|Gravity.RIGHT, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}