package com.example.techcare_services;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class BookingActivity extends BaseActivity {
    Spinner spinnerMethod;
    EditText etAddress, etDateTime;
    Button btnConfirmBooking;
    TextView tvError;

    DB_Operations db;
    long requestId = -1;
    RecyclerView rvBookingHistory;
    TextView tvEmptyBookingHistory;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        Toolbar toolbar = findViewById(R.id.toolbarBooking);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        setupDrawer(toolbar);

        db = new DB_Operations(this);

        spinnerMethod = findViewById(R.id.spinnerMethod);
        etAddress = findViewById(R.id.etPickupAddress);
        etDateTime = findViewById(R.id.etDateTime);
        btnConfirmBooking = findViewById(R.id.btnConfirmBooking);
        tvError = findViewById(R.id.tvNoRequestError);

        requestId = getIntent().getLongExtra("REQUEST_ID", -1);

        setupSpinner();
        setupDateTimePicker();
        validateRequestId();

        btnConfirmBooking.setOnClickListener(v -> confirmBooking());

        rvBookingHistory = findViewById(R.id.rvBookingHistory);
        tvEmptyBookingHistory = findViewById(R.id.tvEmptyBookingHistory);

        rvBookingHistory.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );

        SessionManager sessionManager = new SessionManager(this);
        int customerId = sessionManager.getCustomerId();

        List<Booking> bookings = db.getBookingHistory(customerId);

        if (bookings.isEmpty()) {
            tvEmptyBookingHistory.setVisibility(View.VISIBLE);
            rvBookingHistory.setVisibility(View.GONE);
        } else {
            tvEmptyBookingHistory.setVisibility(View.GONE);
            rvBookingHistory.setVisibility(View.VISIBLE);

            BookingHistoryAdapter adapter =
                    new BookingHistoryAdapter(this, bookings);
            rvBookingHistory.setAdapter(adapter);
        }

        DB_Operations db = new DB_Operations(this);
        int unreadCount = db.getUnreadNotificationCount(customerId);

        setupNotificationBadge(unreadCount);
    }
    private void setupSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Select Method", "Pickup", "Drop-off"}
        );
        spinnerMethod.setAdapter(adapter);

        spinnerMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String method = spinnerMethod.getSelectedItem().toString();
                etAddress.setVisibility(method.equals("Pickup") ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupDateTimePicker() {
        etDateTime.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();

            new DatePickerDialog(this, (view, year, month, day) -> {
                new TimePickerDialog(this, (timeView, hour, minute) -> {
                    calendar.set(year, month, day, hour, minute);
                    String formatted = new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm", Locale.getDefault()
                    ).format(calendar.getTime());
                    etDateTime.setText(formatted);
                }, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show();
            },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            ).show();
        });
    }

    private void validateRequestId() {
        if (requestId == -1) {
            tvError.setVisibility(View.VISIBLE);
            tvError.setText("No repair request found. Please submit a repair request first.");
            btnConfirmBooking.setEnabled(false);
        }
    }

    private void confirmBooking() {
        String method = spinnerMethod.getSelectedItem().toString();
        String dateTime = etDateTime.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        if (method.equals("Select Method")) {
            Toast.makeText(this, "Select service method", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dateTime.isEmpty()) {
            etDateTime.setError("Select date & time");
            return;
        }

        if (method.equals("Pickup") && address.isEmpty()) {
            etAddress.setError("Enter pickup address");
            return;
        }

        db.insertBooking(
                requestId,
                method,
                method.equals("Pickup") ? address : "Drop-off",
                dateTime
        );

        db.updateRepairStatus(requestId, "BOOKED");

        Toast.makeText(this, "Booking confirmed", Toast.LENGTH_SHORT).show();

        SessionManager sessionManager = new SessionManager(this);
        int customerId = sessionManager.getCustomerId();

        db.insertNotification(
                customerId,
                (int) requestId,
                "Your booking request has been sent to TechCare. We’ll update you soon."
        );

        NotificationHelper helper = new NotificationHelper();
        helper.showRepairStatusNotification(
                this,
                "Booking Sent",
                "Your booking request has been sent to TechCare.");

        finish();
    }
}