package com.vehiclerental.app;

import android.app.Application;
import com.stripe.android.PaymentConfiguration;

public class VehicleRentalApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        
        // Initialize Stripe with a test key
        // Replace with your actual publishable key from Stripe Dashboard
        PaymentConfiguration.init(
            getApplicationContext(),
            "pk_test_your_publishable_key"
        );
    }
}
