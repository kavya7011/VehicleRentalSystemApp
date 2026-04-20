package com.vehiclerental.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import androidx.lifecycle.LiveData;
import java.util.List;

@Dao
public interface VehicleDao {
    
    @Insert
    void insertVehicle(VehicleEntity vehicle);
    
    @Update
    void updateVehicle(VehicleEntity vehicle);
    
    @Delete
    void deleteVehicle(VehicleEntity vehicle);
    
    @Query("SELECT * FROM vehicles WHERE id = :id")
    VehicleEntity getVehicleById(int id);
    
    @Query("SELECT * FROM vehicles WHERE vehicleId = :vehicleId")
    VehicleEntity getVehicleByVehicleId(String vehicleId);
    
    @Query("SELECT * FROM vehicles")
    LiveData<List<VehicleEntity>> getAllVehicles();
    
    @Query("SELECT * FROM vehicles")
    List<VehicleEntity> getAllVehiclesSync();
    
    @Query("SELECT * FROM vehicles WHERE available = 1")
    LiveData<List<VehicleEntity>> getAvailableVehicles();
    
    @Query("SELECT * FROM vehicles WHERE vehicleType = :type")
    LiveData<List<VehicleEntity>> getVehiclesByType(String type);
    
    @Query("DELETE FROM vehicles")
    void deleteAllVehicles();
}
