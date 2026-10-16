package com.lunecode.tmdb;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        String email = "test@gmail.com";
        String password = "testPassword";

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        loginPerform(email, password);
    }

    private void loginPerform(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(aVoid -> {
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            finish();
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
        });
    }
}