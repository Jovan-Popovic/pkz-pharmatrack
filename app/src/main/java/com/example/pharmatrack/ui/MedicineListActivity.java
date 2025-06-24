package com.example.pharmatrack.ui;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmatrack.R;
import com.example.pharmatrack.data.MedicineDao;

public class MedicineListActivity extends AppCompatActivity {
    MedicineDao dao;
    MedicineAdapter adapter;

    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);

        setContentView(R.layout.activity_medicines);

        BottomNavigationView nav = findViewById(R.id.bottomNav);
        nav.setSelectedItemId(R.id.menu_meds);
        nav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.menu_meds) {
                startActivity(new Intent(this, MedicineListActivity.class));
                return true;

            } else if (id == R.id.menu_users) {
                startActivity(new Intent(this, PharmacistListActivity.class));
                return true;

            } else if (id == R.id.menu_dispense) {
                startActivity(new Intent(this, DispenseActivity.class));
                return true;
            }

            return false;
        });
        dao = new MedicineDao(this);
        RecyclerView rv = findViewById(R.id.rvMeds);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MedicineAdapter(dao.all(), item -> edit(item.id));
        rv.setAdapter(adapter);
        findViewById(R.id.fabAddMed).setOnClickListener(v -> add());
        findViewById(R.id.fabDispense).setOnClickListener(v -> startActivity(new Intent(this, DispenseActivity.class)));
    }

    void add() {
        startActivity(new Intent(this, AddEditMedicineActivity.class));
    }

    void edit(long id) {
        Intent i = new Intent(this, AddEditMedicineActivity.class);
        i.putExtra("medId", id);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.update(dao.all());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            Intent i = new Intent(this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}