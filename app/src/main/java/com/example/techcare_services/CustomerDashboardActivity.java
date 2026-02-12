package com.example.techcare_services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techcare_services.databinding.ActivityCustomerDashboardBinding;

import java.util.ArrayList;
import java.util.List;

public class CustomerDashboardActivity extends BaseActivity {
    RecyclerView rvServices;
    List<ServiceCard> serviceCardList;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityCustomerDashboardBinding binding;
    SessionManager sm, session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createNotificationChannel();

        binding = ActivityCustomerDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarCustomerDashboard.toolbar);
        binding.appBarCustomerDashboard.toolbar.setNavigationIcon(R.drawable.ic_menu_navy);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setupDrawer(binding.appBarCustomerDashboard.toolbar);

        //Service Section
        rvServices = findViewById(R.id.rvServices);
        rvServices.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        serviceCardList = new ArrayList<>();
        serviceCardList.add(new ServiceCard("Laptop Repair", "Fix laptop issues", R.drawable.ic_laptop_repair));
        serviceCardList.add(new ServiceCard("Phone Repair", "Fix phone issues", R.drawable.ic_phone_repair));

        ServiceCardAdapter adapter = new ServiceCardAdapter(this, serviceCardList);
        rvServices.setAdapter(adapter);

        //FAQ Section
        RecyclerView rvFaqs = findViewById(R.id.rvFaqs);
        rvFaqs.setLayoutManager(new LinearLayoutManager(this));

        List<FAQ> faqList = new ArrayList<>();

        faqList.add(new FAQ("How long does a repair take?", "Most repairs are completed within 24–48 hours."));
        faqList.add(new FAQ("Do you offer pickup service?", "Yes, we offer doorstep pickup and delivery."));
        faqList.add(new FAQ("Is there a warranty?", "Yes, we provide a limited service warranty."));

        FaqAdapter faqAdapter = new FaqAdapter(faqList);
        rvFaqs.setAdapter(faqAdapter);
        TextView tvSeeAllFaqs = findViewById(R.id.tvSeeAllFaqs);

        tvSeeAllFaqs.setOnClickListener(v -> {
            startActivity(new Intent(this, FaqActivity.class));
        });

        // Reviews Section
        RecyclerView rvReviews = findViewById(R.id.rvReviews);
        rvReviews.setLayoutManager(new LinearLayoutManager(this));

        List<Review> reviewList = new ArrayList<>();

        reviewList.add(new Review("Amanda", "Fast and reliable service!", 4.5f));
        reviewList.add(new Review("Kevin", "Very professional technicians.", 4.7f));
        reviewList.add(new Review("Sarah", "Pickup service was super convenient.", 3.9f));

        ReviewAdapter reviewAdapter = new ReviewAdapter(reviewList);
        rvReviews.setAdapter(reviewAdapter);
        TextView tvSeeAllReviews = findViewById(R.id.tvSeeAllReviews);

        tvSeeAllReviews.setOnClickListener(v -> {
            startActivity(new Intent(this, ReviewActivity.class));
        });


        sm = new SessionManager(this);
        String fullName = sm.getFullName();

        TextView tvWelcome = findViewById(R.id.tvWelcome);
        tvWelcome.setText("Welcome " + fullName + "!!");


        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        AnimationSet set = new AnimationSet(true);

        set.addAnimation(fadeIn);
        set.addAnimation(slideUp);
        tvWelcome.startAnimation(set);

        session = new SessionManager(this);
        int customerId = session.getCustomerId();

        DB_Operations db = new DB_Operations(this);
        int unreadCount = db.getUnreadNotificationCount(customerId);

        setupNotificationBadge(unreadCount);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                        this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        1001
                );
            }
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            Intent intent = new Intent(this, ServicesActivity.class);
            intent.putExtra("OPEN_SEARCH", true);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.customer_dashboard, menu);
        return true;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "repair_status_channel";
            String channelName = "Repair Status Updates";
            String channelDesc = "Notifications for repair status changes";

            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription(channelDesc);
            channel.enableVibration(true);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }


}