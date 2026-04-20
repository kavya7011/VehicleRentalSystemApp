package com.vehiclerental.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
    entities = {VehicleEntity.class, RentalEntity.class, CustomerEntity.class},
    version = 1,
    exportSchema = false
)
public abstract class RentalDatabase extends RoomDatabase {
    
    public abstract VehicleDao vehicleDao();
    public abstract RentalDao rentalDao();
    public abstract CustomerDao customerDao();
    
    private static volatile RentalDatabase instance;
    
    public static RentalDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (RentalDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        RentalDatabase.class,
                        "rental_database"
                    )
                    .fallbackToDestructiveMigration()
                    .build();
                }
            }
        }
        return instance;
    }
}
