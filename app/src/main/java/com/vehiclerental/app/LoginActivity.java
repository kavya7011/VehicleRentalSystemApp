package com.vehiclerental.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.vehiclerental.database.CustomerEntity;
import com.vehiclerental.repository.CustomerRepository;

public class LoginActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button ownerLoginBtn, customerLoginBtn, signupBtn;
    private CustomerRepository customerRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        ownerLoginBtn = findViewById(R.id.ownerLoginBtn);
        customerLoginBtn = findViewById(R.id.customerLoginBtn);
        signupBtn = findViewById(R.id.signupBtn);
        
        customerRepository = new CustomerRepository(getApplication());

        ownerLoginBtn.setOnClickListener(v -> loginUser("OWNER"));
        customerLoginBtn.setOnClickListener(v -> loginUser("CUSTOMER"));
        
        signupBtn.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        });
    }

    private void loginUser(String userType) {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (validateInputs(email, password)) {
            // Hardcoded Owner for local testing
            if (userType.equals("OWNER") && email.equals("owner@vehiclerental.com") && password.equals("owner123")) {
                navigateToDashboard("OWNER", email);
                return;
            }

            // Check Room Database for Customer
            new Thread(() -> {
                CustomerEntity customer = customerRepository.getCustomerByEmail(email);
                runOnUiThread(() -> {
                    if (customer != null && customer.userType.equals(userType)) {
                        Toast.makeText(this, userType + " Login Successful!", Toast.LENGTH_SHORT).show();
                        navigateToDashboard(userType, email);
                    } else {
                        Toast.makeText(this, "Invalid credentials or user type", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        }
    }

    private boolean validateInputs(String email, String password) {
        if (email.isEmpty()) {
            emailInput.setError("Email is required");
            return false;
        }
        if (password.isEmpty()) {
            passwordInput.setError("Password is required");
            return false;
        }
        return true;
    }

    private void navigateToDashboard(String userType, String email) {
        if (userType.equals("OWNER")) {
            Intent intent = new Intent(LoginActivity.this, OwnerDashboardActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(LoginActivity.this, CustomerDashboardActivity.class);
            intent.putExtra("customerEmail", email);
            startActivity(intent);
        }
        finish();
    }
}
