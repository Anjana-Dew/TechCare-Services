package com.example.techcare_services;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UpdateRepairStatusActivity extends AppCompatActivity {
    RecyclerView rv;
    DB_Operations db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_repair_status);

        Toolbar toolbar = findViewById(R.id.toolbarUpdateStatus);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        rv = findViewById(R.id.rvUpdateStatus);
        db = new DB_Operations(this);

        SessionManager sm = new SessionManager(this);
        int technicianId = sm.getTechnicianId();

        List<AcceptedRepairItem> list = db.getAcceptedRequestsForTechnician(technicianId);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new UpdateStatusAdapter(this, list));
    }
}