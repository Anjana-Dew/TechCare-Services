package com.example.techcare_services;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewRepairRequestActivity extends AppCompatActivity {
    RecyclerView rvRepairRequests;
    View emptyState;
    DB_Operations db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_repair_requests);

        Toolbar toolbar = findViewById(R.id.toolbarRepairRequests);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        toolbar.setNavigationOnClickListener(v -> {
            startActivity(new Intent(ViewRepairRequestActivity.this, TechnicianDashboardActivity.class));
            finish();
        });

        db = new DB_Operations(this);
        rvRepairRequests = findViewById(R.id.rvRepairRequests);
        emptyState = findViewById(R.id.layoutEmptyState);

        SessionManager sessionManager = new SessionManager(this);
        int technicianId = sessionManager.getTechnicianId();

        List<RepairRequestItem> list = db.getPendingRepairRequestsForTechnician(technicianId);

        if (list.isEmpty()) {
            emptyState.setVisibility(View.VISIBLE);
            rvRepairRequests.setVisibility(View.GONE);
        } else {
            emptyState.setVisibility(View.GONE);
            rvRepairRequests.setVisibility(View.VISIBLE);

            RepairRequestAdapter adapter =
                    new RepairRequestAdapter(this, list, technicianId);


            rvRepairRequests.setLayoutManager(new LinearLayoutManager(this));
            rvRepairRequests.setAdapter(adapter);

        }
    }
}