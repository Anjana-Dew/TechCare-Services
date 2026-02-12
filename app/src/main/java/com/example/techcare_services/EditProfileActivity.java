package com.example.techcare_services;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditProfileActivity extends AppCompatActivity {
    EditText etFullName, etEmail, etPhone, etAddress;
    EditText etNewPassword, etConfirmPassword;
    Button btnSave;

    DB_Operations db;
    SessionManager sessionManager;
    int customerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_profile);

        Toolbar toolbar = findViewById(R.id.toolbarEditProfile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        toolbar.setNavigationOnClickListener(v -> {
            startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class));
            finish();
        });

        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSave = findViewById(R.id.btnSaveProfile);

        db = new DB_Operations(this);
        sessionManager = new SessionManager(this);
        customerId = sessionManager.getCustomerId();

        if (customerId == -1) {
            Toast.makeText(this, "Session expired", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadUserData();

        btnSave.setOnClickListener(v -> updateProfile());

    }
    private void loadUserData() {
        UserProfile profile = db.getCustomerProfile(customerId);

        if (profile != null) {
            etFullName.setText(profile.getFullName());
            etEmail.setText(profile.getEmail());
            etPhone.setText(profile.getPhone());
            etAddress.setText(profile.getAddress());
        }
    }

    private void updateProfile() {

        String fullName = etFullName.getText().toString().trim();
        String newEmail = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String newPassword = etNewPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(newEmail)) {
            Toast.makeText(this, "Name and Email are required", Toast.LENGTH_SHORT).show();
            return;
        }

        String oldEmail = sessionManager.getEmail();
        boolean emailChanged = !newEmail.equals(oldEmail);
        boolean passwordChanged = false;


        boolean userUpdated = db.updateUserInfo(customerId, fullName, newEmail);
        boolean customerUpdated = db.updateCustomerInfo(customerId, phone, address);

        if (!TextUtils.isEmpty(newPassword)) {

            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            String hashed = PasswordUtil.hashPassword(newPassword);
            db.updatePassword(customerId, hashed);
            passwordChanged = true;
        }

        if (userUpdated || customerUpdated || emailChanged || passwordChanged) {

            if (emailChanged || passwordChanged) {

                String msg = passwordChanged
                        ? "Password changed. Please login again."
                        : "Email changed. Please login again.";

                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

                sessionManager.logout();

                Intent intent = new Intent(EditProfileActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return;
            }

            sessionManager.setFullName(fullName);
            sessionManager.setEmail(newEmail);

            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EditProfileActivity.this, CustomerDashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(this, "Nothing changed", Toast.LENGTH_SHORT).show();
        }
    }

}