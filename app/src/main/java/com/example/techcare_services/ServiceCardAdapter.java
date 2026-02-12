package com.example.techcare_services;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ServiceCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_SERVICE = 0;
    private static final int TYPE_SEE_ALL = 1;

    private final List<ServiceCard> serviceCardList;
    private final Context context;

    public ServiceCardAdapter(Context context, List<ServiceCard> serviceCardList) {
        this.context = context;
        this.serviceCardList = serviceCardList;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == serviceCardList.size())
                ? TYPE_SEE_ALL
                : TYPE_SERVICE;
    }

    @Override
    public int getItemCount() {

        return serviceCardList.size() + 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == TYPE_SEE_ALL) {
            View view = inflater.inflate(R.layout.item_service_see_all, parent, false);
            return new SeeAllViewHolder(view);
        }

        View view = inflater.inflate(R.layout.item_service_card, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (position >= serviceCardList.size()) {
            holder.itemView.setOnClickListener(v ->
                    context.startActivity(new Intent(context, ServicesActivity.class))
            );
            return;
        }

        ServiceCard service = serviceCardList.get(position);
        ServiceViewHolder vh = (ServiceViewHolder) holder;

        vh.tvName.setText(service.getName());
        vh.tvDesc.setText(service.getDescription());
        vh.img.setImageResource(service.getImageResId());

        ServiceCard serviceCard = serviceCardList.get(position);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ServicesActivity.class);

            if (serviceCard.getName().contains("Laptop")) {
                intent.putExtra("DEVICE_TYPE", "Laptop");
            } else if (serviceCard.getName().contains("Phone")) {
                intent.putExtra("DEVICE_TYPE", "Phone");
            }

            context.startActivity(intent);
        });

    }


    static class ServiceViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDesc;
        ImageView img;

        ServiceViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvServiceName);
            tvDesc = itemView.findViewById(R.id.tvServiceDesc);
            img = itemView.findViewById(R.id.imgService);
        }
    }

    static class SeeAllViewHolder extends RecyclerView.ViewHolder {
        SeeAllViewHolder(View itemView) {
            super(itemView);
        }
    }
}



