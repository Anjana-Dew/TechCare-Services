package com.example.techcare_services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {

    private List<Device> deviceList;
    private OnAddClickListener listener;

    public interface OnAddClickListener {
        void onAddClick(Device device);
    }

     public DeviceAdapter(List<Device> deviceList, OnAddClickListener listener) {
        this.deviceList = deviceList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_device, parent, false);
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        Device device = deviceList.get(position);

        holder.tvDeviceName.setText(device.getDeviceName());
        holder.tvBrand.setText(device.getBrand());
        holder.imgDevice.setImageResource(device.getImageResId());

        holder.btnAdd.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAddClick(device);
            }
        });
        holder.btnAdd.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAddClick(device);
                holder.btnAdd.setEnabled(false);
                holder.btnAdd.setAlpha(0.5f);
            }
        });
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    static class DeviceViewHolder extends RecyclerView.ViewHolder {

        ImageView imgDevice, btnAdd;
        TextView tvDeviceName, tvBrand;

        public DeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDevice = itemView.findViewById(R.id.imgDevice);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            tvDeviceName = itemView.findViewById(R.id.tvDeviceName);
            tvBrand = itemView.findViewById(R.id.tvBrand);
        }
    }
}

