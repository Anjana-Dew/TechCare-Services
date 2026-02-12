package com.example.techcare_services;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class DevicesActivity extends AppCompatActivity {

    private RecyclerView rvAC, rvTV, rvFridge, rvComputer, rvLaptop, rvPhone, rvWashingMachine;
    private DB_Operations dbOperations;
    private static final int CURRENT_CUSTOMER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);

        MaterialToolbar toolbar = findViewById(R.id.devicesToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        dbOperations = new DB_Operations(this);

        rvAC = findViewById(R.id.rvAC);
        rvTV = findViewById(R.id.rvTV);
        rvFridge = findViewById(R.id.rvFridge);
        rvComputer = findViewById(R.id.rvComputer);
        rvLaptop = findViewById(R.id.rvLaptop);
        rvPhone = findViewById(R.id.rvPhone);
        rvWashingMachine = findViewById(R.id.rvWashingMachine);

        setupRecyclerView();
    }

    private void setupSingleRecycler(RecyclerView recyclerView, String deviceType) {
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );

        List<Device> devices = dbOperations.getDevicesByType(deviceType);

        DeviceAdapter adapter = new DeviceAdapter(devices, device -> {

            int customerId = CURRENT_CUSTOMER_ID;
            int deviceId = device.getDeviceId();

            if (!dbOperations.isDeviceAlreadySaved(customerId, deviceId)) {
                dbOperations.addSavedDevices(
                        CURRENT_CUSTOMER_ID,
                        device.getDeviceId()
                );
                Toast.makeText(this, "Device added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Device already added", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);
    }
    private void setupRecyclerView() {
        setupSingleRecycler(rvAC, "AC");
        setupSingleRecycler(rvTV, "TV");
        setupSingleRecycler(rvFridge, "Fridge");
        setupSingleRecycler(rvComputer, "Computer");
        setupSingleRecycler(rvLaptop, "Laptop");
        setupSingleRecycler(rvPhone, "Phone");
        setupSingleRecycler(rvWashingMachine, "Washing Machine");
    }
}
