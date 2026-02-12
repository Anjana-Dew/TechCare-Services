package com.example.techcare_services;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;


public class BaseActivity extends AppCompatActivity {
    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    protected void setupDrawer(Toolbar toolbar) {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START) );

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home && !(this instanceof CustomerDashboardActivity)) {
                startActivity(new Intent(this, CustomerDashboardActivity.class));
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
            } else if (id == R.id.nav_bookings) {
                startActivity(new Intent(this, BookingActivity.class));
            } else if (id == R.id.nav_tips) {
                startActivity(new Intent(this, MaintenanceTipsActivity.class)); }
            else if (id == R.id.nav_notifications) {
                startActivity(new Intent(this, NotificationsActivity.class));
            } else if (id == R.id.nav_contact) {
                startActivity(new Intent(this, ContactUsActivity.class));
            } else if (id == R.id.nav_logout) {
                SessionManager session = new SessionManager(this);
                session.logout();

                Intent i = new Intent(this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();

            }
            drawerLayout.closeDrawer(GravityCompat.START); return true;
        });
    }
    protected void setupNotificationBadge(int unreadCount) {

        MenuItem item = navigationView.getMenu().findItem(R.id.nav_notifications);

        FrameLayout badgeLayout =
                (FrameLayout) LayoutInflater.from(this)
                        .inflate(R.layout.menu_notification_badge, null);

        TextView tvBadge = badgeLayout.findViewById(R.id.tvBadgeCount);

        if (unreadCount > 0) {
            tvBadge.setText(String.valueOf(unreadCount));
            tvBadge.setVisibility(View.VISIBLE);
        } else {
            tvBadge.setVisibility(View.GONE);
        }

        item.setActionView(badgeLayout);
    }

}
