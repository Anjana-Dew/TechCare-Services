package com.example.techcare_services;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MaintenanceTipsAdapter extends RecyclerView.Adapter<MaintenanceTipsAdapter.TipViewHolder> {

    private final List<MaintenanceTips> tips;

    public MaintenanceTipsAdapter(List<MaintenanceTips> tips) {
        this.tips = tips;
    }

    static class TipViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title, description;

        public TipViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.imgTipIcon);
            title = itemView.findViewById(R.id.tvTipTitle);
            description = itemView.findViewById(R.id.tvTipDescription);
        }
    }

    @NonNull
    @Override
    public TipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_maintanence_tip, parent, false);
        return new TipViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TipViewHolder holder, int position) {
        MaintenanceTips tip = tips.get(position);
        holder.icon.setImageResource(tip.iconRes);
        holder.title.setText(tip.title);
        holder.description.setText(tip.description);
    }

    @Override
    public int getItemCount() {
        return tips.size();
    }

}
