package com.example.ApartmentManagementSystem.chart;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ApartmentManagementSystem.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class PieChartActivity extends AppCompatActivity {
    DatabaseReference myRef;
    String date;
    ArrayList<String> labels,xAxisValue;
    ArrayList<PieEntry> pieEntries;
    PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        myRef = FirebaseDatabase.getInstance().getReference().child("list_visitor");
        labels = new ArrayList<>();
        xAxisValue=new ArrayList<>();
        pieEntries = new ArrayList<>();
        getData();

//        String []s={"2023-04-25","2023-04-26","2023-04-27","2023-04-28","2023-04-29"};
//        String []s2={"2023-04-25","2023-04-26","2023-04-26","2023-04-27","2023-04-28","2023-04-29"};
//        ArrayList<String> str=new ArrayList<>();
//        ArrayList<String> str2=new ArrayList<>();
//        str.addAll(Arrays.asList(s));
//        str2.addAll(Arrays.asList(s2));
//        for (int i = 0; i < str.size(); i++) {
//            int c=Collections.frequency(str2,str.get(i));
//            pieEntries.add(new PieEntry(c,str.get(i)));
//        }

    }

    public void getData() {
        myRef.orderByChild("date").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    date = Objects.requireNonNull(dataSnapshot.child("date")
                            .getValue()).toString();
                    StringBuilder builder= new StringBuilder(date);
                    builder.delete(0,5);
                    labels.add(String.valueOf(builder));
                    xAxisValue.add(String.valueOf(builder));
                }
                Set<String> set = new LinkedHashSet<>();
                set.addAll(xAxisValue);
                xAxisValue.clear();
                xAxisValue.addAll(set);
                Log.d("label",labels.toString());
                Log.d("xAxisvalue",xAxisValue.toString());

                for (int i = 0; i < xAxisValue.size(); i++) {
                    int c= Collections.frequency(labels,xAxisValue.get(i));
                    pieEntries.add(new PieEntry(c,xAxisValue.get(i)));
                }
                PieDataSet dataSet = new PieDataSet(pieEntries, "Pie Chart Report");
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                dataSet.setValueTextColor(Color.BLACK);
                dataSet.setValueTextSize(30f);
                PieData data= new PieData(dataSet);
                pieChart=findViewById(R.id.piechart);
                pieChart.setData(data);
                pieChart.getDescription().setEnabled(true);
                pieChart.setCenterText("Number of visitors by date");
                pieChart.animate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}