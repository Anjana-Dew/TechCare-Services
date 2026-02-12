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

public class FaqActivity extends AppCompatActivity {
    RecyclerView rvFaqs;
    List<FAQ> faqList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        Toolbar toolbar = findViewById(R.id.toolbarFaq);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(v -> {
            startActivity(new Intent(FaqActivity.this, CustomerDashboardActivity.class));
            finish();
        });

        rvFaqs = findViewById(R.id.rvFaqsAll);
        rvFaqs.setLayoutManager(new LinearLayoutManager(this));

        faqList = new ArrayList<>();
        faqList.add(new FAQ("How long does a repair take?", "Most repairs are completed within 24–48 hours."));
        faqList.add(new FAQ("Do you offer pickup service?", "Yes, we offer doorstep pickup and delivery."));
        faqList.add(new FAQ("Is there a warranty?", "Yes, we provide a limited service warranty."));
        faqList.add(new FAQ("What payment methods do you accept?", "We accept cash, credit/debit cards, and UPI."));
        faqList.add(new FAQ("Can I track my repair?", "Yes, you can track your repair status in the app."));
        faqList.add(new FAQ("Do you repair all brands?", "We service all major brands of phones and laptops."));
        faqList.add(new FAQ("Can I cancel my service?", "Yes, you can cancel before the pickup or repair starts."));
        faqList.add(new FAQ("Are your technicians certified?", "All our technicians are certified and trained."));
        faqList.add(new FAQ("Do you offer discounts?", "Occasionally, we offer promotions. Check our app for details."));

        FaqAdapter faqAdapter = new FaqAdapter(faqList);
        rvFaqs.setAdapter(faqAdapter);


        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(slideUp);
        animationSet.addAnimation(fadeIn);

        rvFaqs.startAnimation(animationSet);
    }
}