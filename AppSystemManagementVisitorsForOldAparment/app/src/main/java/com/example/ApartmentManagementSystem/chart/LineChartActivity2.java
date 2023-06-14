package com.example.ApartmentManagementSystem.chart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.ApartmentManagementSystem.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class LineChartActivity2 extends AppCompatActivity {
    DatabaseReference myRef;
    String date;
    LineChart lineChart;

    ArrayList<Entry> entries;
    ArrayList<String> labels, xAxisValue;
    LineDataSet dataSet;
    LineData data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart2);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        myRef = FirebaseDatabase.getInstance().getReference().child("list_visitor");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        lineChart=findViewById(R.id.linechart);
        labels = new ArrayList<>();
        xAxisValue=new ArrayList<>();
        entries=new ArrayList<>();
        getData();
        lineChart.setPinchZoom(false);
        lineChart.setDrawGridBackground(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);
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
                for (int i = 0; i < xAxisValue.size(); i++) {
                    int c= Collections.frequency(labels,xAxisValue.get(i));
                    entries.add(new Entry(i,c));
                }
                dataSet = new LineDataSet(entries, "Number of visitors by date");
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                dataSet.setValueTextColor(Color.BLACK);
                dataSet.setValueTextSize(20f);
                data = new LineData(dataSet);
                XAxis xAxis = lineChart.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisValue));
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setTextSize(20f);
                xAxis.setGranularityEnabled(true);
                xAxis.setGranularity(1.0f);
                xAxis.setXOffset(1f);
                lineChart.setData(data);
                lineChart.getDescription().setEnabled(false);
                lineChart.animateX(2000);
                //range of entries display on screen
                lineChart.setVisibleXRangeMaximum(10);
                lineChart.moveViewToX(xAxisValue.size()-1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}