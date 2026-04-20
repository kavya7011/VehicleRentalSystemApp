package com.vehiclerental.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.vehiclerental.database.RentalEntity;
import com.vehiclerental.repository.RentalRepository;
import java.util.List;

public class RentalViewModel extends AndroidViewModel {
    private RentalRepository repository;
    private LiveData<List<RentalEntity>> allRentals;
    private LiveData<List<RentalEntity>> activeRentals;

    public RentalViewModel(@NonNull Application application) {
        super(application);
        repository = new RentalRepository(application);
        allRentals = repository.getAllRentals();
        activeRentals = repository.getActiveRentals();
    }

    public LiveData<List<RentalEntity>> getAllRentals() {
        return allRentals;
    }

    public LiveData<List<RentalEntity>> getActiveRentals() {
        return activeRentals;
    }

    public LiveData<List<RentalEntity>> getRentalsByCustomer(String email) {
        return repository.getRentalsByCustomer(email);
    }

    public void insert(RentalEntity rental) {
        repository.insertRental(rental);
    }

    public void update(RentalEntity rental) {
        repository.updateRental(rental);
    }
}
