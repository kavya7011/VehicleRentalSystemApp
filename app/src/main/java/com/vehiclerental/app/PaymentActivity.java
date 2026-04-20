package com.vehiclerental.app;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.stripe.android.view.CardInputWidget;
import com.stripe.android.model.CardParams;

public class PaymentActivity extends AppCompatActivity {

    private TextView paymentAmountText;
    private CardInputWidget cardInputWidget;
    private Button payButton;
    private ProgressBar progressBar;
    private double amount = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        paymentAmountText = findViewById(R.id.paymentAmountText);
        cardInputWidget = findViewById(R.id.cardInputWidget);
        payButton = findViewById(R.id.payButton);
        progressBar = findViewById(R.id.paymentProgressBar);

        amount = getIntent().getDoubleExtra("amount", 0.0);
        paymentAmountText.setText(String.format("Amount to Pay: $%.2f", amount));

        payButton.setOnClickListener(v -> processPayment());
    }

    private void processPayment() {
        CardParams cardParams = cardInputWidget.getCardParams();
        if (cardParams == null) {
            Toast.makeText(this, "Invalid Card Details", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show progress
        payButton.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        // Simulate Network Delay for Payment Processing
        new Handler().postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(PaymentActivity.this, "Payment of $" + amount + " Successful!", Toast.LENGTH_LONG).show();
            
            // Return success to calling activity
            setResult(RESULT_OK);
            finish();
        }, 3000);
    }
}
