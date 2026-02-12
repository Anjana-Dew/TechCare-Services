package com.example.techcare_services;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.FaqViewHolder>{
    private List<FAQ> faqList;

    public FaqAdapter(List<FAQ> faqList) {
        this.faqList = faqList;
    }

    @NonNull
    @Override
    public FaqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_faq, parent, false);
        return new FaqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FaqViewHolder holder, int position) {
        FAQ faq = faqList.get(position);
        holder.tvQuestion.setText(faq.getQuestion());
        holder.tvAnswer.setText(faq.getAnswer());

        holder.cardView.setStrokeColor(Color.parseColor("#f59e0b"));

        holder.cardView.setOnClickListener(v -> {
            holder.cardView.setStrokeColor(Color.parseColor("#14b8a6"));

            holder.cardView.postDelayed(() -> {
                holder.cardView.setStrokeColor(Color.parseColor("#f59e0b"));
            }, 1700);
        });
    }

    @Override
    public int getItemCount() {
        return faqList.size();
    }

    static class FaqViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestion, tvAnswer;
        MaterialCardView cardView;

        public FaqViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            tvAnswer = itemView.findViewById(R.id.tvAnswer);
            cardView = (MaterialCardView) itemView; // root of item_faq.xml
        }
    }
}
