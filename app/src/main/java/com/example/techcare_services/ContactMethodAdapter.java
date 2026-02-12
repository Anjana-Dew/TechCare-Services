package com.example.techcare_services;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactMethodAdapter extends RecyclerView.Adapter<ContactMethodAdapter.ViewHolder> {

    private List<ContactMethod> list;

    public ContactMethodAdapter(List<ContactMethod> list) { this.list = list; }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_method, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ContactMethod item = list.get(position);
        holder.icon.setImageResource(item.iconRes);
        holder.title.setText(item.title);
        holder.detail.setText(item.detail);
    }

    @Override
    public int getItemCount() { return list.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title, detail;

        public ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.imgIcon);
            title = itemView.findViewById(R.id.tvTitle);
            detail = itemView.findViewById(R.id.tvDetail);
        }
    }
}


