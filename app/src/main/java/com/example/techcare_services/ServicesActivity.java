package com.example.techcare_services;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ServicesActivity extends AppCompatActivity {
    RecyclerView rvServiceCategories;
    SearchView searchView; DB_Operations db;
    List<Device> categories;
    ServiceCategoryAdapter adapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_services);
        searchView = findViewById(R.id.searchView);

        boolean openSearch = getIntent().getBooleanExtra("OPEN_SEARCH", false);
        if (openSearch) {
            searchView.requestFocus();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(v -> {
            startActivity(new Intent(ServicesActivity.this, CustomerDashboardActivity.class));
            finish(); });

        rvServiceCategories = findViewById(R.id.rvServiceCategories);
        rvServiceCategories.setLayoutManager(new LinearLayoutManager(this));

        db = new DB_Operations(this);
        categories = db.getServiceCategories();
        adapter = new ServiceCategoryAdapter(this, categories, db);
        rvServiceCategories.setAdapter(adapter);

        String targetDeviceType = getIntent().getStringExtra("DEVICE_TYPE");

        if (targetDeviceType != null) {

            scrollToCategory(categories, targetDeviceType);
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) {
                performSearch(query); return true;

            } @Override public boolean onQueryTextChange(String newText) {
                performSearch(newText); return true;
            }
        });

    } private void scrollToCategory(List<Device> categories, String deviceType) {

        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getDeviceType().equalsIgnoreCase(deviceType)) {
                int position = i; rvServiceCategories.post(() -> rvServiceCategories.scrollToPosition(position) );
                break; }
        }
    }
    private int findCategoryPositionByService(Service service) {
        int deviceId = Integer.parseInt(service.getDeviceId());
        for (int i = 0; i < categories.size(); i++) {

            String deviceType = categories.get(i).getDeviceType();
            List<Device> devices = db.getDevicesByType(deviceType);

            for (Device d : devices) {
                if (d.getDeviceId() == deviceId) {
                    return i;
                }
            }
        } return -1;

    }
    private void performSearch(String keyword) {

        if (keyword.trim().isEmpty()) return;

        List<Service> services = db.searchServices(keyword);

        if (!services.isEmpty()) {

            Service service = services.get(0);
            int categoryPos = findCategoryPositionByService(service);

            if (categoryPos != -1) {

                rvServiceCategories.post(() -> {rvServiceCategories.scrollToPosition(categoryPos);

                    List<Service> categoryServices = db.getServicesByDeviceType(categories.get(categoryPos).getDeviceType());

                    for (int i = 0; i < categoryServices.size(); i++) {
                        if (categoryServices.get(i).getServiceId() == service.getServiceId()) {

                            adapter.highlightService(categoryPos, service.getServiceId());
                            adapter.scrollToService(categoryPos, i);
                            break;
                        }
                    }
                });
            }
            return;
        }

        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getDeviceType()
                    .toLowerCase().contains(keyword.toLowerCase())) {

                rvServiceCategories.scrollToPosition(i);
                Toast.makeText(
                        this,
                        "No such service found",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }
        }

        Toast.makeText(
                this,
                "No service found for \"" + keyword + "\"",
                Toast.LENGTH_SHORT
        ).show();
    }

}

