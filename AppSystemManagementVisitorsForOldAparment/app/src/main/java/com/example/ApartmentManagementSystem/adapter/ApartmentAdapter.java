package com.example.ApartmentManagementSystem.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ApartmentManagementSystem.R;
import com.example.ApartmentManagementSystem.model.Apartment;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ApartmentAdapter extends RecyclerView.Adapter<ApartmentAdapter.MyViewHolder> {
    private final int VIEW_ITEM_BASE = 1;
    private final int VIEW_PROG = 0;

    Context c;
    ArrayList<Apartment> apartments;

    public ApartmentAdapter(Context c, ArrayList<Apartment> apartments) {
        this.c = c;
        this.apartments = apartments;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setApartments(ArrayList<Apartment> apartments) {
        this.apartments = apartments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_ITEM_BASE){
            View v = LayoutInflater.from(c).inflate(R.layout.apartment_item, parent, false);
            return new MyViewHolder(v);
        }
        else {
            View v = LayoutInflater.from(c).inflate(R.layout.progress_item, parent, false);
            return new MyViewHolder(v);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ApartmentAdapter.MyViewHolder holder, int position) {
        Apartment a = apartments.get(position);
        holder.owner_phone.setText(a.getOwner_phone());
        holder.owner_name.setText(a.getOwner_name());
        holder.room_id.setText(a.getRoom_id());
        holder.callPhone.setOnClickListener(view -> {
            String phone = a.getOwner_phone();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phone));
            startActivity(c, intent, Bundle.EMPTY);
        });

        holder.zalo.setOnClickListener(view -> {
            String phone = a.getOwner_phone();
            StringBuilder builder=new StringBuilder(phone);
            builder.setCharAt(0,' ');
            builder.toString().trim();
            String s="https://zalo.me/84"+builder.toString().trim();
            Log.d("s",s);
            Uri uri=Uri.parse(s);
            Log.d("uri",uri.toString());
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(c, intent, Bundle.EMPTY);
        });
    }
    @Override
    public int getItemCount() {
        return apartments.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (apartments.get(position) != null) ? VIEW_ITEM_BASE : VIEW_PROG;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView owner_phone, owner_name, room_id;
        FloatingActionButton callPhone, zalo;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card);
            owner_phone = itemView.findViewById(R.id.owner_phone);
            owner_name = itemView.findViewById(R.id.owner_name);
            room_id = itemView.findViewById(R.id.room_id);
            callPhone = itemView.findViewById(R.id.img_phone);
            zalo = itemView.findViewById(R.id.img_zalo);
        }
    }
}
