package com.example.techcare_services;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends BaseActivity {
    private RecyclerView rvSavedDevices;
    private SavedDeviceAdapter savedDeviceAdapter;

    SessionManager sm, session;
    int custormerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        setupDrawer(toolbar);

        //promotions
        RecyclerView rvPromotions = findViewById(R.id.rvPromotions);

        rvPromotions.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        List<Promotion> promotionList = new ArrayList<>();

        promotionList.add(new Promotion(R.drawable.promo_repair, "Get 20% off on your first device repair!"));

        promotionList.add(new Promotion(R.drawable.promo_warranty, "Extended warranty available for selected devices."));

        promotionList.add(new Promotion(R.drawable.promo_support, "24/7 technical support now included for premium users."));

        promotionList.add(new Promotion(R.drawable.promo_offer, "Seasonal offer: Free diagnostics for limited time."));

        PromotionAdapter adapter = new PromotionAdapter(this,promotionList);
        rvPromotions.setAdapter(adapter);

        //saved Devices
        rvSavedDevices = findViewById(R.id.rvSavedDevices);
        rvSavedDevices.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        sm = new SessionManager(this);
        custormerId = sm.getCustomerId();

        if(custormerId == -1){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        DB_Operations db = new DB_Operations(this);

        List<SavedDevice> devices = db.getSavedDevicesForCustomer(custormerId);

        SavedDeviceAdapter adapter1 = new SavedDeviceAdapter(devices, device -> {
            DB_Operations db1 = new DB_Operations(this);
            db1.removeSavedDevice(custormerId, device.getDeviceId());
            loadSavedDevices();
        }
        );
        rvSavedDevices.setAdapter(adapter1);

        TextView tvFullName = findViewById(R.id.tvFullName);

        SessionManager sm = new SessionManager(this);
        String fullName = sm.getFullName();

        tvFullName.setText(fullName);

        Button btnEditProfile = findViewById(R.id.btnEditProfile);

        btnEditProfile.setOnClickListener(v ->{
            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            startActivity(intent);
        });

        session = new SessionManager(this);
        int customerId = session.getCustomerId();

        int unreadCount = db.getUnreadNotificationCount(customerId);

        setupNotificationBadge(unreadCount);
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadSavedDevices();
    }
    private void loadSavedDevices() {
        DB_Operations db = new DB_Operations(this);
        List<SavedDevice> savedDevices = db.getSavedDevicesForCustomer(custormerId);

        savedDeviceAdapter = new SavedDeviceAdapter(savedDevices, device -> {
            DB_Operations db1 = new DB_Operations(this);
            db1.removeSavedDevice(custormerId, device.getDeviceId());
            loadSavedDevices();
        });

        rvSavedDevices.setAdapter(savedDeviceAdapter);
    }
}