package com.example.techcare_services;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UpdateStatusAdapter extends RecyclerView.Adapter<UpdateStatusAdapter.ViewHolder> {
    private Context context;
    private List<AcceptedRepairItem> list;
    private DB_Operations db;

    public UpdateStatusAdapter(Context context, List<AcceptedRepairItem> list) {
        this.context = context;
        this.list = list;
        this.db = new DB_Operations(context);
    }
    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvRequestId, tvServiceInfo, tvCurrentStatus;
        Button btnReceived, btnUnderRepair, btnReady;

        ViewHolder(View v) {
            super(v);
            tvRequestId = v.findViewById(R.id.tvRequestId);
            tvServiceInfo = v.findViewById(R.id.tvServiceInfo);
            tvCurrentStatus = v.findViewById(R.id.tvCurrentStatus);
            btnReceived = v.findViewById(R.id.btnReceived);
            btnUnderRepair = v.findViewById(R.id.btnUnderRepair);
            btnReady = v.findViewById(R.id.btnReady);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_update_repair_status, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {

        AcceptedRepairItem item = list.get(position);

        h.tvRequestId.setText("Request #" + item.getRequestId());
        h.tvServiceInfo.setText(item.getServiceName());
        h.tvCurrentStatus.setText("Current Status: " + item.getStatus());

        setupButtons(h, item);
    }

    private void setupButtons(ViewHolder h, AcceptedRepairItem item) {

        resetButtons(h);

        switch (item.getStatus()) {
            case "ACCEPTED":
                enableNext(h.btnReceived);
                break;
            case "RECEIVED":
                activate(h.btnReceived);
                enableNext(h.btnUnderRepair);
                break;

            case "UNDER_REPAIR":
                activate(h.btnUnderRepair);
                enableNext(h.btnReady);
                break;

            case "READY":
                activate(h.btnReady);
                break;
        }
        h.btnReceived.setOnClickListener(v ->
                showConfirmDialog(item, "RECEIVED"));

        h.btnUnderRepair.setOnClickListener(v ->
                showConfirmDialog(item, "UNDER_REPAIR"));

        h.btnReady.setOnClickListener(v ->
                showConfirmDialog(item, "READY"));

    }
    private void showConfirmDialog(AcceptedRepairItem item, String newStatus) {
        new AlertDialog.Builder(context)
                .setTitle("Confirm Status Update")
                .setMessage("Change status to " + newStatus + "?")
                .setPositiveButton("Yes", (d, w) -> {
                    updateStatus(item, newStatus);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void updateStatus(AcceptedRepairItem item, String newStatus) {

        Log.d(
                "STATUS_FLOW",
                "Technician CONFIRMED status change → requestId="
                        + item.getRequestId()
                        + ", newStatus=" + newStatus
        );
        db.updateRepairStatus(item.getRequestId(), newStatus);
        item.setStatus(newStatus);

        String dbStatus = db.getRepairStatusById(item.getRequestId());

        Log.d(
                "STATUS_FLOW",
                "Verification READ from DB → requestId="
                        + item.getRequestId()
                        + ", statusInDB=" + dbStatus
        );
        int customerId = db.getCustomerIdByRequestId(item.getRequestId());

        String message;
        switch (newStatus) {
            case "RECEIVED":
                message = "Your device has been received by the technician.";
                break;
            case "UNDER_REPAIR":
                message = "Your device is now under repair.";
                break;
            case "READY":
                message = "Your device is ready for pickup.";
                break;
            default:
                message = "Repair status updated.";
        }

        db.insertNotification(customerId, item.getRequestId(), message);

        item.setStatus(newStatus);
        notifyItemChanged(list.indexOf(item));
        Toast.makeText(context, "Status updated & customer notified", Toast.LENGTH_SHORT).show();
    }



    private void resetButtons(ViewHolder h) {
        disable(h.btnReceived);
        disable(h.btnUnderRepair);
        disable(h.btnReady);
    }

    private void activate(Button b) {
        b.setEnabled(false);
        b.setBackgroundTintList(
                ColorStateList.valueOf(Color.parseColor("#16a34a")));
    }

    private void enableNext(Button b) {
        b.setEnabled(true);
        b.setBackgroundTintList(
                ColorStateList.valueOf(Color.parseColor("#2563eb")));
    }

    private void disable(Button b) {
        b.setEnabled(false);
        b.setBackgroundTintList(
                ColorStateList.valueOf(Color.parseColor("#334155")));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
