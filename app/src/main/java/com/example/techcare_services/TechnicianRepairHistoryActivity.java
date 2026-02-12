package com.example.techcare_services;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TechnicianRepairHistoryActivity extends AppCompatActivity {
    RecyclerView rv;
    DB_Operations db;
    View emptyState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technician_repair_history);

        Toolbar toolbar = findViewById(R.id.toolbarRepairHistory);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        rv = findViewById(R.id.rvRepairHistory);
        emptyState = findViewById(R.id.layoutEmptyState);
        db = new DB_Operations(this);

        SessionManager sm = new SessionManager(this);
        int technicianId = sm.getTechnicianId();

        List<HistoryRepairment> list =
                db.getRepairHistoryForTechnician(technicianId);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new HistoryRepairAdapter(this, list));

        if (list.isEmpty()) {
            emptyState.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);
        } else {
            emptyState.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);

            rv.setLayoutManager(new LinearLayoutManager(this));
            rv.setAdapter(new HistoryRepairAdapter(this, list));
        }


    }
}