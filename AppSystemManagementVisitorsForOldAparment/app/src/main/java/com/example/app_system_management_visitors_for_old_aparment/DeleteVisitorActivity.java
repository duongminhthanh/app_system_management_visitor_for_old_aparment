package com.example.app_system_management_visitors_for_old_aparment;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteVisitorActivity extends AppCompatActivity {
    EditText edName, edIdCard, edRoomId, edTime;
    Button btnYes,btnNo;
    DatabaseReference myRef;
    Intent intent;
    String name, idCard, roomId, time;
    Visitor v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_visitor);
        edName = findViewById(R.id.edit_new_name);
        edIdCard = findViewById(R.id.edit_new_id_card);
        edRoomId = findViewById(R.id.edit_room_id);
        edTime = findViewById(R.id.edit_new_time);
        btnYes=findViewById(R.id.button_yes);
        btnNo=findViewById(R.id.button_no);
        myRef = FirebaseDatabase.getInstance().getReference().child("list_visitor");
        intent=getIntent();
        name=intent.getStringExtra("name");
        idCard=intent.getStringExtra("id_card");
        roomId=intent.getStringExtra("room_id");
        time=intent.getStringExtra("visit_time");
        edName.setText(name);
        edIdCard.setText(idCard);
        edRoomId.setText(roomId);
        edTime.setText(time);
        btnNo.setOnClickListener(view -> showErrorDeleteToast());
        btnYes.setOnClickListener(view -> {
            v=new Visitor();
            myRef.child(name).removeValue();
            showDeleteSuccessfulToast();
            Intent intent=new Intent(DeleteVisitorActivity.this,
                    ManageListVisitorActivity.class);
            startActivity(intent);
        });
    }

    @SuppressLint("SetTextI18n")
    public void showDeleteSuccessfulToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_success,
                findViewById(R.id.toast_success));
        TextView text = layout.findViewById(R.id.toast_text_success);
        text.setText("Delete visitor successfully");
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
    @SuppressLint("SetTextI18n")
    public void showErrorDeleteToast() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_error, findViewById(R.id.toast_error));
        TextView text = layout.findViewById(R.id.toast_text_error);
        text.setText("Delete visitor failed");
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}