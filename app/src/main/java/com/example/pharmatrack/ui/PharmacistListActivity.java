package com.example.pharmatrack.ui;


import android.content.Intent;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmatrack.R;
import com.example.pharmatrack.data.PharmacistDao;

public class PharmacistListActivity extends AppCompatActivity {
    PharmacistDao dao;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);

        setContentView(R.layout.activity_pharmacists);

        BottomNavigationView nav = findViewById(R.id.bottomNav);
        nav.setSelectedItemId(R.id.menu_users);
        nav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.menu_meds) {
                startActivity(new Intent(this, MedicineListActivity.class));
                return true;

            } else if (id == R.id.menu_users) {
                startActivity(new Intent(this, PharmacistListActivity.class));
                return true;

            } else if (id == R.id.menu_dispense) {
                startActivity(new Intent(this, PrescribeMedicineActivity.class));
                return true;
            }

            return false;
        });
        dao = new PharmacistDao(this);
        ListView list = findViewById(R.id.listPharmacists);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dao.all());
        list.setAdapter(adapter);
        list.setOnItemLongClickListener((p, v, idx, id) -> {
            remove(idx);
            return true;
        });
        findViewById(R.id.fabAddPharm).setOnClickListener(v -> dialogAdd());
    }

    void refresh() {
        adapter.clear();
        adapter.addAll(dao.all());
        adapter.notifyDataSetChanged();
    }

    void dialogAdd() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_pharmacist, null);
        EditText u = view.findViewById(R.id.dlgUser), p = view.findViewById(R.id.dlgPass);
        new AlertDialog.Builder(this).setTitle("New pharmacist")
                .setView(view)
                .setPositiveButton("Save", (d, which) -> {
                    dao.add(u.getText().toString(), p.getText().toString(), false);
                    refresh();
                })
                .setNegativeButton("Cancel", null).show();
    }

    void remove(int pos) {
        new AlertDialog.Builder(this)
                .setMessage("Remove pharmacist?")
                .setPositiveButton("Yes", (d, w) -> {
                    dao.remove(pos + 1);
                    refresh();
                })
                .setNegativeButton("No", null).show();
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