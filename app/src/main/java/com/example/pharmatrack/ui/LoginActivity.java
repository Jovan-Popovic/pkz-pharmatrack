package com.example.pharmatrack.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmatrack.R;
import com.example.pharmatrack.data.PharmacistDao;

public class LoginActivity extends AppCompatActivity {
    PharmacistDao dao;
    EditText userEt, passEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dao = new PharmacistDao(this);
        userEt = findViewById(R.id.etUser);
        passEt = findViewById(R.id.etPass);
        findViewById(R.id.btnLogin).setOnClickListener(v -> login());
    }

    void login() {
        String u = userEt.getText().toString().trim();
        String p = passEt.getText().toString();
        if (dao.login(u, p)) {
            boolean admin = dao.isAdmin(u);
            Intent i = new Intent(this, admin ? PharmacistListActivity.class : MedicineListActivity.class);
            i.putExtra("currentUser", u);
            startActivity(i);
            finish();
        } else Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show();
    }
}