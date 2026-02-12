package com.example.techcare_services;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    EditText txtUsername, txtPassword;
    Button btnLogin;
    TextView tvSignUp;
    DB_Operations db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        db = new DB_Operations(this);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp);

        btnLogin.setOnClickListener(v -> login());

        tvSignUp.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));
    }

    private void login() {
        String email = txtUsername.getText().toString().trim();
        String pass = txtPassword.getText().toString();

        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Enter email & password", Toast.LENGTH_SHORT).show();
            return;
        }

        Cursor c = db.login(email,pass);

        if (c != null && c.moveToFirst()) {

            long userId = c.getLong(c.getColumnIndexOrThrow("user_id"));
            String role = c.getString(c.getColumnIndexOrThrow("role"));
            String fullName = c.getString(c.getColumnIndexOrThrow("full_name"));

            SessionManager sm = new SessionManager(this);
            sm.setUserId(userId);
            sm.setRole(role);
            sm.setFullName(fullName);
            sm.setEmail(email);

            if ("customer".equals(role)) {
                int customerId = db.getCustomerIdByUserId(userId);

                if (customerId != -1) {
                    sm.setCustomerId(customerId);
                    startActivity(new Intent(this, CustomerDashboardActivity.class));
                } else {
                    Toast.makeText(this, "Customer profile not found", Toast.LENGTH_SHORT).show();
                }
            }

            if ("technician".equals(role)) {

                int technicianId = db.getTechnicianIdByUserId(userId);

                if (technicianId != -1) {
                    sm.setTechnicianId(technicianId);
                    startActivity(new Intent(this, TechnicianDashboardActivity.class));
                } else {
                    Toast.makeText(this, "Technician profile not found", Toast.LENGTH_SHORT).show();
                }
            }


            finish();

        } else {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }

        if (c != null) c.close();
    }

}