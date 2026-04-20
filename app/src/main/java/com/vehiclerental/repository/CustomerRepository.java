package com.vehiclerental.repository;

import android.app.Application;
import com.vehiclerental.database.RentalDatabase;
import com.vehiclerental.database.CustomerDao;
import com.vehiclerental.database.CustomerEntity;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CustomerRepository {
    private CustomerDao customerDao;
    private Executor executor = Executors.newSingleThreadExecutor();
    
    public CustomerRepository(Application application) {
        RentalDatabase db = RentalDatabase.getInstance(application);
        this.customerDao = db.customerDao();
    }
    
    // Insert customer
    public void insertCustomer(CustomerEntity customer) {
        executor.execute(() -> customerDao.insertCustomer(customer));
    }
    
    // Update customer
    public void updateCustomer(CustomerEntity customer) {
        executor.execute(() -> customerDao.updateCustomer(customer));
    }
    
    // Delete customer
    public void deleteCustomer(CustomerEntity customer) {
        executor.execute(() -> customerDao.deleteCustomer(customer));
    }
    
    // Get all customers
    public List<CustomerEntity> getAllCustomers() {
        return customerDao.getAllCustomers();
    }
    
    // Get customer by ID
    public CustomerEntity getCustomerById(int id) {
        return customerDao.getCustomerById(id);
    }
    
    // Get customer by email
    public CustomerEntity getCustomerByEmail(String email) {
        return customerDao.getCustomerByEmail(email);
    }
    
    // Get customers by type
    public List<CustomerEntity> getCustomersByType(String type) {
        return customerDao.getCustomersByType(type);
    }
}
