package com.example.techcare_services;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotificationsActivity extends BaseActivity {
    RecyclerView rvNotifications;
    TextView tvEmpty;
    DB_Operations db;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notifications);

        Toolbar toolbar = findViewById(R.id.toolbarNotifications);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        setupDrawer(toolbar);

        rvNotifications = findViewById(R.id.rvNotifications);
        tvEmpty = findViewById(R.id.tvEmptyNotifications);

        rvNotifications.setLayoutManager(new LinearLayoutManager(this));

        db = new DB_Operations(this);
        sessionManager = new SessionManager(this);

        int customerId = sessionManager.getCustomerId();

        List<Notification> notifications =
                db.getNotifications(customerId);

        if (notifications.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            rvNotifications.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            rvNotifications.setVisibility(View.VISIBLE);

            NotificationAdapter adapter = new NotificationAdapter(this, notifications);
            rvNotifications.setAdapter(adapter);
        }
        db.markNotificationAsRead(customerId);

    }
}