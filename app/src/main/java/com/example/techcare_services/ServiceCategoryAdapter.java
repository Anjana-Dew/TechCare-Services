package com.example.techcare_services;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ServiceCategoryAdapter extends RecyclerView.Adapter<ServiceCategoryAdapter.CategoryViewHolder> {
    private Context context;
    private List<Device> deviceList;
    private DB_Operations db;
    private SparseArray<ServiceAdapter> serviceAdapterMap = new SparseArray<>();
    private RecyclerView recyclerView;



    public ServiceCategoryAdapter(Context context, List<Device> deviceList, DB_Operations db) {
        this.context = context;
        this.deviceList = deviceList;
        this.db = db;
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCategory;
        TextView tvCategoryName;
        RecyclerView rvServices;

        public CategoryViewHolder(View itemView) {

            super(itemView);
            imgCategory = itemView.findViewById(R.id.imgCategory);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            rvServices = itemView.findViewById(R.id.rvServices);
        }
    }
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context) .inflate(R.layout.item_service_category, parent, false);
        return new CategoryViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        Device device = deviceList.get(position);
        holder.tvCategoryName.setText(device.getDeviceType());
        holder.imgCategory.setImageResource(device.getServiceIconResId());

        List<Service> services = db.getServicesByDeviceType(device.getDeviceType());

        holder.rvServices.setLayoutManager( new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false) );

        ServiceAdapter serviceAdapter = new ServiceAdapter(context, services);
        serviceAdapterMap.put(position, serviceAdapter);

        holder.rvServices.setAdapter(serviceAdapter);

    }
    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    public void highlightService(
            int categoryPosition,
            int serviceId
    ) {
        ServiceAdapter adapter = serviceAdapterMap.get(categoryPosition);
        if (adapter != null) {
            adapter.highlightService(serviceId);
        }
    }
    public void scrollToService(
            int categoryPosition,
            int servicePosition
    ) {
        RecyclerView.ViewHolder holder =
                recyclerView.findViewHolderForAdapterPosition(categoryPosition);

        if (holder instanceof CategoryViewHolder) {
            CategoryViewHolder vh = (CategoryViewHolder) holder;
            vh.rvServices.scrollToPosition(servicePosition);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }


}
