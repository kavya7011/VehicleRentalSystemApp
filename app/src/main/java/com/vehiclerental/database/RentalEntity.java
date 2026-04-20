package com.vehiclerental.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "rentals")
public class RentalEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    public String rentalId;
    public String customerName;
    public String customerPhone;
    public String customerEmail;
    public String vehicleId;
    public String vehicleModel;
    public String vehicleType;
    public int rentalDays;
    public double rentalCost;
    public String rentalDate;
    public String returnDate;
    public String status; // "ACTIVE" or "COMPLETED"
    
    public RentalEntity(String rentalId, String customerName, String customerPhone,
                        String customerEmail, String vehicleId, String vehicleModel,
                        String vehicleType, int rentalDays, double rentalCost,
                        String rentalDate, String returnDate, String status) {
        this.rentalId = rentalId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.vehicleId = vehicleId;
        this.vehicleModel = vehicleModel;
        this.vehicleType = vehicleType;
        this.rentalDays = rentalDays;
        this.rentalCost = rentalCost;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.status = status;
    }
}
