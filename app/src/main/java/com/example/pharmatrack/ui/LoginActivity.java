package com.example.pharmatrack.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmatrack.R;
import com.example.pharmatrack.data.PharmacistDao;
import com.example.pharmatrack.util.SessionManager;

public class LoginActivity extends AppCompatActivity {

    private PharmacistDao pharmacistDao;
    private SessionManager sessionManager;
    private EditText usernameEt;
    private EditText passwordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pharmacistDao = new PharmacistDao(this);
        sessionManager = new SessionManager(this);

        // If user is already logged‑in, skip the login screen
        String savedUser = sessionManager.getUser();
        if (savedUser != null) {
            launchHome(savedUser);
            finish();
            return;
        }

        usernameEt = findViewById(R.id.etUser);
        passwordEt = findViewById(R.id.etPass);
        Button loginBtn = findViewById(R.id.btnLogin);
        loginBtn.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        String username = usernameEt.getText().toString().trim();
        String password = passwordEt.getText().toString();

        if (pharmacistDao.login(username, password)) {
            sessionManager.saveUser(username);
            launchHome(username);
            finish();
        } else {
            Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show();
        }
    }

    private void launchHome(String username) {
        boolean isAdmin = pharmacistDao.isAdmin(username);
        Intent intent = new Intent(
                this,
                isAdmin ? PharmacistListActivity.class : MedicineListActivity.class
        );
        intent.putExtra("currentUser", username);
        startActivity(intent);
    }
}