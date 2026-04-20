package com.vehiclerental.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CustomerDashboardActivity extends AppCompatActivity {

    private Button viewVehiclesBtn, myRentalsBtn, logoutBtn;
    private TextView welcomeText;
    private String customerEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);

        welcomeText = findViewById(R.id.welcomeText);
        viewVehiclesBtn = findViewById(R.id.viewVehiclesBtn);
        myRentalsBtn = findViewById(R.id.myRentalsBtn);
        logoutBtn = findViewById(R.id.logoutBtn);

        customerEmail = getIntent().getStringExtra("customerEmail");
        welcomeText.setText("Welcome, " + (customerEmail != null ? customerEmail : "Customer"));

        viewVehiclesBtn.setOnClickListener(v -> {
            Intent intent = new Intent(CustomerDashboardActivity.this, VehicleListActivity.class);
            intent.putExtra("userType", "customer");
            startActivity(intent);
        });

        myRentalsBtn.setOnClickListener(v -> {
            // Future: List of rentals for this customer
            Toast.makeText(this, "My Rentals feature coming soon!", Toast.LENGTH_SHORT).show();
        });

        logoutBtn.setOnClickListener(v -> {
            Intent intent = new Intent(CustomerDashboardActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
