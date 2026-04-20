package com.vehiclerental.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import androidx.lifecycle.LiveData;
import java.util.List;

@Dao
public interface RentalDao {
    
    @Insert
    void insertRental(RentalEntity rental);
    
    @Update
    void updateRental(RentalEntity rental);
    
    @Delete
    void deleteRental(RentalEntity rental);
    
    @Query("SELECT * FROM rentals WHERE id = :id")
    RentalEntity getRentalById(int id);
    
    @Query("SELECT * FROM rentals WHERE rentalId = :rentalId")
    RentalEntity getRentalByRentalId(String rentalId);
    
    @Query("SELECT * FROM rentals")
    LiveData<List<RentalEntity>> getAllRentals();
    
    @Query("SELECT * FROM rentals WHERE status = 'ACTIVE'")
    LiveData<List<RentalEntity>> getActiveRentals();
    
    @Query("SELECT * FROM rentals WHERE customerEmail = :email")
    LiveData<List<RentalEntity>> getRentalsByCustomer(String email);
    
    @Query("SELECT * FROM rentals WHERE vehicleId = :vehicleId AND status = 'ACTIVE'")
    List<RentalEntity> getActiveRentalsByVehicle(String vehicleId);
    
    @Query("DELETE FROM rentals")
    void deleteAllRentals();
}
