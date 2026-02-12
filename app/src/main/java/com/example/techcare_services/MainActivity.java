package com.example.techcare_services;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    DB_Operations dbOps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        DB_Operations db = new DB_Operations(this);
        db.insertDefaultDevices();
        db.insertDefaultServices();
        db.insertDefaultTechnicians();

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DB_Operations db1 = new DB_Operations(this);
        db1.insertDefaultDevices();


        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        ImageView logo = findViewById(R.id.imageView);
        TextView title = findViewById(R.id.textView);

        logo.startAnimation(slideUp);
        title.startAnimation(fadeIn);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            finish();
        }, 1500);

        dbOps = new DB_Operations(this);

// db cheking
        Cursor cursor = dbOps.getAllBookings();
        if (cursor.getCount() == 0) {
            Log.d("DB_CHECK", "No rows found in booking table.");
        } else {
            while (cursor.moveToNext()) {
                int requestId = cursor.getInt(cursor.getColumnIndexOrThrow("request_id"));
                Log.d("DB_CHECK", "Row found with request_id: " + requestId);
            }
        }
        cursor.close();

        if (dbOps.isBookingTableEmpty()) {
            Log.d("DB_CHECK", "Booking table is empty.");
        } else {
            Log.d("DB_CHECK", "Booking table has data.");
        }
        Cursor c = dbOps.getRepairRequests();
        while (c.moveToNext()) {
            Log.d("STATUS_CHECK",
                    "ID=" + c.getInt(0) + " STATUS=" + c.getString(1));
        }
        c.close();


    }

}