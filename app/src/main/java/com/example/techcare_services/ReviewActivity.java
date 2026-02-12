package com.example.techcare_services;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {
    RecyclerView rvReviews;
    List<Review> reviewList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Toolbar toolbar = findViewById(R.id.toolbarReview);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(v -> {
            startActivity(new Intent(ReviewActivity.this, CustomerDashboardActivity.class));
            finish();
        });

        rvReviews = findViewById(R.id.rvReviewsAll);
        rvReviews.setLayoutManager(new LinearLayoutManager(this));

        // Hardcoded reviews
        reviewList = new ArrayList<>();
        reviewList.add(new Review("Amanda", "Fast and reliable service!",4.5f));
        reviewList.add(new Review("Kevin", "Very professional technicians.",4.7f));
        reviewList.add(new Review("Sarah", "Pickup service was super convenient.",3.9f));
        reviewList.add(new Review("John", "Excellent customer service.",4f));
        reviewList.add(new Review("Lisa", "Highly recommend TechCare!",5f));

        ReviewAdapter adapter = new ReviewAdapter(reviewList);
        rvReviews.setAdapter(adapter);

        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(slideUp);
        animationSet.addAnimation(fadeIn);

        rvReviews.startAnimation(animationSet);
    }
}