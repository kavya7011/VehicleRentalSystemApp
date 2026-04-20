package com.vehiclerental.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CustomerDao {
    
    @Insert
    void insertCustomer(CustomerEntity customer);
    
    @Update
    void updateCustomer(CustomerEntity customer);
    
    @Delete
    void deleteCustomer(CustomerEntity customer);
    
    @Query("SELECT * FROM customers WHERE id = :id")
    CustomerEntity getCustomerById(int id);
    
    @Query("SELECT * FROM customers WHERE email = :email")
    CustomerEntity getCustomerByEmail(String email);
    
    @Query("SELECT * FROM customers")
    List<CustomerEntity> getAllCustomers();
    
    @Query("SELECT * FROM customers WHERE userType = :type")
    List<CustomerEntity> getCustomersByType(String type);
    
    @Query("DELETE FROM customers")
    void deleteAllCustomers();
}
