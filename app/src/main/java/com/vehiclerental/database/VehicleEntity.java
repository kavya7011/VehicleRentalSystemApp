package com.vehiclerental.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vehicles")
public class VehicleEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    public String vehicleId;
    public String modelName;
    public double rentalRate;
    public String vehicleType; // "CAR" or "BIKE"
    public boolean available;
    public int seats; // For cars
    public boolean hasAC; // For cars
    public String bikeType; // For bikes
    public int engineCapacity; // For bikes
    public String imageUrl; // URL to vehicle image
    
    public VehicleEntity(String vehicleId, String modelName, double rentalRate, 
                         String vehicleType, boolean available) {
        this.vehicleId = vehicleId;
        this.modelName = modelName;
        this.rentalRate = rentalRate;
        this.vehicleType = vehicleType;
        this.available = available;
    }
}
