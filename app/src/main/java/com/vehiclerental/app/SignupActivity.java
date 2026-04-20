package com.vehiclerental.app;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.vehiclerental.database.CustomerEntity;
import com.vehiclerental.repository.CustomerRepository;

public class SignupActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput, confirmPasswordInput, nameInput, phoneInput;
    private RadioGroup userTypeGroup;
    private CustomerRepository customerRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        nameInput = findViewById(R.id.nameInput);
        phoneInput = findViewById(R.id.phoneInput);
        userTypeGroup = findViewById(R.id.userTypeGroup);
        Button signupBtn = findViewById(R.id.signupBtn);
        Button loginBtn = findViewById(R.id.loginBtn);

        customerRepository = new CustomerRepository(getApplication());

        signupBtn.setOnClickListener(v -> {
            if (validateInputs()) {
                registerUser();
            }
        });
        
        loginBtn.setOnClickListener(v -> finish());
    }

    private boolean validateInputs() {
        if (nameInput.getText().toString().trim().isEmpty()) {
            nameInput.setError("Name is required");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailInput.getText().toString().trim()).matches()) {
            emailInput.setError("Invalid email");
            return false;
        }
        if (passwordInput.getText().toString().trim().length() < 6) {
            passwordInput.setError("Min 6 characters");
            return false;
        }
        if (!passwordInput.getText().toString().equals(confirmPasswordInput.getText().toString())) {
            confirmPasswordInput.setError("Passwords don't match");
            return false;
        }
        if (userTypeGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Select user type", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void registerUser() {
        String email = emailInput.getText().toString().trim();
        String name = nameInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();

        int selectedId = userTypeGroup.getCheckedRadioButtonId();
        RadioButton selectedRadio = findViewById(selectedId);
        String userType = selectedRadio.getText().toString().toUpperCase();

        CustomerEntity customer = new CustomerEntity(email, name, phone, userType);
        
        new Thread(() -> {
            customerRepository.insertCustomer(customer);
            runOnUiThread(() -> {
                Toast.makeText(SignupActivity.this, "Signup Successful!", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }
}
