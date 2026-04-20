package com.vehiclerental.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.vehiclerental.database.VehicleEntity;
import com.vehiclerental.repository.VehicleRepository;

public class AddVehicleActivity extends AppCompatActivity {

    private EditText vehicleIdInput, modelInput, rateInput;
    private RadioGroup vehicleTypeGroup;
    private EditText seatsInput, bikeTypeInput, engineInput;
    private ImageView vehicleImagePreview;
    private Button selectImageBtn, addBtn;
    
    private VehicleRepository vehicleRepository;
    private Uri selectedImageUri;

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    // Take persistable permission to keep access after reboot
                    try {
                        getContentResolver().takePersistableUriPermission(selectedImageUri, 
                                Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    } catch (SecurityException ignored) {}
                    vehicleImagePreview.setImageURI(selectedImageUri);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        vehicleRepository = new VehicleRepository(getApplication());

        vehicleIdInput = findViewById(R.id.vehicleIdInput);
        modelInput = findViewById(R.id.modelInput);
        rateInput = findViewById(R.id.rateInput);
        vehicleTypeGroup = findViewById(R.id.vehicleTypeGroup);
        seatsInput = findViewById(R.id.seatsInput);
        bikeTypeInput = findViewById(R.id.bikeTypeInput);
        engineInput = findViewById(R.id.engineInput);
        vehicleImagePreview = findViewById(R.id.vehicleImagePreview);
        selectImageBtn = findViewById(R.id.selectImageBtn);
        addBtn = findViewById(R.id.addBtn);

        vehicleTypeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.carRadio) {
                seatsInput.setVisibility(View.VISIBLE);
                bikeTypeInput.setVisibility(View.GONE);
                engineInput.setVisibility(View.GONE);
            } else if (checkedId == R.id.bikeRadio) {
                seatsInput.setVisibility(View.GONE);
                bikeTypeInput.setVisibility(View.VISIBLE);
                engineInput.setVisibility(View.VISIBLE);
            }
        });

        selectImageBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        addBtn.setOnClickListener(v -> {
            if (validateInputs()) {
                saveVehicleToDatabase(selectedImageUri != null ? selectedImageUri.toString() : null);
            }
        });
    }

    private boolean validateInputs() {
        if (vehicleIdInput.getText().toString().trim().isEmpty()) {
            vehicleIdInput.setError("Vehicle ID is required");
            return false;
        }
        if (modelInput.getText().toString().trim().isEmpty()) {
            modelInput.setError("Model Name is required");
            return false;
        }
        String rateStr = rateInput.getText().toString().trim();
        if (rateStr.isEmpty()) {
            rateInput.setError("Rental Rate is required");
            return false;
        }
        if (vehicleTypeGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select vehicle type", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void saveVehicleToDatabase(String imageUrl) {
        String id = vehicleIdInput.getText().toString().trim();
        String model = modelInput.getText().toString().trim();
        double rate = Double.parseDouble(rateInput.getText().toString().trim());
        String type = (vehicleTypeGroup.getCheckedRadioButtonId() == R.id.carRadio) ? "CAR" : "BIKE";

        VehicleEntity vehicle = new VehicleEntity(id, model, rate, type, true);
        vehicle.imageUrl = imageUrl;
        
        if (type.equals("CAR")) {
            String seats = seatsInput.getText().toString().trim();
            vehicle.seats = seats.isEmpty() ? 0 : Integer.parseInt(seats);
            vehicle.hasAC = true;
        } else {
            vehicle.bikeType = bikeTypeInput.getText().toString().trim();
            String engine = engineInput.getText().toString().trim();
            vehicle.engineCapacity = engine.isEmpty() ? 0 : Integer.parseInt(engine);
        }

        vehicleRepository.insertVehicle(vehicle);

        Toast.makeText(this, "Vehicle Added Successfully!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
