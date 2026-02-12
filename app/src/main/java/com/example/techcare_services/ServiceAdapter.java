package com.example.techcare_services;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>{
    private Context context;
    private List<Service> serviceList;
    private int highlightedServiceId = -1;



    public ServiceAdapter(Context context, List<Service> serviceList) {
        this.context = context;
        this.serviceList = serviceList;
    }

    static class ServiceViewHolder extends RecyclerView.ViewHolder {
        TextView tvServiceName, tvServicePrice, tvServiceDesc, tvServiceDuration;

        public ServiceViewHolder(View itemView) {
            super(itemView);
            tvServiceName = itemView.findViewById(R.id.tvServiceName);
            tvServicePrice = itemView.findViewById(R.id.tvServicePrice);
            tvServiceDesc = itemView.findViewById(R.id.tvServiceDesc);
            tvServiceDuration = itemView.findViewById(R.id.tvServiceDuration);
        }
    }
    public void highlightService(int serviceId) {
        highlightedServiceId = serviceId;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_service, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {

        Service service = serviceList.get(position);

        holder.tvServiceName.setText(service.getServiceName());
        holder.tvServicePrice.setText("Rs. " + service.getPrice());
        holder.tvServiceDesc.setText(service.getDescription());
        holder.tvServiceDuration.setText("⏱ ~" + service.getEstimatedDuration() + " hrs");


        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RepairRequestActivity.class);
            intent.putExtra("SERVICE_ID", service.getServiceId());
            context.startActivity(intent);
        });
        Log.d("SERVICE_DESC", "Desc: " + service.getDescription());


        holder.itemView.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.setScaleX(0.96f);
                    v.setScaleY(0.96f);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    v.setScaleX(1f);
                    v.setScaleY(1f);
                    break;
            }
            return false;
        });
        if (service.getServiceId() == highlightedServiceId) {
            holder.itemView.setBackgroundResource(R.drawable.border_amber);
        } else {
            holder.itemView.setBackgroundResource(R.drawable.border_teal);
        }
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }
}
