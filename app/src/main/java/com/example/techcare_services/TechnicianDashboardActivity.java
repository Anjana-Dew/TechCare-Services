package com.example.techcare_services;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TechnicianDashboardActivity extends AppCompatActivity {
    RecyclerView rvOptions;
    TextView tvTechName, tvSpecialization, tvAvailability;

    SessionManager sessionManager;
    DB_Operations db ;
    ImageView btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technician_dashboard);

        tvTechName = findViewById(R.id.tvTechName);
        tvSpecialization = findViewById(R.id.tvSpecialization);
        tvAvailability = findViewById(R.id.tvAvailability);
        rvOptions = findViewById(R.id.rvTechnicianOptions);

        sessionManager = new SessionManager(this);
        db = new DB_Operations(this);

        loadTechnicianInfo();
        setupOptionsRecycler();

        btnLogout = findViewById(R.id.ic_logOut);

        btnLogout.setOnClickListener(v -> {

            SharedPreferences prefs = getSharedPreferences("TechCarePrefs", MODE_PRIVATE);
            prefs.edit().clear().apply();

            Intent intent = new Intent(TechnicianDashboardActivity.this, LoginActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            finish();
        });



    }

    private void loadTechnicianInfo() {
        long userId = sessionManager.getUserId();
        Technician tech = db.getTechnicianByUserId(userId);

        if (tech != null) {
            tvTechName.setText(tech.getFullName());
            tvSpecialization.setText(tech.getSpecialization());

            String status = tech.getAvailabilityStatus();
            tvAvailability.setText("Status: " + status);

            if ("AVAILABLE".equals(status)) {
                tvAvailability.setTextColor(getResources().getColor(R.color.green));
            } else {
                tvAvailability.setTextColor(getResources().getColor(R.color.red));
            }
        }
    }

    private void setupOptionsRecycler() {

        List<TechnicianOption> options = new ArrayList<>();

        options.add(new TechnicianOption(
                R.drawable.ic_request,
                "Repair Requests",
                ViewRepairRequestActivity.class
        ));

        options.add(new TechnicianOption(
                R.drawable.ic_update,
                "Update Status",
                UpdateRepairStatusActivity.class
        ));

        options.add(new TechnicianOption(
                R.drawable.ic_history,
                "Repair History",
                TechnicianRepairHistoryActivity.class
        ));

        TechnicianOptionAdapter adapter =
                new TechnicianOptionAdapter(options, option -> {
                    startActivity(new Intent(
                            TechnicianDashboardActivity.this,
                            option.getTargetActivity()
                    ));
                });

        rvOptions.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        rvOptions.setAdapter(adapter);
    }
}