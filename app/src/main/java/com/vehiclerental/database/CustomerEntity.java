package com.vehiclerental.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "customers")
public class CustomerEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    public String email;
    public String name;
    public String phone;
    public String userType; // "OWNER" or "CUSTOMER"
    public long createdAt;
    
    public CustomerEntity(String email, String name, String phone, String userType) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.userType = userType;
        this.createdAt = System.currentTimeMillis();
    }
}
