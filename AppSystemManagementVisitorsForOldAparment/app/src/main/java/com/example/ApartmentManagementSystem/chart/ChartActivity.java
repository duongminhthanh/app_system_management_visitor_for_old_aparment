package com.example.ApartmentManagementSystem.chart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.ApartmentManagementSystem.R;

public class ChartActivity extends AppCompatActivity {
    ImageView imgBar,imgLine,imgPie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart2);
        //toolbar
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        imgBar=findViewById(R.id.image_bar);
        imgPie=findViewById(R.id.image_pie);
        imgLine=findViewById(R.id.image_line);

        imgBar.setOnClickListener(view -> {
            Intent i = new Intent(this, BarChartActivity.class);
            startActivity(i);
        });
        imgPie.setOnClickListener(view -> {
            Intent i = new Intent(this, PieChartActivity.class);
            startActivity(i);
        });
        imgLine.setOnClickListener(view -> {
            Intent i = new Intent(this, LineChartActivity.class);
            startActivity(i);
        });
    }
}