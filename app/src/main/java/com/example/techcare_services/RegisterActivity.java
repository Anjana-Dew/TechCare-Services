package com.example.techcare_services;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {
    EditText txtFullName, txtEmail, txtPH, txtAddress, txtPass, txtConfirmPass;
    Button btnSignUp;
    TextView tvLogin;
    DB_Operations db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tvLogin = findViewById(R.id.tvLogin);

        tvLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        db = new DB_Operations(this);

        txtFullName = findViewById(R.id.txtFullName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPH = findViewById(R.id.txtPH);
        txtAddress = findViewById(R.id.txtAddress);
        txtPass = findViewById(R.id.txtPass);
        txtConfirmPass = findViewById(R.id.txtConfirmPass);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvLogin = findViewById(R.id.tvLogin);

        btnSignUp.setOnClickListener(v -> register());

        tvLogin.setOnClickListener(v ->
                startActivity(new Intent(this, LoginActivity.class)));
    }

    private void register() {
        String name = txtFullName.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String phone = txtPH.getText().toString().trim();
        String address = txtAddress.getText().toString().trim();
        String pass = txtPass.getText().toString();
        String confirm = txtConfirmPass.getText().toString();

        if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Required fields missing", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.setError("Invalid email format");
            txtEmail.requestFocus();
            return;
        }
        if (pass.length() < 8) {
            txtPass.setError("Use a strong password (minimum 8 characters)");
            txtPass.requestFocus();
            return;
        }
        if (!pass.equals(confirm)) {
            txtConfirmPass.setError("Passwords do not match");
            return;
        }

        if (db.isEmailExists(email)) {
            txtEmail.setError("Email already registered");
            return;
        }

        boolean success = db.registerCustomer(
                email, name, pass, phone, address
        );

        if (success) {
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
        }
    }
}