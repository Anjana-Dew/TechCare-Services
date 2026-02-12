package com.example.techcare_services;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TechnicianOptionAdapter extends RecyclerView.Adapter<TechnicianOptionAdapter.OptionViewHolder>{
    public interface OnOptionClickListener {
        void onClick(TechnicianOption option);
    }

    private List<TechnicianOption> options;
    private OnOptionClickListener listener;

    public TechnicianOptionAdapter(List<TechnicianOption> options,
                                   OnOptionClickListener listener) {
        this.options = options;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_technician_option, parent, false);
        return new OptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull OptionViewHolder holder, int position) {

        TechnicianOption option = options.get(position);
        holder.imgIcon.setImageResource(option.getIcon());
        holder.tvTitle.setText(option.getTitle());

        holder.itemView.setOnClickListener(v -> listener.onClick(option));
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    static class OptionViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView tvTitle;

        public OptionViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgOptionIcon);
            tvTitle = itemView.findViewById(R.id.tvOptionTitle);
        }
    }
}
