package com.example.techcare_services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.VH>{
    private Context context;
    private List<Booking> list;

    public BookingHistoryAdapter(Context context, List<Booking> list) {
        this.context = context;
        this.list = list;
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvMethod, tvDate,tvServiceName;

        VH(View v) {
            super(v);
            tvServiceName = v.findViewById(R.id.tvServiceName);
            tvMethod = v.findViewById(R.id.tvMethod);
            tvDate = v.findViewById(R.id.tvDate);
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_booking_history, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        Booking b = list.get(pos);
        h.tvServiceName.setText(b.getServiceName());
        h.tvMethod.setText(b.getMethod());
        h.tvDate.setText(b.getDateTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
