package com.example.app_system_management_visitors_for_old_aparment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.MyViewHolder> {
    Context c;
    ArrayList<Account> accounts;

    public AccountAdapter(Context c, ArrayList<Account> accounts) {
        this.c = c;
        this.accounts = accounts;
    }

    @NonNull
    @Override
    public AccountAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.account_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountAdapter.MyViewHolder holder, int position) {
        Account a = accounts.get(position);
        holder.username.setText(a.getUsername());
        holder.password.setText(a.getPassword());
        holder.pin_code.setText(a.getPin_code());

    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView username, password, pin_code;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            password = itemView.findViewById(R.id.password);
            pin_code = itemView.findViewById(R.id.pin_code);

        }
    }

}
