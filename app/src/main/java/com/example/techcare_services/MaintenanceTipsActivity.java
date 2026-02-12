package com.example.techcare_services;

import android.os.Bundle;
import android.view.animation.AnimationUtils;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;

public class MaintenanceTipsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_tips);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setupDrawer(toolbar);

        //changing toolbar menu
        toolbar.setNavigationIcon(R.drawable.ic_menu_navy);
        AppBarLayout appBar = findViewById(R.id.appBar);

        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isTeal = false;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int totalScrollRange = appBarLayout.getTotalScrollRange();

                if (Math.abs(verticalOffset) >= totalScrollRange && !isTeal) {
                    toolbar.setNavigationIcon(R.drawable.ic_menu_teal);
                    isTeal = true;
                } else if (Math.abs(verticalOffset) < totalScrollRange && isTeal) {
                    toolbar.setNavigationIcon(R.drawable.ic_menu_navy);
                    isTeal = false;
                }
            }
        });

        RecyclerView recyclerView = findViewById(R.id.rvMaintenanceTips);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MaintenanceTipsAdapter adapter = new MaintenanceTipsAdapter(getMaintenanceTips());
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutAnimation(
                AnimationUtils.loadLayoutAnimation(
                        this,
                        R.anim.layout_animation_slide_up
                )
        );
        recyclerView.scheduleLayoutAnimation();

        SessionManager session = new SessionManager(this);
        int customerId = session.getCustomerId();

        DB_Operations db = new DB_Operations(this);
        int unreadCount = db.getUnreadNotificationCount(customerId);

        setupNotificationBadge(unreadCount);
    }

    private List<MaintenanceTips> getMaintenanceTips() {
        List<MaintenanceTips> tips = new ArrayList<>();

        tips.add(new MaintenanceTips(
                R.drawable.ic_tip,
                "Clean your device regularly",
                "Dust buildup can reduce performance and cause overheating."
        ));

        tips.add(new MaintenanceTips(
                R.drawable.ic_tip,
                "Avoid overheating",
                "Do not block air vents and avoid using your device on soft surfaces."
        ));

        tips.add(new MaintenanceTips(
                R.drawable.ic_tip,
                "Update your software",
                "Keeping your system updated improves security and performance."
        ));

        tips.add(new MaintenanceTips(
                R.drawable.ic_tip,
                "Use original chargers",
                "Third-party chargers may damage the battery or reduce lifespan."
        ));

        tips.add(new MaintenanceTips(
                R.drawable.ic_tip,
                "Restart occasionally",
                "Restarting clears background processes and refreshes system memory."
        ));

        tips.add(new MaintenanceTips(
                R.drawable.ic_tip,
                "Protect from moisture",
                "Water exposure can permanently damage internal components."
        ));

        return tips;
    }


}