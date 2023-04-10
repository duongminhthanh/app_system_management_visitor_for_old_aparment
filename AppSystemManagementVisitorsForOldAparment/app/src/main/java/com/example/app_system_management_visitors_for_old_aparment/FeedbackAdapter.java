package com.example.app_system_management_visitors_for_old_aparment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder> {
    Context c;
    ArrayList<Feedback> feedbacks;

    public FeedbackAdapter(Context c, ArrayList<Feedback> feedbacks) {
        this.c = c;
        this.feedbacks = feedbacks;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.feedback_item,parent,false);
        return new MyViewHolder(v);
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

    public static class MyViewHolder  extends RecyclerView.ViewHolder{
        TextView name,feedback;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.visitor_name);
            feedback=itemView.findViewById(R.id.feedbacks);
        }
    }
}
