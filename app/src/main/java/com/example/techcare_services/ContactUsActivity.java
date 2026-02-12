package com.example.techcare_services;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.LinearInterpolator;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactUsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setupDrawer(toolbar);

        RecyclerView rvContactMethods = findViewById(R.id.rvContactMethods);
        RecyclerView rvVisitInfo = findViewById(R.id.rvVisitInfo);

        // contact methods rv
        rvContactMethods.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        List<ContactMethod> contactMethods = new ArrayList<>();
        contactMethods.add(new ContactMethod(
                R.drawable.ic_call,
                "Call Us",
                "+94 77 123 4567"
        ));
        contactMethods.add(new ContactMethod(
                R.drawable.ic_email,
                "Email Us",
                "support@techcare.com"
        ));
        contactMethods.add(new ContactMethod(
                R.drawable.ic_wp,
                "WhatsApp",
                "+94 77 123 4567"
        ));

        ContactMethodAdapter contactAdapter = new ContactMethodAdapter(contactMethods);
        rvContactMethods.setAdapter(contactAdapter);

        //visit info rv
        rvVisitInfo.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));


        List<VisitInfo> visitInfos = new ArrayList<>();
        visitInfos.add(new VisitInfo(
                R.drawable.ic_location,
                "Address",
                "123 Main Street,\nColombo"
        ));
        visitInfos.add(new VisitInfo(
                R.drawable.ic_hours,
                "Working Hours",
                "Mon – Sat\n9:00 AM – 6:00 PM"
        ));

        VisitInfoAdapter visitAdapter = new VisitInfoAdapter(visitInfos);
        rvVisitInfo.setAdapter(visitAdapter);

        smoothAutoNudge(rvContactMethods, 90,200);
        smoothAutoNudge(rvVisitInfo, 50,200);


        SessionManager session = new SessionManager(this);
        int customerId = session.getCustomerId();

        DB_Operations db = new DB_Operations(this);
        int unreadCount = db.getUnreadNotificationCount(customerId);

        setupNotificationBadge(unreadCount);
    }
    private void smoothAutoNudge(RecyclerView recyclerView, int distancePx, long startDelay) {
        ValueAnimator animator = ValueAnimator.ofInt(0, distancePx, 0);
        animator.setDuration(1300);

        animator.setStartDelay(startDelay);

        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            recyclerView.scrollBy(value - recyclerView.computeHorizontalScrollOffset(), 0);
        });
        animator.start();
    }

}

