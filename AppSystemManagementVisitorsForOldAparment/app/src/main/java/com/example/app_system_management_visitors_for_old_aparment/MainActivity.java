package com.example.app_system_management_visitors_for_old_aparment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btnRegisterVisitingForm, btnRating, btnFeedback;
    Intent intent;
    ImageView img_pinCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRegisterVisitingForm = findViewById(R.id.btn_register_visit);
        btnFeedback = findViewById(R.id.btn_feedback);
        btnRating = findViewById(R.id.btn_rating);
        img_pinCode=findViewById(R.id.image_pin_code);
        btnRegisterVisitingForm.setOnClickListener(view -> {
            intent = new Intent(this, RegisterFormActivity.class);
            startActivity(intent);
        });
        btnFeedback.setOnClickListener(view -> {
            intent = new Intent(this, FeedbackActivity.class);
            startActivity(intent);
        });


        btnRating.setOnClickListener(view -> {
            intent = new Intent(this, RatingActivity.class);
            startActivity(intent);
        });
        img_pinCode.setOnClickListener(view -> {
            intent=new Intent(this,PinCodeActivity.class);
            startActivity(intent);
        });
    }
}