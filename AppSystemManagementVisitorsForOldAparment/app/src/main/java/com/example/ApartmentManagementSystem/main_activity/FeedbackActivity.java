package com.example.ApartmentManagementSystem.main_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ApartmentManagementSystem.R;
import com.example.ApartmentManagementSystem.model.Feedback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FeedbackActivity extends AppCompatActivity {
    EditText editName, editFeedback;
    Button btnSend;
    DatabaseReference myRef;
    String name, feedback;
    Feedback f;
    Intent intent;
    Bundle bundle;
    String username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        editName = findViewById(R.id.edit_visitor_name);
        editFeedback = findViewById(R.id.edit_feedback);
        btnSend = findViewById(R.id.btn_send);
        intent = getIntent();
        bundle = intent.getExtras();
        username = bundle.getString("username");
        password = bundle.getString("password");
        Log.d("username",username);
        Log.d("password",password);
        myRef = FirebaseDatabase.getInstance().getReference().child("feedbacks");
        btnSend.setOnClickListener(view -> {
            editName.setText(editName.getText().toString());
            editFeedback.setText(editFeedback.getText().toString());
            name = editName.getText().toString();
            feedback = editFeedback.getText().toString();
            if (name.isEmpty() || feedback.isEmpty()) showErrorEmptyToast();
            else addData(name, feedback);
        });
    }

    public void addData(String name, String feedback) {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                f = new Feedback();
                f.setName(name);
                f.setFeedback(feedback);
                myRef.child(name).setValue(f);
                showSuccessfulToast();
                Intent intent = new Intent(FeedbackActivity.this, MainActivity.class);
                bundle.putString("username", username);
                bundle.putString("password", password);
                intent.putExtras(bundle);
                startActivity(intent);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void showSuccessfulToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_success,
                findViewById(R.id.toast_success));
        TextView text = layout.findViewById(R.id.toast_text_success);
        text.setText("Send successfully");
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    @SuppressLint("SetTextI18n")
    public void showErrorEmptyToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_error, findViewById(R.id.toast_error));
        TextView text = layout.findViewById(R.id.toast_text_error);
        text.setText("You must fill all data");
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}