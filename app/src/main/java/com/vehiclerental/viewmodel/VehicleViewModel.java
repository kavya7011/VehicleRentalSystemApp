package com.vehiclerental.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.vehiclerental.database.VehicleEntity;
import com.vehiclerental.repository.VehicleRepository;
import java.util.List;

public class VehicleViewModel extends AndroidViewModel {
    private VehicleRepository repository;
    private LiveData<List<VehicleEntity>> allVehicles;
    private LiveData<List<VehicleEntity>> availableVehicles;

    public VehicleViewModel(@NonNull Application application) {
        super(application);
        repository = new VehicleRepository(application);
        allVehicles = repository.getAllVehicles();
        availableVehicles = repository.getAvailableVehicles();
    }

    public LiveData<List<VehicleEntity>> getAllVehicles() {
        return allVehicles;
    }

    public LiveData<List<VehicleEntity>> getAvailableVehicles() {
        return availableVehicles;
    }

    public void insert(VehicleEntity vehicle) {
        repository.insertVehicle(vehicle);
    }

    public void update(VehicleEntity vehicle) {
        repository.updateVehicle(vehicle);
    }

    public VehicleEntity getVehicleByVehicleId(String vehicleId) {
        return repository.getVehicleByVehicleId(vehicleId);
    }
}
