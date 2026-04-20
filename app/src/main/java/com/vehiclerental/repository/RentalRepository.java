package com.vehiclerental.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.vehiclerental.database.RentalDatabase;
import com.vehiclerental.database.RentalDao;
import com.vehiclerental.database.RentalEntity;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RentalRepository {
    private RentalDao rentalDao;
    private Executor executor = Executors.newSingleThreadExecutor();
    
    public RentalRepository(Application application) {
        RentalDatabase db = RentalDatabase.getInstance(application);
        this.rentalDao = db.rentalDao();
    }
    
    // Insert rental
    public void insertRental(RentalEntity rental) {
        executor.execute(() -> rentalDao.insertRental(rental));
    }
    
    // Update rental
    public void updateRental(RentalEntity rental) {
        executor.execute(() -> rentalDao.updateRental(rental));
    }
    
    // Delete rental
    public void deleteRental(RentalEntity rental) {
        executor.execute(() -> rentalDao.deleteRental(rental));
    }
    
    // Get all rentals (LiveData)
    public LiveData<List<RentalEntity>> getAllRentals() {
        return rentalDao.getAllRentals();
    }
    
    // Get active rentals (LiveData)
    public LiveData<List<RentalEntity>> getActiveRentals() {
        return rentalDao.getActiveRentals();
    }
    
    // Get rental by ID
    public RentalEntity getRentalById(int id) {
        return rentalDao.getRentalById(id);
    }
    
    // Get rental by rental ID string
    public RentalEntity getRentalByRentalId(String rentalId) {
        return rentalDao.getRentalByRentalId(rentalId);
    }
    
    // Get rentals by customer email (LiveData)
    public LiveData<List<RentalEntity>> getRentalsByCustomer(String email) {
        return rentalDao.getRentalsByCustomer(email);
    }
}
