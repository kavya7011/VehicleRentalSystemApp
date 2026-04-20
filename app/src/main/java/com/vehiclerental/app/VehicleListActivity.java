package com.vehiclerental.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.vehiclerental.database.VehicleEntity;
import com.vehiclerental.viewmodel.VehicleViewModel;
import java.util.ArrayList;

public class VehicleListActivity extends AppCompatActivity {

    private ListView vehicleListView;
    private Button bookBtn;
    private String userType;
    private VehicleViewModel vehicleViewModel;
    private VehicleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_list);

        vehicleListView = findViewById(R.id.vehicleListView);
        bookBtn = findViewById(R.id.bookBtn);

        userType = getIntent().getStringExtra("userType");

        if ("owner".equals(userType)) {
            bookBtn.setVisibility(android.view.View.GONE);
        }

        vehicleViewModel = new ViewModelProvider(this).get(VehicleViewModel.class);
        
        vehicleViewModel.getAllVehicles().observe(this, vehicles -> {
            if (vehicles != null) {
                adapter = new VehicleAdapter(this, vehicles);
                vehicleListView.setAdapter(adapter);
            }
        });

        bookBtn.setOnClickListener(v -> {
            Intent intent = new Intent(VehicleListActivity.this, BookVehicleActivity.class);
            startActivity(intent);
        });
    }
}
