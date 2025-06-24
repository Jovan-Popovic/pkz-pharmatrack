package com.example.pharmatrack.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharmatrack.R;
import com.example.pharmatrack.data.MedicineDao;
import com.example.pharmatrack.data.MedicineDao.MedicineItem;
import com.example.pharmatrack.util.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MedicineListActivity extends AppCompatActivity implements MedicineAdapter.Listener {

    private MedicineDao medicineDao;
    private MedicineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicines);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        medicineDao = new MedicineDao(this);
        String currentUser = getIntent().getStringExtra("currentUser");

        // bottom navigation handling
        BottomNavigationView nav = findViewById(R.id.bottomNav);
        BottomNavigationHelper.setup(nav, this, currentUser);

        RecyclerView recyclerView = findViewById(R.id.rvMeds);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<MedicineItem> list = medicineDao.all();
        adapter = new MedicineAdapter(list, this);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.fabAddMed).setOnClickListener(v -> {
            Intent intent = new Intent(this, AddEditMedicineActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.update(medicineDao.all());
    }

    @Override
    public void onEdit(MedicineItem item) {
        Intent intent = new Intent(this, AddEditMedicineActivity.class);
        intent.putExtra("id", item.id);
        startActivity(intent);
    }

    @Override
    public void onDelete(MedicineItem item) {
        new AlertDialog.Builder(this)
                .setTitle("Delete medicine")
                .setMessage("Are you sure you want to delete " + item.name + "?")
                .setPositiveButton("Delete", (d, w) -> {
                    medicineDao.delete(item.id);
                    adapter.update(medicineDao.all());
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        if (item.getItemId() == R.id.action_logout) {
            new SessionManager(this).clear();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}