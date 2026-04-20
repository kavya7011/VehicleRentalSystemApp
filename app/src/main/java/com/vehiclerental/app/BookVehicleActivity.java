package com.vehiclerental.app;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BookVehicleActivity extends AppCompatActivity {

    private Spinner vehicleSpinner;
    private EditText startDateInput, endDateInput, daysInput, totalCostInput;
    private Button bookBtn, cancelBtn;
    private Calendar calendar = Calendar.getInstance();
    
    private VehicleViewModel vehicleViewModel;
    private RentalViewModel rentalViewModel;
    private List<VehicleEntity> availableVehiclesList = new ArrayList<>();
    private VehicleEntity selectedVehicle;
    private double currentTotalCost = 0.0;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_vehicle);

        vehicleSpinner = findViewById(R.id.vehicleSpinner);
        startDateInput = findViewById(R.id.startDateInput);
        endDateInput = findViewById(R.id.endDateInput);
        daysInput = findViewById(R.id.daysInput);
        totalCostInput = findViewById(R.id.totalCostInput);
        bookBtn = findViewById(R.id.bookBtn);
        cancelBtn = findViewById(R.id.cancelBtn);

        vehicleViewModel = new ViewModelProvider(this).get(VehicleViewModel.class);
        rentalViewModel = new ViewModelProvider(this).get(RentalViewModel.class);

        setupVehicleSpinner();

        startDateInput.setOnClickListener(v -> showDatePicker(startDateInput));
        endDateInput.setOnClickListener(v -> showDatePicker(endDateInput));

        daysInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                calculateTotalCost();
            }
        });

        bookBtn.setOnClickListener(v -> {
            if (validateInputs()) {
                calculateTotalCost();
                // Proceed to Payment
                Intent intent = new Intent(BookVehicleActivity.this, PaymentActivity.class);
                intent.putExtra("amount", currentTotalCost);
                startActivityForResult(intent, 1001);
            }
        });

        cancelBtn.setOnClickListener(v -> finish());
    }

    private boolean validateInputs() {
        if (selectedVehicle == null) {
            Toast.makeText(this, "Please select a vehicle", Toast.LENGTH_SHORT).show();
            return false;
        }

        String startDateStr = startDateInput.getText().toString().trim();
        String endDateStr = endDateInput.getText().toString().trim();
        String daysStr = daysInput.getText().toString().trim();

        if (startDateStr.isEmpty()) {
            startDateInput.setError("Start date is required");
            return false;
        }

        if (endDateStr.isEmpty()) {
            endDateInput.setError("End date is required");
            return false;
        }

        try {
            Date startDate = sdf.parse(startDateStr);
            Date endDate = sdf.parse(endDateStr);
            Date today = Calendar.getInstance().getTime();

            if (startDate.before(sdf.parse(sdf.format(today)))) {
                startDateInput.setError("Start date cannot be in the past");
                return false;
            }

            if (endDate.before(startDate)) {
                endDateInput.setError("End date must be after start date");
                return false;
            }
        } catch (ParseException e) {
            Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (daysStr.isEmpty()) {
            daysInput.setError("Number of days is required");
            return false;
        } else {
            try {
                int days = Integer.parseInt(daysStr);
                if (days <= 0) {
                    daysInput.setError("Days must be at least 1");
                    return false;
                }
            } catch (NumberFormatException e) {
                daysInput.setError("Invalid number");
                return false;
            }
        }

        return true;
    }

    private void setupVehicleSpinner() {
        vehicleViewModel.getAvailableVehicles().observe(this, vehicles -> {
            availableVehiclesList = vehicles;
            List<String> vehicleNames = new ArrayList<>();
            for (VehicleEntity v : vehicles) {
                vehicleNames.add(v.modelName + " ($" + v.rentalRate + "/day)");
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_dropdown_item, vehicleNames);
            vehicleSpinner.setAdapter(adapter);
        });

        vehicleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (availableVehiclesList != null && !availableVehiclesList.isEmpty()) {
                    selectedVehicle = availableVehiclesList.get(position);
                    calculateTotalCost();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            saveBooking();
        }
    }

    private void saveBooking() {
        String rentalId = "R" + System.currentTimeMillis();
        String startDate = startDateInput.getText().toString();
        String endDate = endDateInput.getText().toString();
        int days = Integer.parseInt(daysInput.getText().toString());

        String userEmail = getIntent().getStringExtra("customerEmail");
        if (userEmail == null) userEmail = "customer@example.com";

        RentalEntity rental = new RentalEntity(
                rentalId,
                userEmail.split("@")[0], // Simple name from email
                "000-000-0000",
                userEmail,
                selectedVehicle.vehicleId,
                selectedVehicle.modelName,
                selectedVehicle.vehicleType,
                days,
                currentTotalCost,
                startDate,
                endDate,
                "ACTIVE"
        );

        rentalViewModel.insert(rental);

        // Mark vehicle as unavailable
        selectedVehicle.available = false;
        vehicleViewModel.update(selectedVehicle);

        Toast.makeText(this, "Booking confirmed after successful payment!", Toast.LENGTH_LONG).show();
        finish();
    }

    private void showDatePicker(final EditText dateInput) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    calendar.set(year, monthOfYear, dayOfMonth);
                    dateInput.setText(sdf.format(calendar.getTime()));
                    dateInput.setError(null);
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void calculateTotalCost() {
        if (selectedVehicle == null) return;
        
        String daysStr = daysInput.getText().toString();
        if (!daysStr.isEmpty()) {
            try {
                int days = Integer.parseInt(daysStr);
                currentTotalCost = days * selectedVehicle.rentalRate;
                totalCostInput.setText("$" + String.format(Locale.getDefault(), "%.2f", currentTotalCost));
            } catch (NumberFormatException ignored) {}
        }
    }
}
