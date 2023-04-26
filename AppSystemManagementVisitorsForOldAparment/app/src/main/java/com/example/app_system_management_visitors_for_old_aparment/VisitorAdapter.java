package com.example.app_system_management_visitors_for_old_aparment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VisitorAdapter extends RecyclerView.Adapter<VisitorAdapter.MyViewHolder> {
    Context c;
    ArrayList<Visitor> visitors;

    public VisitorAdapter(Context c, ArrayList<Visitor> visitors) {
        this.c = c;
        this.visitors = visitors;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setVisitors(ArrayList<Visitor> visitors) {
        this.visitors = visitors;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    /*Define Item layouts and initialize Holder.*/
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.visitor_item, parent, false);
        return new MyViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    /*Set the properties of the View and the data.*/
    public void onBindViewHolder(@NonNull VisitorAdapter.MyViewHolder holder, int position) {
        Visitor v = visitors.get(position);
        holder.id_card.setText(v.getId_card());
        holder.name.setText(v.getName());
        holder.room_id.setText(v.getRoom_id());
        holder.visit_time.setText(v.getVisit_time());
        holder.date.setText(v.getDateValue());
    }


    @Override
    /*Đếm số Item trong List Data*/
    public int getItemCount() {
        return visitors.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id_card, name, room_id, visit_time,date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id_card = itemView.findViewById(R.id.idCard);
            name = itemView.findViewById(R.id.visitorName);
            room_id = itemView.findViewById(R.id.roomID);
            visit_time = itemView.findViewById(R.id.visitTime);
            date = itemView.findViewById(R.id.date);
        }
    }
}

