package com.vehiclerental.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.vehiclerental.database.RentalDatabase;
import com.vehiclerental.database.VehicleDao;
import com.vehiclerental.database.VehicleEntity;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class VehicleRepository {
    private VehicleDao vehicleDao;
    private Executor executor = Executors.newSingleThreadExecutor();
    
    public VehicleRepository(Application application) {
        RentalDatabase db = RentalDatabase.getInstance(application);
        this.vehicleDao = db.vehicleDao();
    }
    
    // Insert vehicle
    public void insertVehicle(VehicleEntity vehicle) {
        executor.execute(() -> vehicleDao.insertVehicle(vehicle));
    }
    
    // Update vehicle
    public void updateVehicle(VehicleEntity vehicle) {
        executor.execute(() -> vehicleDao.updateVehicle(vehicle));
    }
    
    // Delete vehicle
    public void deleteVehicle(VehicleEntity vehicle) {
        executor.execute(() -> vehicleDao.deleteVehicle(vehicle));
    }
    
    // Get all vehicles (LiveData)
    public LiveData<List<VehicleEntity>> getAllVehicles() {
        return vehicleDao.getAllVehicles();
    }

    // Get all vehicles (Synchronous)
    public List<VehicleEntity> getAllVehiclesSync() {
        return vehicleDao.getAllVehiclesSync();
    }
    
    // Get available vehicles (LiveData)
    public LiveData<List<VehicleEntity>> getAvailableVehicles() {
        return vehicleDao.getAvailableVehicles();
    }
    
    // Get vehicle by ID
    public VehicleEntity getVehicleById(int id) {
        return vehicleDao.getVehicleById(id);
    }
    
    // Get vehicle by vehicle ID string
    public VehicleEntity getVehicleByVehicleId(String vehicleId) {
        return vehicleDao.getVehicleByVehicleId(vehicleId);
    }
    
    // Get vehicles by type (LiveData)
    public LiveData<List<VehicleEntity>> getVehiclesByType(String type) {
        return vehicleDao.getVehiclesByType(type);
    }
}
