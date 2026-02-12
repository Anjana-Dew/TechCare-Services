package com.example.techcare_services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryRepairAdapter extends RecyclerView.Adapter<HistoryRepairAdapter.ViewHolder>{
    private Context context;
    private List<HistoryRepairment> list;

    public HistoryRepairAdapter(Context context, List<HistoryRepairment> list) {
        this.context = context;
        this.list = list;
    }
    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvRequestId, tvCustomerName,
                tvServiceInfo, tvCompletedDate;

        ViewHolder(View v) {
            super(v);
            tvRequestId = v.findViewById(R.id.tvRequestId);
            tvCustomerName = v.findViewById(R.id.tvCustomerName);
            tvServiceInfo = v.findViewById(R.id.tvServiceInfo);
            tvCompletedDate = v.findViewById(R.id.tvCompletedDate);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_repair_history, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {

        HistoryRepairment item = list.get(position);

        h.tvRequestId.setText("Request #" + item.getRequestId());
        h.tvCustomerName.setText(item.getCustomerName());
        h.tvServiceInfo.setText(item.getServiceInfo());
        h.tvCompletedDate.setText(
                "Completed on: " + item.getCompletedDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
