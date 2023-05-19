package com.example.ApartmentManagementSystem.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ApartmentManagementSystem.R;
import com.example.ApartmentManagementSystem.model.Feedback;

import java.util.ArrayList;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder> {
    private final int VIEW_ITEM_BASE = 1;
    private final int VIEW_PROG = 0;
    Context c;
    ArrayList<Feedback> feedbacks;

    public FeedbackAdapter(Context c, ArrayList<Feedback> feedbacks) {
        this.c = c;
        this.feedbacks = feedbacks;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setFeedbacks(ArrayList<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==VIEW_ITEM_BASE){
            View v = LayoutInflater.from(c).inflate(R.layout.feedback_item,parent,false);
            return new MyViewHolder(v);
        }
        else {
            View v = LayoutInflater.from(c).inflate(R.layout.progress_item,parent,false);
            return new MyViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Feedback f=feedbacks.get(position);
        holder.name.setText(f.getName());
        holder.feedback.setText(f.getFeedback());
    }

    @Override
    public int getItemCount() {
        return feedbacks.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (feedbacks.get(position) != null) ? VIEW_ITEM_BASE : VIEW_PROG;
    }

    public static class MyViewHolder  extends RecyclerView.ViewHolder{
        TextView name,feedback;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.visitor_name);
            feedback=itemView.findViewById(R.id.feedbacks);
        }
    }
}
