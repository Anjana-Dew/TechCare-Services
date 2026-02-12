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

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.ViewHolder>  {
    private List<Promotion> promotionList;
    private Context context;

    public PromotionAdapter(Context context,List<Promotion> promotionList) {
        this.context = context;
        this.promotionList = promotionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_promotion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Promotion promotion = promotionList.get(position);

        holder.imgPromotion.setImageResource(promotion.getImageRes());
        holder.tvPromotionDesc.setText(promotion.getDescription());

        holder.itemView.setOnClickListener(v -> {
            NotificationHelper helper = new NotificationHelper();
            helper.showRepairStatusNotification(
                    context,
                    "TechCare Promotion 🎉",
                    promotion.getDescription()
            );
        });
    }

    @Override
    public int getItemCount() {

        return promotionList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPromotion;
        TextView tvPromotionDesc;

        ViewHolder(View itemView) {
            super(itemView);
            imgPromotion = itemView.findViewById(R.id.imgPromotion);
            tvPromotionDesc = itemView.findViewById(R.id.tvPromotionDesc);
        }
    }
}
