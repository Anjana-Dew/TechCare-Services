package com.example.techcare_services;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RepairRequestActivity extends AppCompatActivity {
    TextView tvServiceName, tvServicePrice;
    EditText etIssueDescription;
    ImageView imgPreview;

    String imagePath = null;
    int serviceId;

    DB_Operations db;
    private static final int PICK_IMAGE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_repair_request);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(v -> {
            startActivity(new Intent(RepairRequestActivity.this, ServicesActivity.class));
            finish();
        });

        tvServiceName = findViewById(R.id.tvServiceName);
        tvServicePrice = findViewById(R.id.tvServicePrice);
        etIssueDescription = findViewById(R.id.etIssueDescription);
        imgPreview = findViewById(R.id.imgPreview);

        db = new DB_Operations(this);

        serviceId = getIntent().getIntExtra("SERVICE_ID", -1);

        loadServiceDetails();

        findViewById(R.id.btnUploadPhoto).setOnClickListener(v -> openGallery());
        findViewById(R.id.btnSubmitRequest).setOnClickListener(v -> submitRequest());
    }

    private void loadServiceDetails() {
        Service service = db.getServiceById(serviceId);

        tvServiceName.setText(service.getServiceName());
        tvServicePrice.setText("Rs. " + service.getPrice());
    }
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            imagePath = imageUri.toString();

            imgPreview.setImageURI(imageUri);
            imgPreview.setVisibility(View.VISIBLE);
        }
    }
    private void submitRequest() {

        String issueDesc = etIssueDescription.getText().toString().trim();

        if (issueDesc.isEmpty()) {
            etIssueDescription.setError("Please describe the issue");
            return;
        }

        SessionManager session = new SessionManager(this);
        int customerId = session.getCustomerId();

        if (customerId == -1) {
            Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show();
            return;
        }

        long requestId = db.insertRepairRequest(
                customerId,
                serviceId,
                issueDesc,
                imagePath
        );

        if (requestId == -1) {
            Toast.makeText(this, "Failed to submit request", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(RepairRequestActivity.this, BookingActivity.class);
        intent.putExtra("REQUEST_ID", requestId);
        intent.putExtra("SERVICE_ID", serviceId);
        startActivity(intent);

        finish();
    }

}