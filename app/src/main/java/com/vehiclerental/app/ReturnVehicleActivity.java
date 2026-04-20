package com.vehiclerental.app;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.vehiclerental.database.RentalEntity;
import com.vehiclerental.database.VehicleEntity;
import com.vehiclerental.viewmodel.RentalViewModel;
import com.vehiclerental.viewmodel.VehicleViewModel;
import java.util.ArrayList;
import java.util.List;

public class ReturnVehicleActivity extends AppCompatActivity {

    private Spinner rentalIdSpinner;
    private EditText conditionInput, damageInput;
    private Button returnBtn, cancelBtn;
    
    private RentalViewModel rentalViewModel;
    private VehicleViewModel vehicleViewModel;
    private List<RentalEntity> activeRentalsList = new ArrayList<>();
    private RentalEntity selectedRental;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_vehicle);

        rentalIdSpinner = findViewById(R.id.rentalIdSpinner);
        conditionInput = findViewById(R.id.conditionInput);
        damageInput = findViewById(R.id.damageInput);
        returnBtn = findViewById(R.id.returnBtn);
        cancelBtn = findViewById(R.id.cancelBtn);

        rentalViewModel = new ViewModelProvider(this).get(RentalViewModel.class);
        vehicleViewModel = new ViewModelProvider(this).get(VehicleViewModel.class);

        setupRentalSpinner();

        returnBtn.setOnClickListener(v -> {
            if (validateInputs()) {
                processReturn();
            }
        });

        cancelBtn.setOnClickListener(v -> finish());
    }

    private boolean validateInputs() {
        if (selectedRental == null) {
            Toast.makeText(this, "No active rental selected", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (conditionInput.getText().toString().trim().isEmpty()) {
            conditionInput.setError("Vehicle condition is required");
            return false;
        }

        return true;
    }

    private void setupRentalSpinner() {
        rentalViewModel.getActiveRentals().observe(this, rentals -> {
            activeRentalsList = rentals;
            List<String> displayList = new ArrayList<>();
            if (rentals == null || rentals.isEmpty()) {
                displayList.add("No active rentals found");
                selectedRental = null;
            } else {
                for (RentalEntity r : rentals) {
                    displayList.add(r.rentalId + " - " + r.vehicleModel + " (" + r.customerName + ")");
                }
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_dropdown_item, displayList);
            rentalIdSpinner.setAdapter(adapter);
        });

        rentalIdSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (activeRentalsList != null && !activeRentalsList.isEmpty()) {
                    selectedRental = activeRentalsList.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void processReturn() {
        // Update rental status
        selectedRental.status = "COMPLETED";
        rentalViewModel.update(selectedRental);

        // Update vehicle availability
        new Thread(() -> {
            VehicleEntity vehicle = vehicleViewModel.getVehicleByVehicleId(selectedRental.vehicleId);
            if (vehicle != null) {
                vehicle.available = true;
                vehicleViewModel.update(vehicle);
            }
            
            runOnUiThread(() -> {
                Toast.makeText(ReturnVehicleActivity.this, "Vehicle Returned Successfully!", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }
}
