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

public class ApartmentManagementAdapter  extends RecyclerView.Adapter<ApartmentManagementAdapter.MyViewHolder>{
    Context c;
    ArrayList<Apartment> apartments;

    public ApartmentManagementAdapter(Context c, ArrayList<Apartment> apartments) {
        this.c = c;
        this.apartments = apartments;
    }

    @NonNull
    @Override
    public ApartmentManagementAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.apartment_management_item,parent,false);
        return new MyViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ApartmentManagementAdapter.MyViewHolder holder, int position) {
        Apartment a=apartments.get(position);
        holder.owner_phone.setText(a.getOwner_phone());
        holder.owner_name.setText(a.getOwner_name());
        holder.room_id.setText(a.getRoom_id());
    }

    @Override
    public int getItemCount() {
        return apartments.size();
    }

    public static class MyViewHolder  extends RecyclerView.ViewHolder{
        TextView owner_phone,owner_name,room_id;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            owner_phone=itemView.findViewById(R.id.owner_phone);
            owner_name=itemView.findViewById(R.id.owner_name);
            room_id=itemView.findViewById(R.id.room_id);

        }
    }
}
