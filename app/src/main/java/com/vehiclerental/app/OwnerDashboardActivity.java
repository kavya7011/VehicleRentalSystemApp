package com.vehiclerental.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class OwnerDashboardActivity extends AppCompatActivity {

    private Button addVehicleBtn, viewVehiclesBtn, returnVehicleBtn, logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_dashboard);

        addVehicleBtn = findViewById(R.id.addVehicleBtn);
        viewVehiclesBtn = findViewById(R.id.viewVehiclesBtn);
        returnVehicleBtn = findViewById(R.id.viewRentalsBtn); // Reusing ID for return
        logoutBtn = findViewById(R.id.logoutBtn);
        
        returnVehicleBtn.setText("Process Return");

        addVehicleBtn.setOnClickListener(v -> {
            Intent intent = new Intent(OwnerDashboardActivity.this, AddVehicleActivity.class);
            startActivity(intent);
        });

        viewVehiclesBtn.setOnClickListener(v -> {
            Intent intent = new Intent(OwnerDashboardActivity.this, VehicleListActivity.class);
            intent.putExtra("userType", "owner");
            startActivity(intent);
        });

        returnVehicleBtn.setOnClickListener(v -> {
            Intent intent = new Intent(OwnerDashboardActivity.this, ReturnVehicleActivity.class);
            startActivity(intent);
        });

        logoutBtn.setOnClickListener(v -> {
            Intent intent = new Intent(OwnerDashboardActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
