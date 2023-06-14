package com.example.ApartmentManagementSystem.main_activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ApartmentManagementSystem.R;
import com.example.ApartmentManagementSystem.authentication.PinCodeActivity;
public class MainActivity extends AppCompatActivity {
    ImageView imgForm, imgRating, imgFeedback;
    Intent intent;
    ImageView img_pinCode;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgForm = findViewById(R.id.image_form);
        imgFeedback = findViewById(R.id.image_feedback);
        imgRating = findViewById(R.id.image_rating);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        img_pinCode = findViewById(R.id.image_pin_code);
        imgForm.setOnClickListener(view -> {
            intent = new Intent(MainActivity.this, RegisterFormActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            bundle.putString("username", username);
//            bundle.putString("password", password);
//            intent.putExtras(bundle);
            startActivity(intent);
        });
        imgFeedback.setOnClickListener(view -> {
            intent = new Intent(this, FeedbackActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            bundle.putString("username", username);
//            bundle.putString("password", password);
//            intent.putExtras(bundle);
            startActivity(intent);
        });
        imgRating.setOnClickListener(view -> {
            intent = new Intent(this, RatingActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            bundle.putString("username", username);
//            bundle.putString("password", password);
//            intent.putExtras(bundle);
            startActivity(intent);
        });
        img_pinCode.setOnClickListener(view -> {
            intent = new Intent(this, PinCodeActivity.class);
//            bundle.putString("username", username);
//            bundle.putString("password", password);
//            intent.putExtras(bundle);
            startActivity(intent);
        });
    }
}