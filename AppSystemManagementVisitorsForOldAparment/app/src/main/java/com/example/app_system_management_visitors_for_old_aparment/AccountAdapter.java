package com.example.app_system_management_visitors_for_old_aparment;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    @SuppressLint("NotifyDataSetChanged")
    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
        notifyDataSetChanged();
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
        holder.acc_id.setText(a.getAcc_id());
        holder.username.setText(a.getUsername());
        holder.password.setText(a.getPassword());
        holder.pin_code.setText(a.getPin_code());
        holder.btnUpdate.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), EditAccountActivity.class);
            intent.putExtra("acc_id", a.getAcc_id());
            intent.putExtra("username", a.getUsername());
            intent.putExtra("password", a.getPassword());
            intent.putExtra("pin_code", a.getPin_code());
            startActivity(c, intent, Bundle.EMPTY);
        });
        holder.btnDelete.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), DeleteAccountActivity.class);
            intent.putExtra("acc_id", a.getAcc_id());
            intent.putExtra("username", a.getUsername());
            intent.putExtra("password", a.getPassword());
            intent.putExtra("pin_code", a.getPin_code());
            startActivity(c, intent, Bundle.EMPTY);
        });
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView acc_id, username, password, pin_code;
        Button btnDelete, btnUpdate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            acc_id = itemView.findViewById(R.id.acc_id);
            username = itemView.findViewById(R.id.username);
            password = itemView.findViewById(R.id.password);
            pin_code = itemView.findViewById(R.id.pin_code);
            btnDelete = itemView.findViewById(R.id.button_delete);
            btnUpdate = itemView.findViewById(R.id.button_update);
        }
    }

}
