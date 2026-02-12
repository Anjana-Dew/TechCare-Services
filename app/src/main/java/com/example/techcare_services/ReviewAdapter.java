package com.example.techcare_services;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>{
    private List<Review> reviewList;

    public ReviewAdapter(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviewList.get(position);

        holder.tvReviewerName.setText(review.getReviewerName());
        holder.tvReviewText.setText(review.getReviewText());
        holder.ratingBar.setRating(review.getRating());

        holder.cardView.setStrokeColor(Color.parseColor("#14b8a6"));

        holder.cardView.setOnClickListener(v -> {
            holder.cardView.setStrokeColor(Color.parseColor("#f59e0b"));

            holder.cardView.postDelayed(() -> {
                holder.cardView.setStrokeColor(Color.parseColor("#14b8a6"));
            }, 1700);
        });
    }

    @Override
    public int getItemCount() {

        return reviewList.size();
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView tvReviewerName, tvReviewText;
        RatingBar ratingBar;
        MaterialCardView cardView;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReviewerName = itemView.findViewById(R.id.tvReviewerName);
            tvReviewText = itemView.findViewById(R.id.tvReviewText);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            cardView = itemView.findViewById(R.id.cardReview);
        }
    }
}
