package com.lunecode.tmdb;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {
    private EditText etFullName, etEmail, etPhone, etPassword;
    private Button btnRegister;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = etFullName.getText().toString();
                String email = etEmail.getText().toString();
                String phone = etPhone.getText().toString();
                String password = etPassword.getText().toString();
                if(!fullName.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !password.isEmpty()){
                    performRegistartion(fullName, email, phone, password);
                }
            }
        });
        
    }

    private void performRegistartion(String fullName, String email, String phone, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()){
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    String userId = user.getUid();
                    saveUserData(userId, fullName, email, phone);
                }
            }else {
                Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserData(String userId, String fullName, String email, String phone) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("userId", userId);
        userData.put("fullName", fullName);
        userData.put("email", email);
        userData.put("phone", phone);

        mFirestore.collection("Users").document(userId).set(userData).addOnSuccessListener(aVoid -> {
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
            finish();
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
        });

    }
}