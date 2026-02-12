package com.example.techcare_services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RepairRequestAdapter extends RecyclerView.Adapter<RepairRequestAdapter.ViewHolder> {
    private List<RepairRequestItem> list;
    private Context context;
    private DB_Operations db;
    private int technicianId;

    public RepairRequestAdapter(
            Context context,
            List<RepairRequestItem> list,
            int technicianId
    ) {
        this.context = context;
        this.list = list;
        this.technicianId = technicianId;
        this.db = new DB_Operations(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_repair_request, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        RepairRequestItem item = list.get(position);

        h.tvCustomerName.setText(item.getCustomerName());
        h.tvServiceInfo.setText(item.getServiceInfo());
        h.tvRequestedDate.setText("Requested on: " + item.getRequestedDate());
        h.tvServiceMethod.setText("Service Method: " + item.getServiceMethod());


        h.btnAccept.setEnabled(true);
        h.btnReject.setEnabled(true);

        h.btnAccept.setOnClickListener(v -> {

            h.btnAccept.setEnabled(false);
            h.btnReject.setEnabled(false);

            db.acceptRepairRequest(item.getRequestId(), technicianId);
            Toast.makeText(context, "Request accepted", Toast.LENGTH_SHORT).show();
            h.btnAccept.setText("Accepted");

            int requestId = item.getRequestId();
            int customerId = db.getCustomerIdByRequestId(requestId);
            db.insertNotification(customerId, requestId, "Your repair request has been accepeted.");
        });

        h.btnReject.setOnClickListener(v -> {

            h.btnAccept.setEnabled(false);
            h.btnReject.setEnabled(false);

            db.rejectRepairRequest(item.getRequestId());
            Toast.makeText(context, "Request rejected", Toast.LENGTH_SHORT).show();

            h.btnReject.setText("Rejected");
            int requestId = item.getRequestId();
            int customerId = db.getCustomerIdByRequestId(requestId);
            db.insertNotification(customerId, requestId, "Your repair request has been rejected.");
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCustomerName, tvServiceInfo, tvRequestedDate, tvServiceMethod;
        Button btnAccept, btnReject;

        ViewHolder(View itemView) {
            super(itemView);

            tvCustomerName = itemView.findViewById(R.id.tvCustomerName);
            tvServiceInfo = itemView.findViewById(R.id.tvServiceInfo);
            tvRequestedDate = itemView.findViewById(R.id.tvRequestedDate);
            tvServiceMethod = itemView.findViewById(R.id.tvServiceMethod);
            btnAccept = itemView.findViewById(R.id.btnAccept);
            btnReject = itemView.findViewById(R.id.btnReject);
        }
    }
}
