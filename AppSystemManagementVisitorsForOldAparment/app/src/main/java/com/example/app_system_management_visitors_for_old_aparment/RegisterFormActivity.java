package com.example.app_system_management_visitors_for_old_aparment;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class RegisterFormActivity extends AppCompatActivity {
    EditText edName,edIdCard,edTime;
    Spinner spinner;
    ArrayList <Apartment> list;
    ArrayList <String> apartmentId;
    ArrayAdapter<String> roomIdAdapter;
    DatabaseReference apartmentRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);
        edName=findViewById(R.id.edit_name_visitor);
        edIdCard=findViewById(R.id.edit_id_card);
        edTime=findViewById(R.id.edit_time);
        spinner=findViewById(R.id.spinner_apartment_id);
        list=new ArrayList<>();
        apartmentId=new ArrayList<>();
        apartmentRef= FirebaseDatabase.getInstance().getReference().
                child("list_apartment");
        edName.setText(edName.getText().toString());
        edTime.setText(edTime.getText().toString());
        edIdCard.setText(edIdCard.getText().toString());
        apartmentRef.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Apartment a = dataSnapshot.getValue(Apartment.class);
                            String roomID= Objects.requireNonNull(dataSnapshot.child("room_id")
                                    .getValue()).toString();
                            Log.d("apartment", String.valueOf(a));
                            Log.d("room_id",roomID);
                            list.add(a);

                            apartmentId.add(roomID);
                            roomIdAdapter=new ArrayAdapter<>
                                    (RegisterFormActivity.this,R.layout.style_spinner,apartmentId);
                            spinner.setAdapter(roomIdAdapter);
                            roomIdAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}