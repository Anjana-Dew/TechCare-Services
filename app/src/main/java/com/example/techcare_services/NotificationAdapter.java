package com.example.techcare_services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private Context context;
    private List<Notification> list;
    private DB_Operations db;

    public NotificationAdapter(Context context, List<Notification> list) {
        this.context = context;
        this.list = list;
        this.db = new DB_Operations(context);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessage, tvTime;
        MaterialCardView card;

        ViewHolder(View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvTime = itemView.findViewById(R.id.tvTime);
            card = itemView.findViewById(R.id.cardNotification);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder, int position) {

        Notification n = list.get(position);

        holder.tvMessage.setText(n.getMessage());
        holder.tvTime.setText(n.getCreatedAt());


        if (n.isRead()) {
            holder.card.setStrokeColor(
                    context.getResources().getColor(R.color.amber_500));
        } else {
            holder.card.setStrokeColor(
                    context.getResources().getColor(R.color.teal_500));
        }

        holder.itemView.setOnClickListener(v -> {
            if (!n.isRead()) {
                db.markNotificationAsRead(n.getNotificationId());
                n.setRead(true);
                notifyItemChanged(position);
            }
        });


        holder.itemView.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.setScaleX(0.97f);
                    v.setScaleY(0.97f);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    v.setScaleX(1f);
                    v.setScaleY(1f);
                    break;
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
