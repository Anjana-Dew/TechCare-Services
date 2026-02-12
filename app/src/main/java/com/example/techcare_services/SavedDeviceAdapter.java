package com.example.techcare_services;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SavedDeviceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SavedDevice> deviceList;
    private OnRemoveClickListener removeListener;
    private static final int TYPE_DEVICE = 0;
    private static final int TYPE_ADD = 1;

    public SavedDeviceAdapter(List<SavedDevice> deviceList, OnRemoveClickListener removeListener) {
        this.deviceList = deviceList;
        this.removeListener = removeListener;
    }

    static class DeviceViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDevice, btnRemove;
        TextView tvDeviceName, tvBrand, tvStatus;
        private OnRemoveClickListener removeListener;

        public DeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDevice = itemView.findViewById(R.id.imgDevice);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            tvDeviceName = itemView.findViewById(R.id.tvDeviceName);
            tvBrand = itemView.findViewById(R.id.tvBrand);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }

    static class AddDeviceViewHolder extends RecyclerView.ViewHolder {
        public AddDeviceViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == deviceList.size()) {
            return TYPE_ADD;
        }
        return TYPE_DEVICE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == TYPE_ADD) {
            View view = inflater.inflate(R.layout.item_add_device, parent, false);
            return new AddDeviceViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_saved_device, parent, false);
            return new DeviceViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof AddDeviceViewHolder) {
            holder.itemView.setOnClickListener(v -> {
                Context context = v.getContext();
                context.startActivity(new Intent(context, DevicesActivity.class));
            });
            return;
        }

        // device cards
        SavedDevice device = deviceList.get(position);
        DeviceViewHolder vh = (DeviceViewHolder) holder;

        vh.tvDeviceName.setText(device.getDeviceName());
        vh.tvBrand.setText(device.getBrand());

        if (device.isActive()) {
            vh.tvStatus.setText("Active");
            vh.tvStatus.setTextColor(0xFF22C55E);
        } else {
            vh.tvStatus.setText("Inactive");
            vh.tvStatus.setTextColor(0xFFEF4444);
        }

        switch (device.getDeviceType()) {
            case "TV":
                vh.imgDevice.setImageResource(R.drawable.ic_tv);
                break;
            case "Fridge":
                vh.imgDevice.setImageResource(R.drawable.ic_fridge);
                break;
            case "Washing Machine":
                vh.imgDevice.setImageResource(R.drawable.ic_washing_machine);
                break;
            case "Phone":
                vh.imgDevice.setImageResource(R.drawable.ic_phone);
                break;
            case "Computer":
                vh.imgDevice.setImageResource(R.drawable.ic_computer);
                break;
            case "Laptop":
                vh.imgDevice.setImageResource(R.drawable.ic_laptop);
                break;
            case "AC":
            default:
                vh.imgDevice.setImageResource(R.drawable.ic_ac);
                break;
        }
        vh.btnRemove.setOnClickListener(v -> {
            if (removeListener != null) {
                removeListener.onRemoveClick(device);
            }
        });
    }

    @Override
    public int getItemCount() {
        return deviceList.size() + 1;
    }

    public interface OnRemoveClickListener {
        void onRemoveClick(SavedDevice device);
    }

}
