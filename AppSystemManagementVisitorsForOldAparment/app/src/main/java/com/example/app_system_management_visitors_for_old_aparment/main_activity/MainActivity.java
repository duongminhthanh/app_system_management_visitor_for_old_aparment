package com.example.app_system_management_visitors_for_old_aparment.main_activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_system_management_visitors_for_old_aparment.R;
import com.example.app_system_management_visitors_for_old_aparment.authentication.PinCodeActivity;

public class MainActivity extends AppCompatActivity {
    Button btnRegisterVisitingForm, btnRating, btnFeedback;
    Intent intent;
    ImageView img_pinCode;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRegisterVisitingForm = findViewById(R.id.btn_register_visit);
        btnFeedback = findViewById(R.id.btn_feedback);
        btnRating = findViewById(R.id.btn_rating);
        img_pinCode = findViewById(R.id.image_pin_code);
        intent = getIntent();
        bundle = intent.getExtras();
        String username = bundle.getString("username");
        String password = bundle.getString("password");
        Log.d("username",username);
        Log.d("password",password);

        btnRegisterVisitingForm.setOnClickListener(view -> {
            intent = new Intent(this, RegisterFormActivity.class);
            bundle.putString("username", username);
            bundle.putString("password", password);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        btnFeedback.setOnClickListener(view -> {
            intent = new Intent(this, FeedbackActivity.class);
            bundle.putString("username", username);
            bundle.putString("password", password);
            intent.putExtras(bundle);
            startActivity(intent);
        });


        btnRating.setOnClickListener(view -> {
            intent = new Intent(this, RatingActivity.class);
            bundle.putString("username", username);
            bundle.putString("password", password);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        img_pinCode.setOnClickListener(view -> {
            intent = new Intent(this, PinCodeActivity.class);
            bundle.putString("username", username);
            bundle.putString("password", password);
            intent.putExtras(bundle);
            startActivity(intent);
        });

    }

}