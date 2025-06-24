package com.example.pharmatrack.ui;


import android.content.Intent;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.view.Menu;
import android.view.MenuItem;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pharmatrack.R;
import com.example.pharmatrack.data.MedicineDao;
import com.example.pharmatrack.data.MedicineDao.MedicineItem;

import java.util.ArrayList;
import java.util.List;

public class PrescribeMedicineActivity extends AppCompatActivity {
    MedicineDao dao;
    Spinner spMed;
    EditText etQty, etPatient;
    List<MedicineItem> meds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.activity_dispense);

        BottomNavigationView nav = findViewById(R.id.bottomNav);
        nav.setSelectedItemId(R.id.menu_dispense);
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
        dao = new MedicineDao(this);
        spMed = findViewById(R.id.spMed);
        etQty = findViewById(R.id.etQtyDisp);
        etPatient = findViewById(R.id.etPatient);
        refresh();
        findViewById(R.id.btnDisp).setOnClickListener(v -> disp());
    }

    void refresh() {
        meds = dao.all();
        List<String> names = new ArrayList<>();
        for (MedicineItem m : meds) names.add(m.name + " (" + m.qty + ")");
        spMed.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, names));
    }

    void disp() {
        int pos = spMed.getSelectedItemPosition();
        int q = Integer.parseInt(etQty.getText().toString());
        MedicineItem mi = meds.get(pos);
        if (q > mi.qty) {
            Toast.makeText(this, "Nema dovoljno zaliha", Toast.LENGTH_SHORT).show();
            return;
        }
        dao.dispense(mi.id, q);
        Toast.makeText(this, "Izdato", Toast.LENGTH_SHORT).show();
        finish();
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